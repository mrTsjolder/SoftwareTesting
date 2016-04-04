package at.archkb.server.service.archprofile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.security.authentication.*;
import org.springframework.security.access.AccessDeniedException;

import at.archkb.server.config.MainConfig;
import at.archkb.server.entity.ArchProfile;
import at.archkb.server.entity.ArchProfileArchitecturestyle;
import at.archkb.server.entity.ArchProfileConstraint;
import at.archkb.server.entity.ArchProfileQualityattribute;
import at.archkb.server.entity.ArchProfileTradeoff;
import at.archkb.server.entity.Architecturestyle;
import at.archkb.server.entity.Constraint;
import at.archkb.server.entity.DesignDecision;
import at.archkb.server.entity.Diagram;
import at.archkb.server.entity.Driver;
import at.archkb.server.entity.QualityAttribute;
import at.archkb.server.entity.Tradeoff;
import at.archkb.server.entity.TradeoffItem;
import at.archkb.server.exception.OptimisticLockingException;
import at.archkb.server.service.user.UserService;
import objectcloner.ObjectCloner;

/**
 * Unittest class for the class:
 * at.archkb.server.service.archprofile.ArchProfileService(Impl)
 * ArchProfileService provides CRUD-Functionality for Architecture Profiles with
 * the Graph-Database Neo4j
 * 
 * @author Team12 (Mayer, Pfoser, Hoedt)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MainConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@ComponentScan("at.archkb.server")
@WebAppConfiguration
public class ArchProfileServiceTest {

	private final static Logger log = LoggerFactory.getLogger(ArchProfileServiceTest.class);

	/**
	 * Class under test
	 */
	@Autowired
	private ArchProfileService archProfileService;

	/**
	 * Provides CRUD-Functionality for Users
	 */
	@Autowired
	private UserService userService;

	/**
	 * Provides possibilities to communicate with the graph database
	 */
	@Autowired
	private Neo4jOperations template;

	/**
	 * Clears the DB and inits two user to test authentication
	 */
	@Before
	public void clearDB() {
		final String clearall = "Match (n) Optional Match (n)-[r]-(n2) Delete n,r,n2";
		template.query(clearall, new HashMap<String, String>());
		log.info("DB cleared");
		initUsers();
	}

	/**
	 * Creates one Architectural Profile with all possible properties in the
	 * database and loads this object afterwards from the database
	 * @throws Exception 
	 */
	@Test
	public void creatAndGetArchProfile() throws Exception {
		log.info("Create ArchProfile");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("admin@archkb.at", "password"));

		ArchProfile created = initArchProfiles();

		/*
		 * Saving the Object in the db forces an update of the object To compare
		 * the object with the one loaded from the db the object is cloned
		 */
		ArchProfile copy = (ArchProfile) ObjectCloner.deepCopy(created);

		archProfileService.createArchProfile(created);

		log.info("ArchProfile created");

		// load all Profiles
		ArchProfile loaded = archProfileService.getArchProfiles().iterator().next();
		Assert.assertEquals(loaded, copy);

		// load one Profile
		loaded = archProfileService.getArchProfile(loaded.getId());
		Assert.assertEquals(loaded, copy);
	}

	/**
	 * Tries to retrieve an Architecture Profile with a non existing id
	 */
	@Test
	public void getProfileWithNonExistingID() {
		log.info("Get ArchProfile with non existing ID");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("admin@archkb.at", "password"));

		ArchProfile loaded = archProfileService.getArchProfile(new Long(-1));
		Assert.assertEquals(loaded, new ArchProfile());
	}

	/**
	 * Tries to create an Architecture Profile with null Value
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createArchProfileWithNullValue() {
		log.info("Create ArchProfile with null Value");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("admin@archkb.at", "password"));

		archProfileService.createArchProfile(null);
	}

	/**
	 * Tries to create an Architectural Profile without authentication
	 */
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void createArchProfileUnauthorized() {
		log.info("Create ArchProfile without Authrization");

		ArchProfile create = new ArchProfile();
		create.setTitle("Test Title");
		archProfileService.createArchProfile(create);
	}

	/**
	 * Tries to create an Architectural Profile with wrong username
	 */
	@Test(expected = BadCredentialsException.class)
	public void createArchProfilewithWrongUser() {
		log.info("Create ArchProfile with wrong User");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("admin@wronguser.at", "password"));

		ArchProfile create = new ArchProfile();
		create.setTitle("Test Title");
		archProfileService.createArchProfile(create);
	}

	/**
	 * Tries to create an Architectural Profile with wrong password
	 */
	@Test(expected = BadCredentialsException.class)
	public void createArchProfilewithWrongPassword() {
		log.info("Create ArchProfile with wrong Password");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("admin@archkb.at", "wrongpassword"));

		ArchProfile create = new ArchProfile();
		create.setTitle("Test Title");
		archProfileService.createArchProfile(create);
	}

	/**
	 * Tries to update an existing profile and checks if the changes took effect
	 * in the database Deletion of properties of the Architecture Profile will
	 * not take effect, since this behavior will be treated in a sepperate class
	 * @throws Exception 
	 */
	@Test
	public void updateArchProfile() throws Exception {
		log.info("Update ArchProfile");

		String user = "admin@archkb.at";
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password"));

		ArchProfile profile = createProfileforUpdate(user);

		profile = archProfileService.getArchProfile(profile.getId());

		// change root property
		profile.setTitle("updated Title");

		// change properties of related nodes
		profile.getArchProfileQualityattributes().forEach(qa -> {
			qa.setDescription("new Description");
			qa.getQualityAttribute().setName("new Name");
		});

		// change properties of related nodes
		profile.getDiagrams().forEach(dia -> {
			dia.setPath("new path");
		});

		// add new node
		Diagram diagram = new Diagram();
		diagram.setPath("new Dia");
		profile.getDiagrams().add(diagram);

		ArchProfile updated = archProfileService.updateArchProfile(profile);
		
		int newDia = 0;
		for(Diagram dia:updated.getDiagrams()){
			if(dia.getPath().equals("new Dia")){
				newDia++;
			}else{
				Assert.assertEquals(dia.getPath(), "new path");
			}
		}
		Assert.assertEquals(newDia, 1);
		
		for(ArchProfileQualityattribute qa:updated.getArchProfileQualityattributes()){
			Assert.assertEquals(qa.getDescription(), "new Description");
			Assert.assertEquals(qa.getQualityAttribute().getName(), "new Name");
		}
	}

	/**
	 * Tries to update an existing profile with an user who is not the owner of
	 * the profile
	 * 
	 * @throws IOException
	 * @throws OptimisticLockingException
	 */
	@Test(expected = AccessDeniedException.class)
	public void updateArchProfileWithNotOwner() throws IOException, OptimisticLockingException {
		log.info("Update ArchProfile with wrong user");
		String user = "admin@archkb.at";
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password"));

		ArchProfile profile = createProfileforUpdate(user);

		// Reauthenticate with wrong user
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("user@archkb.at", "password"));

		// change root property
		profile.setTitle("updated Title");

		archProfileService.updateArchProfile(profile);
	}

	/**
	 * Tries to delete an existing profile and checks if the changes took effect
	 * in the database
	 */
	@Test
	public void deletArchProfile() {
		log.info("Delete ArchProfile");

		String user = "admin@archkb.at";
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password"));
		ArchProfile profile = createProfileforUpdate(user);

		archProfileService.deleteArchProfile(profile.getId());

		ArchProfile loaded = archProfileService.getArchProfile(profile.getId());

		Assert.assertEquals(loaded, new ArchProfile());
	}

	/**
	 * Tries to delete an existing profile with an user who is not the owner of
	 * the profile
	 */
	@Test(expected = AccessDeniedException.class)
	public void deletArchProfileWithNotOwner() {
		log.info("Delete ArchProfile with wrong User");

		String user = "admin@archkb.at";
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password"));

		ArchProfile profile = createProfileforUpdate(user);

		// Reauthenticate with wrong user
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("user@archkb.at", "password"));

		archProfileService.deleteArchProfile(profile.getId());
	}

	private ArchProfile createProfileforUpdate(String user) {
		ArchProfile profile = new ArchProfile();
		profile.setTitle("new Title");
		profile.setDiagrams(diagrams());
		profile.setArchProfileQualityattributes(archProfileQualityattributes());

		profile.setOwner(userService.getUser("admin@archkb.at"));

		return archProfileService.createArchProfile(profile);
	}

	private void initUsers() {
		userService.createUser("user@archkb.at", "password", "ROLE_USER");
		userService.createUser("admin@archkb.at", "password", "ROLE_ADMIN", "ROLE_USER");
	}

	private ArchProfile initArchProfiles() {

		ArchProfile archProfile = new ArchProfile();

		archProfile.setDescription(
				"A publicly consumed search solution based on the Velocity Search platform that will help users  find companies of interest.");
		archProfile.setTitle("Megavista Guides Search");

		archProfile.setDiagrams(diagrams());
		archProfile.setDesigndecisions(designDecisions());
		archProfile.setDrivers(drivers());
		archProfile.setArchProfileQualityattributes(archProfileQualityattributes());
		archProfile.setArchProfileTradeoffs(archProfileTradeoffs());
		archProfile.setArchProfileArchitecturestyles(archProfileArchitecturestyle());
		archProfile.setArchProfileConstraints(archProfileConstraints());
		archProfile.setOwner(userService.getUser("user@archkb.at"));

		return archProfile;
	}

	private Set<ArchProfileQualityattribute> archProfileQualityattributes() {
		Set<ArchProfileQualityattribute> result = new HashSet<>();

		QualityAttribute qaOne = new QualityAttribute();
		qaOne.setName("Crawlability");
		QualityAttribute qaTwo = new QualityAttribute();
		qaTwo.setName("Queryability");
		QualityAttribute qaThree = new QualityAttribute();
		qaThree.setName("Scalability");

		ArchProfileQualityattribute apqaOne = new ArchProfileQualityattribute();
		apqaOne.setQualityattribute(qaOne);
		apqaOne.setOrdering(0);
		apqaOne.setDescription(
				"A batch of up to 900 metadata updates are published to the database and reflected in search within 3 minutes.");

		ArchProfileQualityattribute apqaTwo = new ArchProfileQualityattribute();
		apqaTwo.setQualityattribute(qaTwo);
		apqaTwo.setOrdering(1);
		apqaTwo.setDescription(
				"An average- sized result set can be calculated within 2 seconds of Engine receiving the query.");

		ArchProfileQualityattribute apqaThree = new ArchProfileQualityattribute();
		apqaThree.setQualityattribute(qaThree);
		apqaThree.setOrdering(2);
		apqaThree.setDescription(
				"The size of the data source increases beyond current capacity, and the system can be easily expanded to deal with this.");

		result.add(apqaOne);
		result.add(apqaTwo);
		result.add(apqaThree);

		return result;
	}

	private Set<Diagram> diagrams() {
		Set<Diagram> result = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			Diagram diagram = new Diagram();
			diagram.setPath(UUID.randomUUID().toString());
			diagram.setOrdering(i);
			result.add(diagram);
		}
		return result;
	}

	private Set<DesignDecision> designDecisions() {
		Set<DesignDecision> result = new HashSet<>();

		DesignDecision ddOne = new DesignDecision();
		ddOne.setName("Database Crawling");
		ddOne.setRationale(
				"Database must be crawled as multiple views (vice JOINed) to avoid stressing it too much. Currently 1.6 million companies, each with dozens of metadata fields. Metadata joined via Virtual Document (company ID = vse- key).");
		ddOne.setOrdering(0);

		DesignDecision ddTwo = new DesignDecision();
		ddTwo.setName("Website Crawling");
		ddTwo.setRationale(
				"Company website must be crawled and website content made searchable under the company (i.e. return a single results with all content searchable as the same document). Website data joined with metadata via Virtual Document (company ID = vse- key).");
		ddTwo.setOrdering(1);

		DesignDecision ddThree = new DesignDecision();
		ddThree.setName("Full Crawl");
		ddThree.setRationale(
				"Full recrawl / week, DB refresh / 30 sec. if no crawl running. Full recrawl will pick up changes made to company websites (external data). 30- sec. refresh enables catchup for metadata crawls on DB updated every 60 seconds at most.");
		ddThree.setOrdering(2);

		DesignDecision ddFour = new DesignDecision();
		ddFour.setName("Dividing Collections");
		ddFour.setRationale(
				"Collections divided by company ID. Dividing allows maximum crawl throughput, using company ID (e.g. collection A has IDs 1â€“10) allows us to control partitioning.");
		ddFour.setOrdering(3);

		DesignDecision ddFive = new DesignDecision();
		ddFive.setName("Relevance Factor calculation");
		ddFive.setRationale(
				" Paid listings and keyword weighting factors used for relevancy calculation will come from the database (not Engine configuration). This will allow the business unit to make changes quickly using their existing tools.");
		ddFive.setOrdering(4);

		result.add(ddOne);
		result.add(ddTwo);
		result.add(ddThree);
		result.add(ddFour);
		result.add(ddFive);

		return result;
	}

	private Set<Driver> drivers() {
		Set<Driver> result = new HashSet<>();

		Driver driverOne = new Driver();
		driverOne.setName("More Revenue");
		driverOne.setDescription(
				"Better search encourages listed companies to purchase advertising, metadata supplements, and paid listings.");
		driverOne.setOrdering(0);

		Driver driverTwo = new Driver();
		driverTwo.setName("More Users");
		driverTwo.setDescription(
				"Faster results for users and advanced search options (such as refinement and spelling suggest) bring more users to the site.");
		driverTwo.setOrdering(1);

		result.add(driverOne);
		result.add(driverTwo);

		return result;
	}

	private Set<ArchProfileTradeoff> archProfileTradeoffs() {

		TradeoffItem toi1 = new TradeoffItem();
		toi1.setName("Crawlability");

		TradeoffItem toi2 = new TradeoffItem();
		toi2.setName("Maintainability");

		TradeoffItem toi3 = new TradeoffItem();
		toi3.setName("Crawlability and Queryability");

		TradeoffItem toi4 = new TradeoffItem();
		toi4.setName("Configuration");

		TradeoffItem toi5 = new TradeoffItem();
		toi5.setName("Flexibility/Development Speed");

		TradeoffItem toi6 = new TradeoffItem();
		toi6.setName("Cost");

		TradeoffItem toi7 = new TradeoffItem();
		toi7.setName("Modifiability");

		TradeoffItem toi8 = new TradeoffItem();
		toi8.setName("Maintainability");

		Tradeoff tradeoff1 = new Tradeoff();
		tradeoff1.setTradeoffItemUnder(toi1);
		tradeoff1.setTradeoffItemOver(toi2);

		Tradeoff tradeoff2 = new Tradeoff();
		tradeoff2.setTradeoffItemUnder(toi3);
		tradeoff2.setTradeoffItemOver(toi4);

		Tradeoff tradeoff3 = new Tradeoff();
		tradeoff3.setTradeoffItemUnder(toi5);
		tradeoff3.setTradeoffItemOver(toi6);

		Tradeoff tradeoff4 = new Tradeoff();
		tradeoff4.setTradeoffItemUnder(toi7);
		tradeoff4.setTradeoffItemOver(toi8);

		ArchProfileTradeoff apt1 = new ArchProfileTradeoff();
		apt1.setTradeoff(tradeoff1);
		apt1.setDescription(
				"Need to split the data source across multiple search collections, distributed across several servers. Incidentally promotes Scalability.");
		apt1.setOrdering(0);

		ArchProfileTradeoff apt2 = new ArchProfileTradeoff();
		apt2.setTradeoff(tradeoff2);
		apt2.setDescription("Highly configurable system reduces speed of crawl and query.");
		apt2.setOrdering(1);

		ArchProfileTradeoff apt3 = new ArchProfileTradeoff();
		apt3.setTradeoff(tradeoff3);
		apt3.setDescription(
				"Stakeholders are not 100 percent on all features and have a strong desire to go live as soon as possible.");
		apt3.setOrdering(2);

		ArchProfileTradeoff apt4 = new ArchProfileTradeoff();
		apt4.setTradeoff(tradeoff4);
		apt4.setDescription(
				"IT will maintain the system and know databases, not Engine, so when they make a change it should be detected and reflected.");
		apt4.setOrdering(3);

		Set<ArchProfileTradeoff> result = new HashSet<>();
		result.add(apt1);
		result.add(apt2);
		result.add(apt3);
		result.add(apt4);

		return result;
	}

	private Set<ArchProfileArchitecturestyle> archProfileArchitecturestyle() {

		Architecturestyle as1 = new Architecturestyle();
		as1.setName("3-tier");

		Architecturestyle as2 = new Architecturestyle();
		as2.setName("Source-Selector");

		Architecturestyle as3 = new Architecturestyle();
		as3.setName("Query Redundancy");

		Architecturestyle as4 = new Architecturestyle();
		as4.setName("Virtual Documents");

		Architecturestyle as5 = new Architecturestyle();
		as5.setName("Document Enqueue");

		Architecturestyle as6 = new Architecturestyle();
		as6.setName("Collection Sharding");

		Architecturestyle as7 = new Architecturestyle();
		as7.setName("Crawler Clone");

		Architecturestyle as8 = new Architecturestyle();
		as8.setName("Geolocation Lookup");

		ArchProfileArchitecturestyle apas1 = new ArchProfileArchitecturestyle();
		apas1.setArchitecturestyle(as1);
		apas1.setDescription("data, crawl, query");
		apas1.setOrdering(0);

		ArchProfileArchitecturestyle apas2 = new ArchProfileArchitecturestyle();
		apas2.setArchitecturestyle(as2);
		apas2.setDescription("promotes reliability");
		apas2.setOrdering(1);

		ArchProfileArchitecturestyle apas3 = new ArchProfileArchitecturestyle();
		apas3.setArchitecturestyle(as3);
		apas3.setDescription("promotes availability");
		apas3.setOrdering(2);

		ArchProfileArchitecturestyle apas4 = new ArchProfileArchitecturestyle();
		apas4.setArchitecturestyle(as4);
		apas4.setDescription("all data crawled");
		apas4.setOrdering(3);

		ArchProfileArchitecturestyle apas5 = new ArchProfileArchitecturestyle();
		apas5.setArchitecturestyle(as5);
		apas5.setDescription("for website data");
		apas5.setOrdering(4);

		ArchProfileArchitecturestyle apas6 = new ArchProfileArchitecturestyle();
		apas6.setArchitecturestyle(as6);
		apas6.setDescription("promotes crawlability");
		apas6.setOrdering(5);

		ArchProfileArchitecturestyle apas7 = new ArchProfileArchitecturestyle();
		apas7.setArchitecturestyle(as7);
		apas7.setDescription("promotes availability");
		apas7.setOrdering(6);

		ArchProfileArchitecturestyle apas8 = new ArchProfileArchitecturestyle();
		apas8.setArchitecturestyle(as8);
		apas8.setDescription("promotes maintainability/modi ability");
		apas8.setOrdering(7);

		Set<ArchProfileArchitecturestyle> result = new HashSet<>();

		result.add(apas1);
		result.add(apas2);
		result.add(apas3);
		result.add(apas4);
		result.add(apas5);
		result.add(apas6);
		result.add(apas7);
		result.add(apas8);

		return result;
	}

	private Set<ArchProfileConstraint> archProfileConstraints() {
		Constraint c1 = new Constraint();
		c1.setName("Linux");

		Constraint c2 = new Constraint();
		c2.setName("Windows");

		Constraint c3 = new Constraint();
		c3.setName("MacOS");

		ArchProfileConstraint apc1 = new ArchProfileConstraint();
		apc1.setConstraint(c1);
		apc1.setDescription("Must run on Linux machine.");
		apc1.setOrdering(0);

		ArchProfileConstraint apc2 = new ArchProfileConstraint();
		apc2.setConstraint(c2);
		apc2.setDescription("Must run on Windows machine.");
		apc2.setOrdering(1);

		ArchProfileConstraint apc3 = new ArchProfileConstraint();
		apc3.setConstraint(c3);
		apc3.setDescription("Must run on Mac OS machine.");
		apc3.setOrdering(2);

		Set<ArchProfileConstraint> result = new HashSet<>();

		result.add(apc1);
		result.add(apc2);
		result.add(apc3);

		return result;
	}

}

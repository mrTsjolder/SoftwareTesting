package at.archkb.server.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.InProcessServer;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "at.archkb.server.repository")
@EnableScheduling
@EnableTransactionManagement
public class Neo4JConfig extends Neo4jConfiguration {

	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stu
		return new SessionFactory("at.archkb.server.entity");
	}

	@Bean
	@Override
	public Neo4jServer neo4jServer() {
		return new InProcessServer();
	}

	@Bean
	@Override
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception {
		return super.getSession();
	}

}

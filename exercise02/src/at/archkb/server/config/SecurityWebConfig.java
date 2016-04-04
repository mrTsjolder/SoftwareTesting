package at.archkb.server.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import at.archkb.server.business.user.UserBusiness;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private UserBusiness userBusiness;

	public SecurityWebConfig() {
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userBusiness).passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and().authorizeRequests().antMatchers("index.html", "/assets/**", "/app/**").permitAll().anyRequest().permitAll()
		.and().logout().logoutUrl("/logout").logoutSuccessHandler(new CustomLogoutSuccessHandler()).permitAll()
		.and().csrf().disable();
	}
	
  /**
   * We do not want the server to set the location to a fixed destination (e.g. /#login) in the
   * response header of the logout request (the logoutSuccessUrl). Therefore we need a
   * Customized Logout Success Handler that just uses the referer location as the logoutSuccessUrl.
   * 
   * 
   * See: http://stackoverflow.com/questions/11454729/spring-security-3-1-redirect-after-logout
   * 
   * @author Rainer
   *
   */
	private class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
		public CustomLogoutSuccessHandler() {
			super.setUseReferer(true);
		}
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}

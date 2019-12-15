package com.customers.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.customers.authentication.CusstomAccessDeniedHandler;
import com.customers.authentication.JwtAuthenticationTokenFilter;
import com.customers.authentication.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CusstomAccessDeniedHandler cusstomAccessDeniedHandler() {
		return new CusstomAccessDeniedHandler();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().ignoringAntMatchers("/api/v1/**");
		httpSecurity.authorizeRequests().antMatchers("/api/v1/users/login**").permitAll();
		httpSecurity.antMatcher("/api/v1/**").httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/v1/**").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.POST, "/api/v1/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.PUT, "/api/v1/**").access("hasRole('ROLE_ADMIN')").and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(cusstomAccessDeniedHandler());
	}

}

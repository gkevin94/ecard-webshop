package com.ecard.ecardwebshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
@EnableWebSecurity
@Configuration

public class EcardwebshopApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(EcardwebshopApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "/js/**", "/css/**").permitAll()
				.antMatchers("/basket.html", "/myorders.html").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/adminproducts.html", "/adminusers.html", "/users.html", "/dashboard.html", "/reports.html", "/orders.html", "/orders", "/reports", "/dashboard", "/categories.html", "/upload.html").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/login.html").loginProcessingUrl("/login")
				.defaultSuccessUrl("/index.html")
				.and()
				.logout();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("SELECT user_name,password,enabled FROM users WHERE user_name=?")
				.authoritiesByUsernameQuery("SELECT user_name, role FROM users WHERE user_name = ?");
	}

}

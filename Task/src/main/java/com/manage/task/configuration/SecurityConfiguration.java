package com.manage.task.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.
//                jdbcAuthentication()
//                .usersByUsernameQuery("select email as principal, password as credentials, true from user where email=?")
//                .authoritiesByUsernameQuery("select u.email as principal, r.role as role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.email=?")
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .rolePrefix("ROLE_");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
	public SecurityFilterChain securityFilterChin(HttpSecurity http) throws Exception {
    	System.out.println("first here");
        http
		        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> {
                	auth.requestMatchers("/register", "/", "/login", "/about", "/css/**", "/webjars/**")
                    .permitAll();
                	auth.requestMatchers("/profile/**", "/tasks/**", "/task/**", "/users", "/user/**", "/h2-console/**")
//                  .permitAll();
                	.hasAnyRole("USER","ADMIN");
                	auth.requestMatchers("/assignment/**")
//                  .permitAll();  
                	.hasRole("ADMIN");
                })
                .formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/profile"))
                .logout(logout -> logout.logoutSuccessUrl("/login"));
//        http.headers().frameOptions().sameOrigin();
        
		return http.build();
    }
    
    @Bean
	public AuthenticationManager authManager(UserDetailsService detailsService) {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		System.out.println("next here");
		daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
	}


}

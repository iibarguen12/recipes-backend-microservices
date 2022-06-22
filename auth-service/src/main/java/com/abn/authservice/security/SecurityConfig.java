package com.abn.authservice.security;


import com.abn.authservice.domain.Roles;
import com.abn.authservice.filter.CustomAuthenticationFilter;
import com.abn.authservice.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), jwtSecret);
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests().antMatchers("/login").permitAll();
        //Food Categories
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/food-categories").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/food-categories/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE,"/v1/food-categories/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/v1/food-categories").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        //Ingredients
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/ingredients").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/ingredients/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE,"/v1/ingredients/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/v1/ingredients").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        //Recipes
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/recipes").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/recipes/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE,"/v1/recipes/{id}").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/v1/recipes").hasAnyAuthority(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        //Roles
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/roles").hasAnyAuthority(Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/v1/roles/user").hasAuthority(Roles.ROLE_ADMIN.name());
        //Users
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/users/{id}").hasAnyAuthority(Roles.ROLE_USER.name(),Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET,"/v1/users").hasAnyAuthority(Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/v1/users").hasAuthority(Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE,"/v1/users/{id}").hasAnyAuthority(Roles.ROLE_ADMIN.name());
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

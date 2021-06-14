package com.evgenii.my_market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;

    private static final String USER_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/registration", "/api/v1/auth", "/cart", "/products")
                .permitAll()
                .antMatchers("/user-info").hasAuthority(USER_ROLE)
                .antMatchers("/user-products").hasAuthority(USER_ROLE)
                .antMatchers("/user-main").hasAuthority(USER_ROLE)
                .antMatchers("/user-cart").hasAuthority(USER_ROLE)
                .antMatchers("/order-confirmation").hasAuthority(USER_ROLE)
                .antMatchers("/orders-result").hasAuthority(USER_ROLE)
                .antMatchers("/user-orders").hasAuthority(USER_ROLE)
                .antMatchers("/change-password").hasAuthority(USER_ROLE)
                .antMatchers("/add-products").hasAuthority(ADMIN_ROLE)
                .antMatchers("/statistic").hasAuthority(ADMIN_ROLE)
                .antMatchers("/change-orders").hasAuthority(ADMIN_ROLE)
                .antMatchers("/admin-main").hasAuthority(ADMIN_ROLE)
                .antMatchers("/admin-products").hasAuthority(ADMIN_ROLE)
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


}

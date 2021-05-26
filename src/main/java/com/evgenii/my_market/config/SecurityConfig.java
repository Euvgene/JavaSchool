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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/registration", "/api/v1/auth", "/cart","/products")
                .permitAll()
           /*     .antMatchers("/user-info").hasAuthority("ROLE_USER")
                .antMatchers("/user-products").hasAuthority("ROLE_USER")
                .antMatchers("/user-main").hasAuthority("ROLE_USER")
                .antMatchers("/user-cart").hasAuthority("ROLE_USER")
                .antMatchers("/order-confirmation").hasAuthority("ROLE_USER")
                .antMatchers("/orders-result").hasAuthority("ROLE_USER")
                .antMatchers("/user-orders").hasAuthority("ROLE_USER")
                .antMatchers("/change-password").hasAuthority("ROLE_USER")
                .antMatchers("/add-products").hasAuthority("ROLE_ADMIN")
                .antMatchers("/statistic").hasAuthority("ROLE_ADMIN")
                .antMatchers("/change-orders").hasAuthority("ROLE_ADMIN")
                .antMatchers("/admin-main").hasAuthority("ROLE_ADMIN")
                .antMatchers("/admin-products").hasAuthority("ROLE_ADMIN")*/
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

package io.javabrains.movieinfoservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, a.authority " +
                                "FROM user_authorities a, users u " +
                                "WHERE u.username = ? " +
                                "AND u.id = a.user_id"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails john = User.withUsername("john").password(passwordEncoder.encode("doe")).roles("USER").build();
        UserDetails jane = User.withUsername("jane").password(passwordEncoder.encode("doe")).roles("USER", "ADMIN").build();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
        UserDetails moderator = User.withUsername("moderator").password(passwordEncoder.encode("password")).roles("MODERATOR").build();
        return new InMemoryUserDetailsManager(john, jane, admin, moderator);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/login/**", "/css/**", "/images/**", "/favicon.ico")
                .permitAll()
                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login/error")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .httpBasic();
    }


}

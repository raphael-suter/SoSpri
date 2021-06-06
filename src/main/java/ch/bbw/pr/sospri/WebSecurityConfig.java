package ch.bbw.pr.sospri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("user");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("user", "admin");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers("/css/*")
                .permitAll()
                .antMatchers("/fragments/*")
                .permitAll()
                .antMatchers("/img/*")
                .permitAll()
                .antMatchers("/403.html")
                .permitAll()
                .antMatchers("/404.html")
                .permitAll()
                .antMatchers("/contact.html")
                .permitAll()
                .antMatchers("/index.html")
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/get-register")
                .permitAll()
                .antMatchers("/get-channel")
                .hasRole("user")
                .antMatchers("/get-members")
                .hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403.html");

        http
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}

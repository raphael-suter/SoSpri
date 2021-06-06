package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MemberService memberService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
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
                .hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/get-members")
                .hasAnyAuthority("admin")
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

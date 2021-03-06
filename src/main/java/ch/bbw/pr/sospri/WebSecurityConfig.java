package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    private final MemberService memberService;

    public WebSecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String pepper = "P0ydHhy0g0BrGjrqcv29";

        int iterations = 200000;
        int hashWidth = 256;

        return new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
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
                .failureHandler((request, response, exception) -> {
                    String username = request.getParameter("username");

                    logger.warn("Login fehlgeschlagen. User: " + username);

                    String redirectUrl = request.getContextPath() + "/login?error";
                    response.sendRedirect(redirectUrl);
                })
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    logger.info("Login erfolgreich. User: " + username);

                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                })
                .and()
                .logout()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    logger.info("Logout erfolgreich. User: " + username);

                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                })
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
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

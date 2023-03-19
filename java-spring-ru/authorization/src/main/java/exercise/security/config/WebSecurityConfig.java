package exercise.security.config;

import exercise.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyAuthority(UserRole.ADMIN.name())
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}

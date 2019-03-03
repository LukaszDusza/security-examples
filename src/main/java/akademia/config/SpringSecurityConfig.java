package akademia.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public SpringSecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("user")
                .password("$2a$10$xVpXASglb3fOQmqc4zIJUO5A/wb8O4rj6fKYtdOlM.QP5Xt44iW1.")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("$2a$10$Gyu6urRS/kNCftk0Ubokbu17TMLm1A6zHrz13K7VTuLB1ASlB.ij.")
                .roles("ADMIN")
        ;


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home").hasAnyRole("USER", "ADMIN")
                .antMatchers("/secured").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                // .loginPage("/login") //wlaczam wlasna stronę logowania
                .defaultSuccessUrl("/home");
        // .permitAll();

    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package akademia.config;


import akademia.repository.UserRepository;
import akademia.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfigDataBase extends WebSecurityConfigurerAdapter {

    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public SecurityConfigDataBase(CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .cors()
                .disable();

        http
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin") // Specifies the URL,which is used in action attribute on the <from> tag
                .usernameParameter("username")  // Username parameter, used in name attribute of the <input> tag. Default is 'username'.
                .passwordParameter("password")  // Password parameter, used in name attribute of the <input> tag. Default is 'password'.

                .successHandler((req, res, auth) -> {
                    for (GrantedAuthority authority : auth.getAuthorities()) {
                        System.out.println(authority.getAuthority());
                    }
                    System.out.println(auth.getName());
                    res.sendRedirect("/"); // Redirect user to index/home page
                })

                // .defaultSuccessUrl("/") // URL, where user will go after authenticating successfully. Skipped if successHandler() is used.

                .failureHandler((req, res, exp) -> {  // Failure handler invoked after authentication failure
                    String errMsg = "";
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        errMsg = "Invalid username or password.";
                    } else {
                        errMsg = "Unknown error - " + exp.getMessage();
                    }
                    req.getSession().setAttribute("message", errMsg);
                    res.sendRedirect("/login"); // Redirect user to login page with error message.
                })

                //.failureUrl("/login?error")   // URL, where user will go after authentication failure. Skipped if failureHandler() is used.

                .permitAll() // Allow access to any URL associate to formLogin()
                .and()
                .logout()

                .logoutUrl("/logout")   // Specifies the logout URL, default URL is '/logout'
                .logoutSuccessHandler((req, res, auth) -> {   // Logout handler called after successful logout
                    req.getSession().setAttribute("message", "You are logged out successfully.");
                    res.sendRedirect("/login"); // Redirect user to login page with message.
                })

                //.logoutSuccessUrl("/login") // URL, where user will be redirect after successful logout. Ignored if logoutSuccessHandler() is used.

                .permitAll() // Allow access to any URL associate to logout()
                .and()
                .csrf().disable(); // Disable CSRF support

        http
                .headers()
                .frameOptions()
                .disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder)
        ;
    }

}

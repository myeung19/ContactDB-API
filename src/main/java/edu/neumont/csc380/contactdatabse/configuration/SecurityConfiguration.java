package edu.neumont.csc380.contactdatabse.configuration;

import edu.neumont.csc380.contactdatabse.model.User;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserRepository userRepo;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsService()
        {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
            {
                return userRepo.findUserByUsername(s)
                        .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http)
    {
        try
        {
            http.cors();
            http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers("/user/**").hasAuthority("USER") // All http methods are acecpted by default
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception
    {
        return new AuthenticationManager()
        {
            @Override
            public Authentication authenticate(Authentication auth) throws AuthenticationException
            {
                String username = auth.getName();
                String password = auth.getCredentials().toString(); // Get the password as string

                Optional<User> user = userRepo.findUserByUsername(username);
                if (user.isPresent() &&
                        passwordEncoder().matches(password, user.get().getPassword()))
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            password,
                            user.get().getAuthorities());

                return null;
            }
        };
    }
}

package akademia.service;

import akademia.model.CustomUserDetails;
import akademia.model.UserApp;
import akademia.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<UserApp> usersOptional = userRepository.findUserByLogin(login);
        usersOptional.orElseThrow(() -> new UsernameNotFoundException("User not find"));

        return usersOptional.map(CustomUserDetails::new).get();
    }
}

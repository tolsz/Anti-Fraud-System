package antifraud.Security;

import antifraud.Repositories.UserRepository;
import antifraud.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(User::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}

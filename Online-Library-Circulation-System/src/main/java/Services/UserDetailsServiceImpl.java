package Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import Entities.Userentity;
import Repositories.UserEntityRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserEntityRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Userentity> userInfo = repository.findByEmail(email);
		return userInfo.map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("Email not found " + email));

	}
}

package br.com.postech.soat.commons.infrastructure.security;

import br.com.postech.soat.customer.application.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByCpf(username)
            .map(CustomerUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Customer not found with CPF: " + username));
    }
}

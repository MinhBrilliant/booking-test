package com.booking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minhnt2
 */
@Primary
@Service
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = null;
            }
            return Optional.of(username);
        } catch (Exception e) {
            return Optional.of("");
        }
    }
}

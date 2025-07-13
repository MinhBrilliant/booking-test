package com.booking.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AuditorAwareImplTest {

    private AuditorAwareImpl auditorAwareImplUnderTest;

    @BeforeEach
    void setUp() {
        auditorAwareImplUnderTest = new AuditorAwareImpl();
    }

    @Test
    void testGetCurrentAuditor() {
        assertThat(null == auditorAwareImplUnderTest.getCurrentAuditor());
    }
}

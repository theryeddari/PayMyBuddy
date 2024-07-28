package com.thery.paymybuddy.util;

import com.thery.paymybuddy.exception.InformationOnContextUtilsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InformationOnContextUtilsTest {

    @Test
    public void testGetIdClientFromContext() throws Exception {

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("1");
        String id = InformationOnContextUtils.getIdClientFromContext();

        assertEquals("1", id);
    }

    @Test
    public void testGetIdClientFromContext_Exception() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenThrow(RuntimeException.class);

        assertThrows(InformationOnContextUtilsException.GetIdClientFromContextException.class, InformationOnContextUtils::getIdClientFromContext);
    }

}

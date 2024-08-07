package com.thery.paymybuddy.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.thery.paymybuddy.exception.InformationOnContextUtilsException.GetIdClientFromContextException;

/**
 * Utils class for managing authentication operations such as sign up, sign in, and log out.
 */
@Component
public class InformationOnContextUtils {

    private static final Logger logger = LogManager.getLogger(InformationOnContextUtils.class);

    private InformationOnContextUtils() {
    }

    /**
     * Retrieves the username (id) from the context authentication.
     *
     * @return the id as a string
     */

    public static String getIdClientFromContext() throws GetIdClientFromContextException {
        try {
            String id = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.debug("Retrieved id from Context: {}", id);
            return id;
        } catch (Exception e) {
            throw new GetIdClientFromContextException(e);
        }
    }
}

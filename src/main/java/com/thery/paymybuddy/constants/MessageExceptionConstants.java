package com.thery.paymybuddy.constants;

/**
 * This class contains constants for exception messages used throughout the application.
 */
public class MessageExceptionConstants {

    public static final String MORE_INFO = ", additional information: ";

    //Jwt Exception messages
    public static final String GENERATE_TOKEN_EXCEPTION = "Error generating JWT token";

    // AuthenticationManagement Exception messages
    public static final String SIGN_UP_CLIENT_EXCEPTION = "Error while writing information for SignUp a Client";
    public static final String SIGN_IN_CLIENT_EXCEPTION = "Error while sign in Client";
    public static final String LOG_OUT_CLIENT_EXCEPTION = "Error while LogOut Client";
    public static final String CLIENT_ALREADY_EXISTS_EXCEPTION = "User already exists, use login page";
    public static final String GET_ID_CLIENT_FROM_CONTEXT_EXCEPTION = "Error when getting id from context";


    // Client Exception messages
    public static final String GET_PROFILE_EXCEPTION = "Error while getting information about a Client";
    public static final String CHANGE_PROFILE_EXCEPTION = "Error while save new information about Client";
    public static final String GET_SAVING_CLIENT_EXCEPTION = "Error while get saving information about Client";
    public static final String CLIENT_FIND_BY_EMAIL_EXCEPTION = "Error when getting client from this email";
    public static final String CLIENT_NOT_FOUND_EXCEPTION = "Client not found with this email, check your credentials ";
    public static final String CLIENT_BACKUP_EXCEPTION = "error while saving the customer profile ";
    public static final String CLIENT_IS_EXIST_EXCEPTION = "error while check client exist ";

    // RelationShips Exception messages
    public static final String ADD_RELATIONSHIPS_EXCEPTION = "Error while adding relationships about Client";
    public static final String RELATIONSHIPS_DETAIL_FOR_TRANSFER_EXCEPTION = "Error while get history of transfer's Client";

    // Transaction Exception messages
    public static final String GET_GENERAL_TRANSFER_DETAIL_EXCEPTION = "Error while getting history of all transaction detail about Client";
    public static final String DO_TRANSFER_EXCEPTION = "Error while doing transaction about Client, transaction canceled";

}
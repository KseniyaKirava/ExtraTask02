package by.htp.kirova.task2.controller.command;


/**
 * Provides prepared messages which cover most of standard situations. Utility
 * class, therefore final as it's not designed for instantiation and extension.
 *
 * @author Kseniya Kirava
 * @since Dec 12, 2018
 */
final class MessageConstant {

    /**
     * The message 'incorrect data' constant.
     */
    final static String MESSAGE_INCORRECT_DATA = "message.incorrectData";

    /**
     * The message 'too early for assessment' constant.
     */
    final static String MESSAGE_TOO_EARLY = "message.tooEarlyForAssessment";

    /**
     * The message 'too early for assessment' attribute name constant.
     */
    final static String TOO_EARLY = "tooEarly";

    /**
     * The message 'error search' attribute name constant.
     */
    final static String ERROR_SEARCH = "errorSearchCommand";

    /**
     * The message 'error with login or password' attribute name constant.
     */
    final static String ERROR_LOGIN = "errorLogin";

    /**
     * The message 'error with login or password' constant.
     */
    final static String MESSAGE_LOGIN_ERROR = "message.loginError";

    /**
     * The message 'error data' attribute name constant.
     */
    final static String ERROR_DATA = "errorData";

    /**
     * The message 'reservation not found' constant.
     */
    final static String MESSAGE_RESERVATIONS_NOT_FOUND = "message.reservationNotFound";

    /**
     * The message 'error data' attribute name constant.
     */
    final static String RESERVATIONS_NOT_FOUND = "reservationNotFound";

    /**
     * The message 'reservation not found' constant.
     */
    final static String MESSAGE_SEARCH_RESULTS = "message.reservationsSearchResults";

    /**
     * The message 'error data' attribute name constant.
     */
    final static String SEARCH_RESULTS = "reservationsSearchResults";

    /**
     * The message 'error signup' attribute name constant.
     */
    final static String ERROR_SIGNUP = "errorSignUp";

    /**
     * The message 'reservation not found' constant.
     */
    final static String MESSAGE_USERNAME_DUPLICATE = "message.usernameDuplicate";

    /**
     * The message 'error data' attribute name constant.
     */
    final static String USERNAME_DUPLICATE =  "usernameDuplicate";


    /**
     * Private default constructor as this class is not designed to be instantiated
     * or extended.
     */
    private MessageConstant() {}

}

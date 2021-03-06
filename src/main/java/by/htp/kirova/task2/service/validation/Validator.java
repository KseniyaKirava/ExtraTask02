package by.htp.kirova.task2.service.validation;

import by.htp.kirova.task2.service.util.DateService;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Validates data such as e-mail, name and password, by checking it against
 * predefined patterns. Utility class, therefore final as it's not designed for
 * instantiation and extension.
 *
 * @author Kseniya Kirava
 * @since Sep 24, 2018
 */

public final class Validator {

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger logger = Logger.getLogger(Validator.class);


    /**
     * Instance of Validator is used for data validation.
     */
    private static final Validator instance = new Validator();

    /**
     * Default private constructor as this class entirely utility.
     */
    private Validator() {
    }


    public static Validator getInstance() {
        return instance;
    }


    /**
     * Checks the username against user's username pattern.
     *
     * @param username user's username.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkUsername(String username) {
        boolean isValid = username != null && ValidationPattern.USERNAME.getPattern().matcher(username).matches();
        logger.debug("Username is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the first name/last name against user's name pattern.
     *
     * @param name user's first name/last name.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkName(String name) {
        boolean isValid = name != null && ValidationPattern.NAME.getPattern().matcher(name).matches();
        logger.debug("User name is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the middle name against user's name pattern.
     *
     * @param middleName user's middle name.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkMiddleName(String middleName) {
        boolean isValid = middleName == null || middleName.equals("") || ValidationPattern.MIDDLE_NAME.getPattern().
                matcher(middleName).matches();
        logger.debug("User middle name is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the name against room class name pattern.
     *
     * @param roomClassName room class name.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkRoomClassName(String roomClassName) {
        boolean isValid = roomClassName != null && ValidationPattern.ROOM_CLASS_NAME.getPattern().matcher(roomClassName).matches();
        logger.debug("Room class name is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the number against room number pattern.
     *
     * @param roomNumber room number.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkRoomNumber(String roomNumber) {
        boolean isValid = roomNumber != null && ValidationPattern.ROOM_NUMBER.getPattern().matcher(roomNumber).matches();
        logger.debug("Room number is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the name against room's name pattern.
     *
     * @param entityName room name.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkEntityName(String entityName) {
        boolean isValid = entityName != null && ValidationPattern.ENTITY_NAME.getPattern().matcher(entityName).matches();
        logger.debug("Entity name is valid: " + isValid);
        return isValid;
    }


    /**
     * Checks the e-mail against user's e-mail pattern.
     *
     * @param email user's e-mail.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkEmail(String email) {
        boolean isValid = email != null && ValidationPattern.EMAIL.getPattern().matcher(email).matches();
        logger.debug("User e-mail is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the password against user's password pattern.
     *
     * @param password user's password.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkPassword(String password) {
        boolean isValid = password != null && ValidationPattern.PASSWORD.getPattern().matcher(password).matches();
        logger.debug("User password is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the checkin/checkout/registration date against date pattern.
     *
     * @param date checkin/checkout/registration date
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkDate(String date) {
        boolean isValid = date != null && ValidationPattern.DATE.getPattern().matcher(date).matches();
        logger.debug("Date is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the checkin/checkout/registration date against date pattern.
     *
     * @param date checkin/checkout/registration date in Long
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkDateInLong(Long date) {
        // minus delta 2000 ms. (2 sec.) - bug in java.util.Date method getTime() convertation:
        // usually delta about plus 1000 ms. (1 sec)
        if (date == 0) {
            logger.debug("Date in long format is valid: false");
            return false;
        }
        Long currentDateInMiliseconds = DateService.getCurrentDateInMiliseconds();
        boolean isValid = date >= currentDateInMiliseconds;
        logger.debug("Date in long format is valid: " + isValid);
        return isValid;
    }


    /**
     * Checks the checkin and checkout date together.
     * User can not enter the hotel in hindsight, and
     * the date of departure can not precede the date of entry.
     *
     * @param checkinDate checkin date in Long
     * @param checkoutDate checkout date in Long
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkCheckinCheckoutDate(Long checkinDate, Long checkoutDate) {
        if (!checkDateInLong(checkinDate) || !checkDateInLong(checkoutDate)) {
            logger.debug("Checkin and/or checkput date is valid: false");
            return false;
        }
        Date currentDate = new Date();
        int daysCount = currentDate.getDay();
        int checkInDays = (int) (checkinDate / (1000 * 24 * 60 * 60));
        boolean isValid = (checkInDays - daysCount >= 0) && (checkoutDate - checkinDate > 0);

        logger.debug("Checkin and checkput dates is valid: " + isValid);
        return isValid;
    }


    /**
     * Check cost.
     *
     * @param cost cost of the room per night/room for few days.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkCost(double cost) {
        boolean isValid = cost > 0.0;
        logger.debug("Cost is valid: " + isValid);
        return isValid;
    }


    /**
     * Check room's assessment.
     *
     * @param assessment room by user.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkAssessment(byte assessment) {
        boolean isValid = assessment >= 1 && assessment <=5;
        logger.debug("Cost is valid: " + isValid);
        return isValid;
    }


    /**
     * Check count.
     *
     * @param count of facility.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkCount(double count) {
        boolean isValid = count > 0 && count < 10;
        logger.debug("Count is valid: " + isValid);
        return isValid;
    }

    /**
     * Check room's capacity.
     *
     * @param capacity room's capacity.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkCapacity(int capacity) {
        boolean isValid = capacity > 0 && capacity < 6;
        logger.debug("Capacity is valid: " + isValid);
        return isValid;
    }

    /**
     * Checks the username against user's username pattern.
     *
     * @param authority user's username.
     * @return {@code true} in case of success and false otherwise.
     */
    public boolean checkAuthority(String authority) {
        boolean isValid = authority != null && ValidationPattern.AUTHORITY.getPattern().matcher(authority).matches();
        logger.debug("Authority is valid: " + isValid);
        return isValid;
    }

}




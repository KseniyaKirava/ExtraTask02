package by.htp.kirova.task2.controller.command;


import by.htp.kirova.task2.entity.User;
import by.htp.kirova.task2.service.logic.UserLogic;
import by.htp.kirova.task2.service.BookingService;
import by.htp.kirova.task2.service.ServiceException;
import by.htp.kirova.task2.service.ServiceFactory;
import by.htp.kirova.task2.service.validation.Validator;
import by.htp.kirova.task2.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract class implementation for a
 * particular command type - Profile.
 *
 * @author Kseniya Kirava
 * @since Oct 14, 2018
 */
public class ProfileCommand extends Command {

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger LOGGER = Logger.getLogger(ProfileCommand.class);

    /**
     * The unique identification name constant.
     */
    private final static String USERNAME = "username";

    /**
     * The user's password constant.
     */
    private final static String PASSWORD = "password";

    /**
     * The user's e-mail constant.
     */
    private final static String EMAIL = "email";

    /**
     * The user's first name constant.
     */
    private final static String FIRST_NAME = "first_name";

    /**
     * The user's last name constant.
     */
    private final static String LAST_NAME = "last_name";

    /**
     * The user's middle name constant.
     */
    private final static String MIDDLE_NAME = "middle_name";


    @Override
    public Command execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = Util.getUserFromSession(request);
        if (user == null) {
            return CommandType.LOGIN.getCurrentCommand();
        } else {

            String currentPassword = user.getPassword();

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            BookingService<User> userService = serviceFactory.getUserService();


            request.setAttribute(USERNAME, user.getUsername());
            request.setAttribute(EMAIL, user.getEmail());
            request.setAttribute(PASSWORD, user.getPassword());
            request.setAttribute(FIRST_NAME, user.getFirstName());
            request.setAttribute(LAST_NAME, user.getLastName());
            request.setAttribute(MIDDLE_NAME, user.getMiddleName());

            if (request.getMethod().equalsIgnoreCase("post")) {
                if (request.getParameter("saveinfo") != null) {
                    String email = request.getParameter(EMAIL);
                    String password = request.getParameter(PASSWORD);
                    String first_name = request.getParameter(FIRST_NAME);
                    String last_name = request.getParameter(LAST_NAME);
                    String middle_name = request.getParameter(MIDDLE_NAME);
                    boolean passwordIsUpdated = !password.equals(currentPassword);

                    Validator validator = Validator.getInstance();
                    if (passwordIsUpdated && !validator.checkPassword(password)) {
                        return null;
                    }
                    if (!validator.checkEmail(email) || !validator.checkName(first_name) ||
                            !validator.checkName(last_name) || !validator.checkMiddleName(middle_name)) {
                        return null;
                    }
                    if (passwordIsUpdated) {
                        user.setPassword(UserLogic.getHashPassword(password));
                    }
                    user.setEmail(email);
                    user.setFirstName(first_name);
                    user.setLastName(last_name);
                    user.setMiddleName(middle_name);

                    boolean isUpdate;
                    try {
                        isUpdate = userService.update(user);
                    } catch (ServiceException e) {
                        LOGGER.info("Updating user failed", e);
                        throw new CommandException("Updating user failed", e);
                    }
                    if (isUpdate) {
                        LOGGER.info("Data from form successfully saved");
                    }
                    return CommandType.PROFILE.getCurrentCommand();
                }
                if (request.getParameter("logout") != null) {
                    request.getSession().invalidate();
                    return CommandType.LOGIN.getCurrentCommand();
                }
                if (request.getParameter("deletemyaccount") != null) {
                        user.setEnabled(false);
                        try {
                            userService.update(user);
                        } catch (ServiceException e) {
                            LOGGER.info("Deleting user failed", e);
                            throw new CommandException("Deleting user failed", e);
                        }
                        request.getSession().invalidate();
                        LOGGER.info("User successfully deleted");
                        return CommandType.LOGIN.getCurrentCommand();

                }

            }

        }
        return null;

    }


}




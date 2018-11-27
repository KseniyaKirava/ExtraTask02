package by.htp.kirova.task2.service.logic;

import by.htp.kirova.task2.entity.User;
import by.htp.kirova.task2.controller.command.CommandException;
import by.htp.kirova.task2.service.BookingService;
import by.htp.kirova.task2.service.ServiceException;
import by.htp.kirova.task2.service.ServiceFactory;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserLogic {

//    public static void main(String[] args) {
//        String pass = getHashPassword("45d45d");
//        System.out.println(pass);
//
//    }


    public static User checkLogin(String username, String password) throws CommandException {
        String hashPassword = getHashPassword(password);
        String where = String.format("WHERE username='%s' AND password='%s' AND enabled=true LIMIT 0,1", username, hashPassword);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        User user = null;

        try {
            List<User> users = serviceFactory.getUserService().read(where);
            if (users.size() > 0) {
                user = users.get(0);
            }
        } catch (ServiceException e) {
            throw new CommandException("", e);
        }

        return user;
    }

    public static String getHashPassword(String password) {
        String salt = "rand"; // генерация разной соли в классе SaltRandom
        String hashpass = DigestUtils.sha256Hex(password + salt);
        return hashpass;
    }

    public static boolean isUsernameUnique(String username){
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        BookingService<User> userService = serviceFactory.getUserService();
        List<User> list = null;
        try {
            list = userService.read("WHERE username like '" + username + "'");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return list.isEmpty() || list == null;
    }
}

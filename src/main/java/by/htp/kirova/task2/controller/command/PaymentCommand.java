package by.htp.kirova.task2.controller.command;

import by.htp.kirova.task2.entity.User;
import by.htp.kirova.task2.service.util.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract class implementation for a
 * particular command type - Payment.
 *
 * @author Kseniya Kirava
 * @since Oct 20, 2018
 */
public class PaymentCommand extends Command {

    @Override
    public Command execute(HttpServletRequest request, HttpServletResponse response) {
        User user = UserService.getUserFromSession(request);
        if (user == null) {
            return CommandType.LOGIN.getCurrentCommand();
        }
        else {
            request.setAttribute("totalCost", request.getSession().getAttribute("totalCost"));
            if (request.getMethod().equalsIgnoreCase("post")) {
                if (request.getParameter("confirm-purchase") != null) {
                    return CommandType.RESERVE.getCurrentCommand();
                }
            }
        }

        return null;
    }
}

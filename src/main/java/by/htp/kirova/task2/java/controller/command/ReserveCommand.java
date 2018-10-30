package by.htp.kirova.task2.java.controller.command;

import by.htp.kirova.task2.java.entity.User;
import by.htp.kirova.task2.java.service.HelperService;
import by.htp.kirova.task2.java.service.ServiceException;
import by.htp.kirova.task2.java.service.ServiceFactory;
import by.htp.kirova.task2.java.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class implementation for a
 * particular command type - Reserve.
 *
 * @author Kseniya Kirava
 * @since Oct 20, 2018
 */
public class ReserveCommand extends Command{

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger LOGGER = Logger.getLogger(ReserveCommand.class);


    @Override
    public Command execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
            User user = Util.getUserFromSession(request);
            if (user == null) {
                return CommandType.LOGIN.getCurrentCommand();
            } else {
                String username = user.getUsername();

                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                HelperService helperService = serviceFactory.getHelperService();
                List<ArrayList<Object>> reservations;

                try {
                    reservations = helperService.showAllReservations("\'" + username + "\' ORDER BY res.reservation_date");
                    request.getSession().setAttribute("size", reservations.size());
                    String strStart = request.getParameter("start");
                    int startReq = 0;
                    if (strStart != null) {
                        startReq = Integer.parseInt(strStart);
                    }
                    String where = String.format(" LIMIT %d, 10", startReq);
                    reservations = helperService.showAllReservations("\'" + username + "\' ORDER BY res.reservation_date" + where);
                    request.getSession().setAttribute("reservations", reservations);
                } catch (ServiceException e) {
                    LOGGER.error("Reservations read error");
                    throw new CommandException(e);
                }

            }

            return null;

        }
}
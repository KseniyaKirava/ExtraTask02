package by.htp.kirova.task2.java.controller.command;


import by.htp.kirova.task2.java.util.DateConverter;
import by.htp.kirova.task2.java.entity.Request;
import by.htp.kirova.task2.java.entity.Reservation;
import by.htp.kirova.task2.java.entity.User;
import by.htp.kirova.task2.java.service.GenericService;
import by.htp.kirova.task2.java.service.ServiceException;
import by.htp.kirova.task2.java.service.ServiceFactory;
import by.htp.kirova.task2.java.util.Util;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Abstract class implementation for a
 * particular command type - Reservation.
 *
 * @author Kseniya Kirava
 * @since Oct 14, 2018
 */
public class ReservationCommand extends Command {

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger LOGGER = Logger.getLogger(ReservationCommand.class);

    @Override
    public Command execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = Util.getUserFromSession(request);

        if (user == null) {
            return CommandType.LOGIN.command;
        } else {
            String username = user.getUsername();
            String currentPassword = user.getPassword();
            Cookie cookie = new Cookie(username, currentPassword);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            LOGGER.info("Cookie successfully added");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            GenericService<Reservation> reservationService = serviceFactory.getReservationService();

            long requests_Id = (long) (request.getSession().getAttribute("requestId"));
            ArrayList room = new ArrayList();
            try {
                room = serviceFactory.getHelperService().showAvialiableRooms(requests_Id);
            } catch (ServiceException e) {
                LOGGER.error("Parsing requestId from quert error", e);
                throw new CommandException("Parsing requestId from quert error", e);
            }
            if (!room.isEmpty()) {
                long reservation_date = DateConverter.getCurrentDateInMiliseconds();
                long checkin_date = (long) (room.get(1));
                long checkout_date = (long) (room.get(2));
                double cost = (double) room.get(7);
                double total_cost = Util.getTotalCost(checkin_date, checkout_date, cost);
                int rooms_id = (int) room.get(4);
                int room_class = (int) room.get(3);
                Reservation reservation = new Reservation(0, reservation_date, checkin_date, checkout_date,
                        total_cost, true, requests_Id, username, rooms_id, room_class);

                boolean isCreated;
                try {
                    isCreated = reservationService.create(reservation);
                } catch (ServiceException e) {
                    LOGGER.info("Creating user failed", e);
                    throw new CommandException("Creating user failed", e);
                }
                if (isCreated) {
                    LOGGER.info("Reservation successfully created");
                }


                String room_name = (String) room.get(5);
                String room_number = (String) room.get(6);
                int capacity = (int) room.get(0);

                request.setAttribute("room_name", room_name);
                request.setAttribute("room_number", room_number);
                request.setAttribute("capacity", capacity);
                request.setAttribute("check_in", checkin_date);
                request.setAttribute("check_out", checkout_date);
                request.setAttribute("total_cost", total_cost);
            }
        }
        return null;
    }
}

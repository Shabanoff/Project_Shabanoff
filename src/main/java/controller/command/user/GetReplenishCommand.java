package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import controller.util.validator.UserNumberValidator;
import entity.User;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by JohnUkraine on 26/5/2018.
 */
public class GetReplenishCommand implements ICommand {
    private final static String NO_SUCH_USER = "account.not.exist";
    private final static String USER_STATUS_NOT_ACTIVE = "account.not.active";
    private final UserService userService = ServiceFactory.getUserService();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        validateRequestData(request, errors);
        validateAccount(request, errors);

        if (errors.isEmpty()) {
            User user = getUserFromSession(request.getSession());
            Long refillableUserNumber = getUserFromRequest(request);

            User refillableUser = userService.findUserByNumber(
                    refillableUserNumber).get();
            List<User> refillableUsers = new ArrayList<>();
            refillableUsers.add(refillableUser);


            return Views.REPLENISH_VIEW;
        }
        request.setAttribute(Attributes.ERRORS, errors);

        return Views.INFO_VIEW;
    }

    private void validateRequestData(HttpServletRequest request, List<String> errors) {
        Util.validateField(new UserNumberValidator(),
                request.getParameter(Attributes.REFILLABLE_USER), errors);
    }

    private void validateAccount(HttpServletRequest request, List<String> errors) {
        Optional<User> user = userService.findUserByNumber(
                Long.valueOf(request.getParameter(Attributes.REFILLABLE_USER)));

        if (!user.isPresent()) {
            errors.add(NO_SUCH_USER);
            return;
        }

        if (!user.get().isActive()) {
            errors.add(USER_STATUS_NOT_ACTIVE);
        }
    }

    private Long getUserFromRequest(HttpServletRequest request) {
        return Long.valueOf(request.getParameter(Attributes.REFILLABLE_USER));
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}

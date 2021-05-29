package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
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
import java.util.ResourceBundle;

public class PostSignupCommand implements ICommand {
    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";

    private final static String USER_ALREADY_EXISTS = "user.exists";

    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, bundle.
                    getString("home.path"));
            return REDIRECTED;
        }

        User userDto = getDataFromRequest(request);
        List<String> errors = validateData(userDto);

        if(errors.isEmpty()) {
            User createdUser = userService.createUser(userDto);

            addUserToSession(request.getSession(), createdUser);

            Util.redirectTo(request, response, bundle.
                    getString("home.path"));

            return REDIRECTED;
        }

        addInvalidDataToRequest(request, userDto, errors);

        return Views.SIGNUP_VIEW;


    }

    private User getDataFromRequest(HttpServletRequest request) {
        return User.newBuilder()
                .addLogin(request.getParameter(LOGIN_PARAM))
                .addPassword(request.getParameter(PASSWORD_PARAM))
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);

        if(errors.isEmpty() && userService.isUserExists(user)) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER, user);
        request.setAttribute(Attributes.ERRORS, errors);
    }

    private void addUserToSession(HttpSession session, User user){
        session.setAttribute(Attributes.USER, user);
    }
}

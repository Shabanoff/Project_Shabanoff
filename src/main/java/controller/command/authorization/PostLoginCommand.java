package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class PostLoginCommand implements ICommand {
    private final static Logger logger = LogManager.getLogger(PostLoginCommand.class);

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String INVALID_CREDENTIALS =
            "invalid.credentials";
    private final static String ACTIVE_ACCOUNT_IS_EXIST =
            "active.user.exist";
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, bundle.
                    getString("account.path"));
            return REDIRECTED;
        }

        User userDto = getDataFromRequest(request);

        List<String> errors = validateData(userDto);
        errors.addAll(
                validateUniquenessActiveUser(request.getSession(), userDto));

        if (errors.isEmpty()) {
            logger.info("LOGGIN WITHOUT ERRORS!");
            User user = loadUserFromDatabase(userDto.getLogin());
            addUserToContext(request.getSession(), user);
            addUserToSession(request.getSession(), user);
            if (user.isManager()) {
                Util.redirectTo(request, response, bundle.
                        getString("users.path"));
            }else {
                Util.redirectTo(request, response, bundle.
                        getString("account.path"));
            }

            return REDIRECTED;
        }
        logger.info("LOGGIN HAS ERRORS!");
        addInvalidDataToRequest(request, userDto, errors);

        return Views.LOGIN_VIEW;
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

        /* Check if entered password matches with user password only in case,
            when email and password is valid
        */
        if (errors.isEmpty() && !userService.
                isCredentialsValid(user.getLogin(), user.getPassword())) {
            errors.add(INVALID_CREDENTIALS);
        }

        return errors;
    }

    public List<String> validateUniquenessActiveUser(HttpSession session, User user) {
        List<String> errors = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Map<String, User> activeUserList = (Map<String, User>) session.getServletContext().
                getAttribute(Attributes.USER_LIST);
        if (activeUserList.get(user.getLogin()) != null)
            errors.add(ACTIVE_ACCOUNT_IS_EXIST);
        return errors;
    }

    private User loadUserFromDatabase(String email) {
        Optional<User> userOptional = userService.findByLogin(email);
        return userOptional.orElseThrow(IllegalStateException::new);
    }

    private void addUserToSession(HttpSession session, User user) {
        session.setAttribute(Attributes.USER, user);
    }

    private void addUserToContext(HttpSession session, User user) {
        @SuppressWarnings("unchecked")
        Map<String, User> activeUserList = (Map<String, User>) session.getServletContext().
                getAttribute(Attributes.USER_LIST);
        activeUserList.put(user.getLogin(), user);
    }

    private void addInvalidDataToRequest(HttpServletRequest request,
                                         User user,
                                         List<String> errors) {
        request.setAttribute(Attributes.USER, user);
        request.setAttribute(Attributes.ERRORS, errors);
    }

}

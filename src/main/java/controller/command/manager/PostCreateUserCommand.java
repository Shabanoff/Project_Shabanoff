package controller.command.manager;

import controller.command.ICommand;
import controller.command.authorization.PostLoginCommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostCreateUserCommand implements ICommand {
    private final static Logger logger = LogManager.getLogger(PostCreateUserCommand.class);

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);

        UserService userService = ServiceFactory.getUserService();
        List<String> errors = userService.createUser(login, password);

        if (errors.isEmpty()){
            List<User> users =  userService.findAllUser();
            request.setAttribute(Attributes.USERS, users);
            return Views.MANAGER_VIEW;
        }
        logger.info("LOGGIN HAS ERRORS!");
        addInvalidDataToRequest(request,  errors);

        return Views.CREATE_USER_VIEW;
    }
    private void addInvalidDataToRequest(HttpServletRequest request, List<String> errors) {
        request.setAttribute(Attributes.ERRORS, errors);
    }
}

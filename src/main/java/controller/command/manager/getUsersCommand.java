package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Views;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getUsersCommand implements ICommand {
    UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.userPagination(request);
        return Views.USERS_VIEWS;
    }
}

package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Views;
import entity.Status;
import entity.User;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PostUnblockCommand implements ICommand {
    private static final UserService userService = ServiceFactory.getUserService();
    private static final String USER_ID = "userId";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> currentUser = getUser(request);
        currentUser.ifPresent(user -> userService.updateUserStatus(user, Status.StatusIdentifier.ACTIVE_STATUS.getId()));

        userService.userPagination(request);
        return Views.USERS_VIEW;
    }
    private Optional<User> getUser(HttpServletRequest request){
        return userService.findUserByNumber(
                Long.parseLong(request.getParameter(USER_ID)));
    }
}

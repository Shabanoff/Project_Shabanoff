package controller.command.authorization;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static controller.util.constants.Views.LOGIN_VIEW;

public class GetLoginCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        if(Util.isAlreadyLoggedIn(request.getSession())) {
            Util.redirectTo(request, response, ResourceBundle.
                    getBundle(Views.PAGES_BUNDLE).getString("home.path"));
            return REDIRECTED;
        }

        return LOGIN_VIEW;
    }
}

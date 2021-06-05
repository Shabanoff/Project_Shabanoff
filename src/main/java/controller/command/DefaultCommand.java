package controller.command;

import controller.util.Util;
import controller.util.constants.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

public class DefaultCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Util.redirectTo(request, response, ResourceBundle
                .getBundle(Views.PAGES_BUNDLE)
                .getString("home.path"));
        return REDIRECTED;
    }
}


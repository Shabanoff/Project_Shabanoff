package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JohnUkraine on 26/5/2018.
 */
public class GetReplenishCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        return Views.REPLENISH_VIEW;
    }

}

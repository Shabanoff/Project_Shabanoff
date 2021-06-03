package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import service.IncludedOptionService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetIncludedOptionCommand implements ICommand {
    IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute(Attributes.OPTIONS, includedOptionService.findAllIncludedOption());
        return Views.OPTION_VIEW;
    }
}
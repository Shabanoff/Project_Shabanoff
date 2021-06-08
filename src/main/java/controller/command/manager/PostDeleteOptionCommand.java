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

public class PostDeleteOptionCommand implements ICommand {
    private static final String OPTION_ID = "optionId";
    private static final IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        includedOptionService.deleteIncludedOptionByNumber(Long.parseLong(request.getParameter(OPTION_ID)));
        request.setAttribute(Attributes.OPTIONS, includedOptionService.findAllIncludedOption());
        return Views.OPTION_VIEW;
    }
}

package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostCreateServiceCommand implements ICommand {
    private static final  String SERVICE_NAME = "name";
    private static final  ServiceForService serviceForService = ServiceFactory.getServiceService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serviceForService.createService(request.getParameter(SERVICE_NAME));
        request.setAttribute(Attributes.SERVICES, serviceForService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

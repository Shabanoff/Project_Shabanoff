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

public class PostDeleteServiceCommand implements ICommand {
    ServiceForService serviceService = ServiceFactory.getServiceService();
    private final String SERVICE_ID_PARAM = "serviceId";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serviceService.deleteService(Long.parseLong(request.getParameter(SERVICE_ID_PARAM)));
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

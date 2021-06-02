package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.Service;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetServiceCommand implements ICommand {

    private final ServiceForService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Service> services = serviceService.findAllService();
        request.setAttribute(Attributes.SERVICES, services);

        return Views.SERVICE_VIEW;
    }
}

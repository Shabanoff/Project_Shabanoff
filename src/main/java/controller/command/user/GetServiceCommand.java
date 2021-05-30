package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.IncludedPackage;
import entity.Service;
import entity.Tariff;
import service.ServiceFactory;
import service.ServiceService;
import service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetServiceCommand implements ICommand {

    private final TariffService tariffService = ServiceFactory.getTariffService();
    private final ServiceService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Service> services = serviceService.findAllService();
        //List<Tariff> tariffs = tariffService.findByService();


       // request.setAttribute(Attributes.TARIFFS, tariffs);
        request.setAttribute(Attributes.SERVICES, services);


        return Views.SERVICE_VIEW;
    }
}

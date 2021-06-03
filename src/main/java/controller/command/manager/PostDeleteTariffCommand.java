package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import service.ServiceFactory;
import service.ServiceForService;
import service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostDeleteTariffCommand implements ICommand {
    ServiceForService serviceService = ServiceFactory.getServiceService();
    TariffService tariffService = ServiceFactory.getTariffService();
    private final String TARIFF_ID_PARAM = "tariffId";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tariffService.deleteTariff(
                Long.parseLong(request.getParameter(TARIFF_ID_PARAM)));
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

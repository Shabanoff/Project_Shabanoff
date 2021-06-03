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
import java.math.BigDecimal;

public class PostChangeTariffCostCommand implements ICommand {
    ServiceForService serviceService = ServiceFactory.getServiceService();
    private final String COST_PARAM = "newCost";
    private final String TARIFF_ID_PARAM = "tariffId";

    TariffService tariffService = ServiceFactory.getTariffService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tariffService.changeCost(tariffService.findTariffById(
                Long.parseLong(request.getParameter(TARIFF_ID_PARAM))).get(),
                new BigDecimal(request.getParameter(COST_PARAM)));
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

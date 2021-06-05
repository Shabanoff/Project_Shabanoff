package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import service.IncludedOptionService;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetCreateTariffCommand implements ICommand {
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        request.setAttribute(Attributes.OPTIONS, includedOptionService.findAllIncludedOption());
        return Views.CREATE_TARIFF_VIEW;
    }
}

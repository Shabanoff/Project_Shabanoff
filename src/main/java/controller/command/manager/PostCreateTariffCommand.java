package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.IncludedOption;
import entity.Service;
import entity.Tariff;
import service.IncludedOptionService;
import service.ServiceFactory;
import service.ServiceForService;
import service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PostCreateTariffCommand implements ICommand {
    private final ServiceForService serviceService = ServiceFactory.getServiceService();
    private final IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();
    private final static String TARIFF_NAME_PARAM = "tariffName";
    private final static String COST_PARAM = "cost";
    private final static String SERVICE_ID_PARAM = "serviceId";
    private final static String OPTION_ID_PARAM = "optionId";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TariffService tariffService = ServiceFactory.getTariffService();
        tariffService.createTariff(getDataFromRequestCreating(request));

        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }

    private Tariff getDataFromRequestCreating(HttpServletRequest request) {
        String tariffName = request.getParameter(TARIFF_NAME_PARAM);
        BigDecimal cost = new BigDecimal(request.getParameter(COST_PARAM));
        long serviceId = Long.parseLong(request.getParameter(SERVICE_ID_PARAM));
        long optionId = Long.parseLong(request.getParameter(OPTION_ID_PARAM));

        List<IncludedOption> includedOptions= new ArrayList<>();
        includedOptions.add(includedOptionService.findIncludedOptionByNumber(optionId).get());

        return Tariff.newBuilder()
                .addTariffName(tariffName)
                .addCost(cost)
                .addService(serviceService.findServiceById(serviceId).get())
                .addIncludedOptions(includedOptions)
                .build();
    }
}

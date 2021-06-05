package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import entity.IncludedOption;
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
import java.util.Optional;

public class PostCreateTariffCommand implements ICommand {
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final IncludedOptionService includedOptionService = ServiceFactory.getIncludedOptionService();
    private static final String TARIFF_NAME_PARAM = "tariffName";
    private static final String COST_PARAM = "cost";
    private static final String SERVICE_ID_PARAM = "serviceId";
    private static final String OPTION_ID_PARAM = "optionId";

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
        String[] optionsId = request.getParameterValues(OPTION_ID_PARAM);
        List<IncludedOption> includedOptions = new ArrayList<>();
        for (String optionId : optionsId) {
            Optional<IncludedOption> includedOption = includedOptionService.findIncludedOptionByNumber(Long.parseLong(optionId));
            includedOption.ifPresent(includedOptions::add);
        }


        return Tariff.newBuilder()
                .addTariffName(tariffName)
                .addCost(cost)
                .addService(serviceService.findServiceById(serviceId).get())
                .addIncludedOptions(includedOptions)
                .build();
    }
}

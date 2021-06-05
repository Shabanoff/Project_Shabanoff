package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;
import service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostDeleteTariffCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(PostDeleteTariffCommand.class);
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();
    private static final TariffService tariffService = ServiceFactory.getTariffService();
    private static final String TARIFF_ID_PARAM = "tariffId";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = tariffService.deleteTariff(
                Long.parseLong(request.getParameter(TARIFF_ID_PARAM)));
        if (!errors.isEmpty()) {
            logger.info("Tariff has already add to user");
            request.setAttribute(Attributes.ERRORS, errors);
        }
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

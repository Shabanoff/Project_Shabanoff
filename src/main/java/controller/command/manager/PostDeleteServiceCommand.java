package controller.command.manager;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostDeleteServiceCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(PostDeleteServiceCommand.class);
    private static final String SERVICE_ID_PARAM = "serviceId";
    private static final ServiceForService serviceService = ServiceFactory.getServiceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = serviceService.deleteService(Long.parseLong(request.getParameter(SERVICE_ID_PARAM)));
        if (!errors.isEmpty()) {
            logger.info("Service has already add to user");
            request.setAttribute(Attributes.ERRORS, errors);
        }
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

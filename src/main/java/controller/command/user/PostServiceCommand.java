package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import entity.Tariff;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.TariffService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static controller.util.constants.Views.SERVICE_VIEW;

public class PostServiceCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(PostServiceCommand.class);
    private static final String TARIFF_ID_PARAM = "tariffId";

    private static final TariffService tariffService = ServiceFactory.getTariffService();
    private static final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());
        Optional<Tariff> addedTariff = getTariff(request);
        if (addedTariff.isPresent()) {
            List<String> errors = userService.addingTariff(currentUser, addedTariff.get());
            request.setAttribute(Attributes.SERVICES, ServiceFactory.getServiceService().findAllService());
            if (!errors.isEmpty()) {
                request.setAttribute(Attributes.ERRORS, errors);
                logger.info("Adding tariff HAS ERRORS!");
            }
        }
        return SERVICE_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }

    private Optional<Tariff> getTariff(HttpServletRequest request) {
        return tariffService.findTariffById(
                Long.parseLong(request.getParameter(TARIFF_ID_PARAM)));
    }

}

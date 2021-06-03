package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import entity.Tariff;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static controller.util.constants.Views.SERVICE_VIEW;

public class PostServiceCommand implements ICommand {
    private final static Logger logger = LogManager.getLogger(PostServiceCommand.class);
    private final String TARIFF_ID_PARAM = "tariffId";

    private final TariffService tariffService = ServiceFactory.getTariffService();
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());
        Optional<Tariff> addingTariff = getTariff(request);
        List<String> errors = userService.addingTariff(currentUser, addingTariff);
        request.setAttribute(Attributes.SERVICES, ServiceFactory.getServiceService().findAllService());
        if(!errors.isEmpty()){
            request.setAttribute(Attributes.ERRORS, errors);
            logger.info("Adding tariff HAS ERRORS!");
        }
        return SERVICE_VIEW;
    }
    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }

    private Optional<Tariff> getTariff(HttpServletRequest request){
        return tariffService.findTariffById(
                Long.parseLong(request.getParameter(TARIFF_ID_PARAM)));
    }

}

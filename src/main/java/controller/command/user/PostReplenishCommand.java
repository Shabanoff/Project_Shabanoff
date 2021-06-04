package controller.command.user;

import controller.command.ICommand;
import controller.util.Util;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import controller.util.validator.AmountValidator;
import entity.IncludedPackage;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IncludedPackageService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static controller.util.constants.Views.ACCOUNT_VIEW;

public class PostReplenishCommand implements ICommand {
    private final String AMOUNT = "balance";
    private final IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();
    private final UserService userService = ServiceFactory.getUserService();
    private final static Logger logger = LogManager.getLogger(PostReplenishCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());
        List<String> errors = validateDataFromRequest(request);

        if (errors.isEmpty()) {
            BigDecimal amount = new BigDecimal(request.getParameter(AMOUNT));
            userService.increaseUserBalance(currentUser, amount);
            List<IncludedPackage> includedPackages = includedPackageService.findByUser(currentUser.getId());
            request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackages);
            return ACCOUNT_VIEW;
        }
        logger.info("LOGGIN HAS ERRORS!");
        request.setAttribute(Attributes.ERRORS, errors);

        return Views.REPLENISH_VIEW;


    }
    private List<String> validateDataFromRequest(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
       Util.validateField(new AmountValidator(),
                request.getParameter(Attributes.AMOUNT), errors);
        return errors;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}

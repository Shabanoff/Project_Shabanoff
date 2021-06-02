package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import entity.IncludedPackage;
import entity.User;
import service.IncludedPackageService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static controller.util.constants.Views.ACCOUNT_VIEW;
import static controller.util.constants.Views.HOME_VIEW;

public class GetAccountCommand implements ICommand {

    private final IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());
        List<IncludedPackage> includedPackages = includedPackageService.findByUser(currentUser.getId());
        currentUser.setIncludedPackages(includedPackages);
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackages);


        return ACCOUNT_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}
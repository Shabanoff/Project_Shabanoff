package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import entity.IncludedPackage;
import entity.User;
import service.IncludedPackageService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static controller.util.constants.Views.ACCOUNT_VIEW;

public class PostAccountCommand implements ICommand {
    private static final IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();
    private static final String INCLUDED_PACKAGE_ID = "includedPackageId";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getUserFromSession(request.getSession());

        List<IncludedPackage> includedPackages = includedPackageService.findByUser(currentUser.getId());
        currentUser.setIncludedPackages(includedPackages);
        includedPackageService.deleteIncludedPackage(Long.valueOf(request.getParameter(INCLUDED_PACKAGE_ID)));
        request.setAttribute(Attributes.INCLUDED_PACKAGES, includedPackages);


        return ACCOUNT_VIEW;
    }

    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(Attributes.USER);
    }
}

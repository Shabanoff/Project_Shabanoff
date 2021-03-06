package controller.command.user;

import controller.command.ICommand;
import controller.util.constants.Attributes;
import controller.util.constants.Views;
import service.CreatePdfService;
import service.ServiceFactory;
import service.ServiceForService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class PostServicePdfCommand implements ICommand {
    CreatePdfService createPdfService = ServiceFactory.getCreatePdfService();
    ServiceForService serviceService = ServiceFactory.getServiceService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=response.pdf");
        try(OutputStream out = response.getOutputStream()) {
        createPdfService.createServiceInPdf(out);
        }
        request.setAttribute(Attributes.SERVICES, serviceService.findAllService());
        return Views.SERVICE_VIEW;
    }
}

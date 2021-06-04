package controller.command.manager;

import controller.command.ICommand;
import dao.abstraction.UserDao;
import dao.impl.mysql.DefaultDaoImpl;
import entity.User;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class getUsersCommand extends HttpServlet {
    UserService userService = ServiceFactory.getUserService();
    private DefaultDaoImpl<User> defaultDao;
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<User> list = userService.findUsers((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = defaultDao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("users", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher view = request.getRequestDispatcher("users.jsp");
        view.forward(request, response);
    }
}

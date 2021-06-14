package controller.filter;

import controller.util.constants.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PRGFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        request.setAttribute(Attributes.ERRORS,session.getAttribute(Attributes.ERRORS));
        session.removeAttribute(Attributes.ERRORS);

        request.setAttribute(Attributes.MESSAGES,session.getAttribute(Attributes.MESSAGES));
        session.removeAttribute(Attributes.MESSAGES);

        request.setAttribute(Attributes.WARNING,session.getAttribute(Attributes.WARNING));
        session.removeAttribute(Attributes.WARNING);

        request.setAttribute(Attributes.AMOUNT,session.getAttribute(Attributes.AMOUNT));
        session.removeAttribute(Attributes.AMOUNT);

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

}

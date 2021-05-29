package controller.i18n.filter;

import controller.listener.UniquenessCheckSessionListener;
import javax.servlet.*;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncodingFilter implements Filter {
    private static final String ENCODING = "utf-8";
    private final Logger logger = LogManager.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("I`m a LOGGER!");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

package controller.customTags;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SelectedPageTagHandler extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        HttpServletRequest request = getRequest();
        sendViewUriToJsp(request);
    }

    /**
     * Get request from jspContext
     *
     * @return httpRequest
     */
    private HttpServletRequest getRequest() {
        PageContext pageContext = (PageContext) getJspContext();
        return (HttpServletRequest) pageContext.getRequest();
    }

    /**
     * Writes URI of current view to out stream of jspContext
     *
     * @param request
     * @throws IOException
     */
    private void sendViewUriToJsp(HttpServletRequest request)
            throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print(request.getRequestURI());
    }
}

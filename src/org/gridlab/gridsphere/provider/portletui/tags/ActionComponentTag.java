package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.ActionComponentBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class ActionComponentTag extends IncludeTag {

    private static PortletLog log = SportletLog.getInstance(ActionComponentTag.class);
    private String activeCompId = "";

    protected String getActiveComponentId() {
        return activeCompId;
    }

    protected void setActionComponentId(String compId) {
        this.activeCompId = compId;
    }

    protected void includePage() {
        //log.debug("includePage(" + page + ")");

        RequestDispatcher rd = servletContext.getRequestDispatcher(page);
        ServletRequest request = pageContext.getRequest();
        ServletResponse response = pageContext.getResponse();

        String baseCompId = (String)request.getAttribute(SportletProperties.GP_COMPONENT_ID);

        if (includeBean != null) {
            //log.debug("Using active component id ");
            activeCompId = ((ActionComponentBean)includeBean).getActiveComponentId();
        } else {
            //log.debug("Using request component id ");
            activeCompId = (String)request.getAttribute(SportletProperties.GP_COMPONENT_ID);
        }
        //log.debug("Changing component id from " + baseCompId + " to " + activeCompId);
        request.setAttribute(SportletProperties.GP_COMPONENT_ID, activeCompId);
        try {

            // Very important here... must pass it the appropriate jsp writer!!!
            // Or else this include won't be contained within the parent content
            // but either before or after it.
            //rd.include(request, new ServletResponseWrapperInclude(response, pageContext.getOut()));
            rd.include(request, new StoredPortletResponseImpl((HttpServletResponse)response, pageContext.getOut()));
            //rd.include(pageContext.getRequest(), pageContext.getResponse());
        } catch (Exception e) {
            log.error("Unable to include page ", e);
        }
        //log.debug("Resetting component id to " + baseCompId);
        request.setAttribute(SportletProperties.GP_COMPONENT_ID, baseCompId);
    }
}
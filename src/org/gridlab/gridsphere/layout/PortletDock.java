/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;

public class PortletDock extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletDock.class);

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation;
    private String title = "";
    private PortletInsets margin;
    //private List components = new Vector();

    public PortletDock() {}

    public PortletDock(String title) {
        this.title = title;
    }

    public PortletDock(int orientation) {
        this.orientation = orientation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getComponentIndex(PortletComponent c) {
        return components.indexOf(c);
    }

    public PortletComponent getComponentAtIndex(int i) {
        return (PortletComponent)components.get(i);
    }

    public void setMargin(PortletInsets margin) {
        this.margin = margin;
    }

    public PortletInsets getMargin() {
        return margin;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.write("<tr><td>" + title);
        ListIterator compIt = components.listIterator();
        while (compIt.hasNext()) {
            PortletRender action = (PortletRender)compIt.next();
            action.doRender(event);
            margin.doRender(event);
        }
        out.write("</td></tr>");
    }

}








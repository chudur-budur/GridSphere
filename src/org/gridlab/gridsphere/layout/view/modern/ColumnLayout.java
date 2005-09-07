/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.view.modern;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;


public class ColumnLayout extends BaseRender implements Render {

    protected static final StringBuffer TOP_COLUMN = new StringBuffer("\n<!-- START COLUMN --><div>");
    protected static final StringBuffer TOP_COLUMN_BORDER = new StringBuffer("\n<div>");
    protected static final StringBuffer BOTTOM_COLUMN_BORDER = new StringBuffer("</div>\n");
    protected static final StringBuffer BOTTOM_COLUMN = new StringBuffer("</div> <!-- END COLUMN -->\n");

    public ColumnLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return TOP_COLUMN;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        return TOP_COLUMN_BORDER;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_COLUMN_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_COLUMN;
    }



}




/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Iterator;
import java.util.Map;

public interface ConcretePortletApp {

    public String getPortletAppName();

    public Map getContextParam();

    public ConcretePortlet[] getConcretePortlet();

}

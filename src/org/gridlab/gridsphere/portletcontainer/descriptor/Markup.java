/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.exolab.castor.types.AnyNode;

import java.util.ArrayList;
import java.util.List;

public class Markup {

    private String name;
    private List portletModes = new ArrayList();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List getPortletModes() {
        return portletModes;
    }

    public void setPortletModes(ArrayList portletModes) {
        this.portletModes = portletModes;
    }

    public String[] getPortletModesAsStrings() {
        String[] modes = new String[portletModes.size()];
        for (int i = 0; i < portletModes.size(); i++) {
            AnyNode a = (AnyNode) portletModes.get(i);
            modes[i] = a.getLocalName();
        }
        return modes;
    }
}


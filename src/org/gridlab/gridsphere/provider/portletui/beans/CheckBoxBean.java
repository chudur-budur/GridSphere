/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class CheckBoxBean extends SelectElementBean {

    public CheckBoxBean(String name, String value, boolean selected, boolean disabled) {
        super(name, value, selected, disabled);
    }

    public String toString() {
        return super.toString("checkbox");
    }

}

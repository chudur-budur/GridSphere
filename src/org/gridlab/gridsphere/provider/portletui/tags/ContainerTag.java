/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridlab.gridsphere.provider.portletui.beans.BeanContainer;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import java.util.Iterator;
import java.util.List;

/**
 * The abstract <code>ContainerTag</code> provides a bean container used by other tags that are themselves containers
 * for nested tags.
 */
public abstract class ContainerTag extends BaseComponentTag {

    protected List list = null;

    /**
     * Adds a tag bean to the container
     *
     * @param tagBean a tag bean
     */
    public void addTagBean(TagBean tagBean) {
        list.add(tagBean);
    }

    /**
     * Removes a tag bean from the container
     *
     * @param tagBean a tag bean
     */
    public void removeTagBean(TagBean tagBean) {
        list.remove(tagBean);
    }

    /**
     * Returns a list of tag beans
     *
     * @return a list of tag beans
     */
    public List getTagBeans() {
        return list;
    }

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void doEndTag(BeanContainer beanContainer) throws JspException {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BaseComponentBean bc = (BaseComponentBean)it.next();
            beanContainer.addBean(bc);
        }
    }

}

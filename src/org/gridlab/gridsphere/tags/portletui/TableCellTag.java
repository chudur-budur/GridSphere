package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableCellTag extends ContainerTag {

    protected TableCellBean cellBean = null;
    protected String width = null;
    protected String cellSpacing = null;

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public void setCellBean(TableCellBean cellBean) {
        this.cellBean = cellBean;
    }

    public TableCellBean getCellBean() {
        return cellBean;
    }

    public int doStartTag() throws JspException {

        list = new Vector();

        if (!beanId.equals("")) {
            cellBean = (TableCellBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (cellBean == null) cellBean = new TableCellBean();
        } else {
            cellBean = new TableCellBean();
        }

        ContainerTag rowTag = (ContainerTag)getParent();
        if (rowTag == null) return SKIP_BODY;
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        if (cellSpacing != null) cellBean.setCellSpacing(cellSpacing);
        if (width != null) cellBean.setWidth(width);
        ContainerTag rowTag = (ContainerTag)getParent();
        if (rowTag != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                BaseComponentBean bean = (BaseComponentBean)it.next();
                if (bean.toString() != null) {
                    cellBean.addBean(bean);
                    cellBean.setCssStyle(bean.getCssStyle());
                }
            }
            rowTag.addTagBean(cellBean);
        }
        return EVAL_BODY_INCLUDE;
    }
}

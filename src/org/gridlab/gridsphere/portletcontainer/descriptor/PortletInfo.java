/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class PortletInfo {

    private String Href = new String();
    private String Id = new String();
    private String Name = new String();
    private List portletConfig = new Vector();
    private CacheInfo cacheInfo = new CacheInfo();
    private AllowsWindowStates allowsWindowStates = new AllowsWindowStates();
    private SupportsModes supportsModes = new SupportsModes();

    /**
     * gets the href of a PortletInfo
     *
     * @returns href
     */
    public String getHref() {
        return Href;
    }

    /**
     * sets the href of a portlet
     *
     * @param Href the Href of the portlet
     */
    public void setHref(String Href) {
        this.Href = Href;
    }

    /**
     * gets the id of a portlet
     *
     * @return id of the portlet
     */
    public String getId() {
        return Id;
    }

    /**
     * sets the id of a portlet
     *
     * @param id id of the portlet
     */
    public void setId(String Id) {
        this.Id = Id;
    }


    /**
     * gets the name of the portlet
     *
     * @returns name of the portlet
     */
    public String getName() {
        return Name;
    }

    /**
     * sets the name of the portlet
     *
     * @param name name of the portlet
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    public List getConfigParamList() {
        return portletConfig;
    }

    public void setConfigParamList(Vector portletConfig) {
        this.portletConfig = portletConfig;
    }

    public CacheInfo getCacheInfo() {
        return cacheInfo;
    }

    public void setCacheInfo(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    public void setAllowsWindowStates(AllowsWindowStates allowsWindowStates) {
        this.allowsWindowStates = allowsWindowStates;
    }

    public AllowsWindowStates getAllowsWindowStates() {
        return allowsWindowStates;
    }

    public void setSupportsModes(SupportsModes supportModes) {
        this.supportsModes = supportModes;
    }

    public SupportsModes getSupportsModes() {
        return supportsModes;
    }
}


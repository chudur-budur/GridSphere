/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal.impl;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * Portal configuration service is used to manage portal administrative settings
 */
public class PortalConfigServiceImpl implements PortletServiceProvider, PortalConfigService {

    private PortletLog log = SportletLog.getInstance(PortalConfigServiceImpl.class);

    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();

    public synchronized void init(PortletServiceConfig config) throws PortletServiceUnavailableException {

        PortalConfigSettings configSettings = this.getPortalConfigSettings();
        if (configSettings == null) {
            configSettings = new PortalConfigSettings();
            boolean canCreate = Boolean.getBoolean(config.getInitParameter("canUserCreateNewAccount"));
            configSettings.setCanUserCreateAccount(canCreate);
            // set gridsphere as a default group
            Set defaultGroups = new HashSet();
            defaultGroups.add(SportletGroup.CORE);
            configSettings.setDefaultGroups(defaultGroups);
            // set default theme
            configSettings.setDefaultTheme(config.getInitParameter("defaultTheme"));
            savePortalConfigSettings(configSettings);
        }

    }

    public synchronized void destroy() {

    }

    public void savePortalConfigSettings(PortalConfigSettings configSettings) {

        try {
            if (configSettings.getOid() == null) {
                pm.create(configSettings);
            } else {
                pm.update(configSettings);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to save or update config settings!", e);
        }
    }

    public PortalConfigSettings getPortalConfigSettings() {
        PortalConfigSettings settings = null;
        try {
            settings = (PortalConfigSettings)pm.restore("select c from " + PortalConfigSettings.class.getName() + " c");
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve config settings!", e);
        }
        return settings;
    }

}

/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatManagerException;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatManagerWrapper;
import org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat.TomcatWebAppResult;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.layout.PortletTabRegistry;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The PortletApplicationManager is a wrapper for the Tomcat manager webapp in 4.1.X which allows dynamic
 * ui application management and hence dynamic portlet management. This class needs to be adapted for
 * other servlet containers.
 */
public class PortletApplicationManager extends ActionPortlet {

    public static final String VIEW_JSP = "admin/portlets/view.jsp";
    public static final String HELP_JSP = "admin/portlets/help.jsp";

    private TomcatManagerWrapper tomcat = TomcatManagerWrapper.getInstance();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "listPortlets";
        DEFAULT_HELP_PAGE = HELP_JSP;
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void listPortlets(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        List result = new ArrayList();
        try {
            result = tomcat.getPortletAppList(req);
            event.getPortletRequest().setAttribute("result", result);
            log.info("result is OK");
        } catch (TomcatManagerException e) {
            log.error("Unable to retrieve list of portlets. Make sure tomcat-users.xml has been edited according to the UserGuide.");
            event.getPortletRequest().setAttribute("result", result);
            MessageBoxBean msg = event.getMessageBoxBean("msg");
            msg.setKey("PORTLET_ERR_LIST");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }

        //if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(event.getPortletRequest(), VIEW_JSP);
    }

    public void doPortletManager(FormEvent event) throws PortletException {
        log.debug("In doPortletManager");
        DefaultPortletAction action = event.getAction();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        User user = event.getPortletRequest().getUser();
        MessageBoxBean msg = event.getMessageBoxBean("msg");
        PortletManagerService portletManager = null;
        try {
            portletManager = (PortletManagerService) getConfig().getContext().getService(PortletManagerService.class, user);
        } catch (PortletServiceException e) {
            msg.setKey("PORTLET_ERR_REGISTRY");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }

        Map params = action.getParameters();
        String operation = (String) params.get("operation");
        String appName = (String) params.get("context");
        TomcatWebAppResult result = null;

        try {
            if ((operation != null) && (appName != null)) {
                if (operation.equals("start")) {
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("stop")) {
                    result = tomcat.stopWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    //portletManager.destroyPortletWebApplication(appName, req, res);
                } else if (operation.equals("reload")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.stopWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("remove")) {
                    portletManager.destroyPortletWebApplication(appName, req, res);
                    result = tomcat.removeWebApp(req, appName);
                    log.debug("removing application tab :" + appName);
                    PortletTabRegistry.removeGroupTab(appName);                   
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                } else if (operation.equals("deploy")) {
                    result = tomcat.deployWebApp(req, appName);
                    result = tomcat.startWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.initPortletWebApplication(appName, req, res);
                } else if (operation.equals("undeploy")) {
                    result = tomcat.undeployWebApp(req, appName);
                    this.createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_TOMCAT"));
                    portletManager.destroyPortletWebApplication(appName, req, res);
                }

            }
        } catch (IOException e) {
            log.error("Caught IOException!", e);
            msg.setKey("PORTLET_ERR_IO");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        } catch (TomcatManagerException e) {
            log.error("Caught TomcatmanagerException!", e);
            msg.setKey("PORTLET_ERR_TOMCAT");
            msg.setMessageType(MessageStyle.MSG_ERROR);
        }
        req.setAttribute("result", result);
        if (result != null) log.debug("result: " + result.getReturnCode() + " " + result.getDescription());
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void uploadPortletWAR(FormEvent event) throws PortletException {

        log.debug("in FileManagerPortlet: doUploadFile");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        if (req.getRole().compare(req.getRole(), PortletRole.SUPER) < 0) return;

        try {
            FileInputBean fi = event.getFileInputBean("userfile");
            User user = event.getPortletRequest().getUser();
            String fileName = fi.getFileName();
            log.info("filename = " + fileName);
            if (fileName.equals("")) return;
            PortletManagerService portletManager = null;
            try {
                portletManager = (PortletManagerService) getConfig().getContext().getService(PortletManagerService.class, user);
            } catch (PortletServiceException e) {
                MessageBoxBean msg = event.getMessageBoxBean("msg");
                msg.setKey("PORTLET_ERR_REGISTRY");
                msg.setMessageType(MessageStyle.MSG_ERROR);
                throw new PortletException("PortletRegistry service unavailable! ", e);
            }

            int isWar = fileName.indexOf(".war");
            if (isWar > 0) {
                String appName = fileName.substring(0, isWar);
                log.debug("installing and initing webapp: " + appName);
                tomcat.installWebApp(req, appName, fileName);
                portletManager.initPortletWebApplication(appName, req, res);
            }
            log.debug("fileinputbean value=" + fi.getValue());
        } catch (Exception e) {
            MessageBoxBean errMsg = event.getMessageBoxBean("errorFrame");
            errMsg.setKey("PORTLET_ERR_UPLOAD");
            errMsg.setMessageType(MessageStyle.MSG_ERROR);
            log.error("Unable to store uploaded file ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void deployWebapp(FormEvent event) throws PortletException {

        log.debug("in FileManagerPortlet: deployWebapp");
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        if (req.getRole().compare(req.getRole(), PortletRole.SUPER) < 0) return;
        try {
            TextFieldBean tf = event.getTextFieldBean("webappNameTF");
            User user = event.getPortletRequest().getUser();
            String webappName = tf.getValue();
            if (webappName == null) return;
            PortletManagerService portletManager = null;
            try {
                portletManager = (PortletManagerService) getConfig().getContext().getService(PortletManagerService.class, user);
            } catch (PortletServiceException e) {
                createErrorMessage(event, this.getLocalizedText(req, "PORTLET_ERR_REGISTRY"));
                throw new PortletException("PortletRegistry service unavailable! ", e);
            }

            tomcat.installWebApp(req, webappName);
            portletManager.initPortletWebApplication(webappName, req, res);
            createSuccessMessage(event, this.getLocalizedText(req, "PORTLET_SUC_DEPLOY") + " " + webappName);
        } catch (Exception e) {
            createErrorMessage(event, this.getLocalizedText(req, "PORTLET_ERR_DEPLOY"));
            log.error("Unable to deploy webapp  ", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.setValue(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.setValue(msg);
    }
}

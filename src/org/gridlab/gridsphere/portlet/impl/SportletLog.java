/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

/**
 * The <code>PortletLog</code> provides the portlet with the ability to log
 * information, warning, or error texts. The log is maintained by the portlet
 * container. The type and location of of the log is a matter of implementation.
 * Also, whether logging is enabled or not is at the discretion of the
 * portlet container.
 * <p/>
 * <code>
 * ...
 * PortletLog log = iContext.getLog ();
 * <p/>
 * if (log.isWarnEnabled())
 * log.warn ("Can access the content source");
 * <p/>
 * ...
 * </code>
 * <p/>
 */
public class SportletLog implements PortletLog {


    static {
        URL propsURL = SportletLog.class.getResource("/gridsphere/log4j.properties");
        PropertyConfigurator.configure(propsURL);
    }

    private static Map logMap = new Hashtable();
    private static String thisClassName = SportletLog.class.getName() + ".";
    //private Logger logger;

    private Log logger;

    /**
     * Constructor not accessible. Use getDefault instead.
     */
    private SportletLog(Class clazz) {
        logger = LogFactory.getLog(clazz);
    }

    /**
     * Return an instance of the PortletLog for a particular class
     *
     * @return the PortletLog
     */
    public static synchronized PortletLog getInstance(Class clazz) {
        SportletLog log = (SportletLog) logMap.get(clazz);
        if (log != null) return log;
        log = new SportletLog(clazz);
        logMap.put(clazz, new SportletLog(clazz));
        return log;
    }

    /**
     * Returns whether the debug level of the portlet log is enabled.
     *
     * @return true if debbuging is enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void debug(String text) {
        logger.debug(text);
    }

    /**
     * Returns whether the info level of the portlet log is enabled.
     *
     * @return true if the log is enabled, false otherwise
     */
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void info(String text) {
        logger.info(text);
    }

    /**
     * Returns whether the warn level of the portlet log is enabled.
     *
     * @return true for the moment
     */
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    /**
     * Logs the given warning text in the portlet log.
     *
     * @param text the warning text to log
     */
    public void warn(String text) {
        logger.warn(text);
    }

    /**
     * Returns whether the error level of the portlet log is enabled.
     *
     * @return true if the log is enabled, false otherwise
     */
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    /**
     * Logs the given error text in the portlet log.
     *
     * @param text the error text to log
     */
    public void error(String text) {
        logger.error(text);
    }

    /**
     * Logs the given error text, cause, and a stack trace in the portlet log.
     *
     * @param text  the error text to log
     * @param cause the cause for logging
     */
    public void error(String text, Throwable cause) {
        logger.error(text, cause);
    }

}

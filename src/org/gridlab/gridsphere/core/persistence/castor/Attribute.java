/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.UniqueID;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.jdo.conf.Mapping;
/**
 * @table attribute
 * @key-generator UUID
 * @depends org.gridlab.gridsphere.portlet.impl.SportletUserImpl
 */
public class Attribute extends BaseObject {

    /**
     * @sql-size 256
     * @sql-name key
     */
    private String Key = new String();
    /**
     * @sql-size 256
     * @sql-name value
     */
    private String Value  = new String();

    /**
     * @sql-name sportletuser
     */
    private SportletUserImpl User = new SportletUserImpl();

    public Attribute () {
        super();
    }

    public Attribute(String k, String v) {
        Key = k;
        Value = v;
        this.setOid(UniqueID.get());
    }

    public SportletUserImpl getUser() {
        return User;
    }

    public void setUser(SportletUserImpl user) {
        User = user;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }



}


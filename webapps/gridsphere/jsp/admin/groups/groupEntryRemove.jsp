<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>


<jsp:useBean id="groupEntryList" class="java.util.List" scope="request"/>

<ui:form>
<ui:hiddenfield beanId="groupID"/>

<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewConfirmRemoveGroupEntry" value="Remove Users"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doViewCancelRemoveGroupEntry" value="Cancel Remove"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell style="message">
                Select the users you would like to remove from [<ui:text beanId="groupLabel"/>]
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                Selection
            </ui:tablecell>
            <ui:tablecell>
                User Name
            </ui:tablecell>
            <ui:tablecell>
                Full Name
            </ui:tablecell>
            <ui:tablecell>
                Role In Group
            </ui:tablecell>
        </ui:tablerow>

<%
                Iterator groupIterator = groupEntryList.iterator();
                while (groupIterator.hasNext()) {
                    GroupEntry groupEntry = (GroupEntry)groupIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:checkbox beanId="groupEntryID" name="<%= groupEntry.getID() %>" value="<%= groupEntry.getID() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroupEntry" value="<%= groupEntry.getUser().getUserName() %>">
                                <ui:actionparam name="groupEntryID" value="<%= groupEntry.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <%= groupEntry.getUser().getFullName() %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <%= groupEntry.getRole() %>
                        </ui:tablecell>
                </ui:tablerow>

<%              } %>

    </ui:frame>
</ui:panel>

</ui:form>

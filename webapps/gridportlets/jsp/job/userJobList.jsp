<%@ page import="org.gridlab.gridsphere.portlet.User,
                 java.util.List,
                 org.gridlab.gridsphere.portlet.PortletURI,
                 org.gridlab.gridsphere.services.grid.job.Job,
                 org.gridlab.gridsphere.services.grid.job.JobSpecification" %>
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<jsp:useBean id="jobManagerBean"
             class="org.gridlab.gridsphere.portlets.grid.job.JobManagerBean"
             scope="request"/>
<gs:form action="doListUserJob">
<table class="portlet-pane" cellspacing="1" width="100%">
<% if (jobManagerBean.isFormInvalid()) { %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-message-alert">
            <%=jobManagerBean.getFormInvalidMessage()%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<% } %>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
        <tr>
          <td class="portlet-frame-actions">
            <gs:submit name="doListUserJob" value="List Jobs"/>
            &nbsp;&nbsp;<gs:submit name="doNewUserJob" value="New Job"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table class="portlet-frame" cellspacing="1" width="100%">
<% System.out.println("Getting jobs for user!!!!!!!!!!!");
   List userJobList = jobManagerBean.getUserJobList();
   int numUsers = userJobList.size();
   System.out.println("no jobs for user");
   if (numUsers == 0) { %>
        <tr>
          <td class="portlet-frame-text">
            There are no jobs associated with your account.
          </td>
        </tr>
<% } else { %>
       <tr>
         <td class="portlet-frame-header" width="100">
             Job
         </td>
         <td class="portlet-frame-header" width="150">
             Host
         </td>
         <td class="portlet-frame-header" width="200">
             Scheduler
         </td>
         <td class="portlet-frame-header" width="150">
             Queue
         </td>
         <td class="portlet-frame-header" width="150">
             Executable
         </td>
       </tr>
<%   for (int ii = 0; ii < numUsers; ++ii) {
      System.out.println("job " + ii + " for user");
       Job job = (Job)userJobList.get(ii);
       String jobID = job.getID();
       JobSpecification jobSpecification = (JobSpecification)job.getJobSpecification();%>
        <tr>
          <td class="portlet-frame-text">
            <gs:actionlink action="doViewUserJob" label="start">
              <gs:actionparam name="jobID" value="<%=jobID%>"/>
            </gs:actionlink>
          </td>
          <td class="portlet-frame-text">
            <%=job.getRuntimeHost()%>
          </td>
          <td class="portlet-frame-text">
            <%=job.getRuntimeScheduler()%>
          </td>
          <td class="portlet-frame-text">
            <%=job.getRuntimeQueue()%>
          </td>
          <td class="portlet-frame-text">
            <%=jobSpecification.getExecutable()%>
          </td>
        </tr>
<%   }
   } %>
      </table>
    </td>
  </tr>
</table>
</gs:form>

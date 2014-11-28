<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.exoplatform.support.EventBean" %>

<portlet:defineObjects/>

    <%  ArrayList<EventBean> eventList = (ArrayList<EventBean>) renderRequest.getAttribute("eventList"); %>

<div id="content">

    <h2>Events</h2>

    <table>
              <tr>
                  <th>Name</th>
                  <th>Date</th>
              </tr>
    <% for (EventBean event : eventList) {%>
              <tr>
                   <td> <%= event.getName() %></td>
                   <td> <%= event.getDate() %></td>
              </tr>
    <% } %>

          </table>
          <br />

  <form method="POST" action="<portlet:actionURL />">
           <span class="portlet-form-field-label">Name:</span>
           <input class="portlet-form-input-field" type="text" name="event-name" id="event-name"/>
           <span class="portlet-form-field-label">Date:</span>
           <input class="portlet-form-input-field" type="text" name="event-date" id="event-date"/>
           <input type="submit" name="Add" >

  </form>
</div>
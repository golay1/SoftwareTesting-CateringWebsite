<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- TODO -->
<head>
<meta charset="ISO-8859-1">
<title>Event Summary</title>
</head>
<body>
<center>
<h3>Events</h3>
<form action = "EventController" method = "post">
<table>
<tr>
<td>Date :</td>
<td><input type = "date" name = "date" placeholder = "Enter the date" value="<c:out value = '${currdate}'/>"></td>
<td><input name = "err_date"  value="<c:out value = '${EventErrorMsgs.dateError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Start Time :</td>
<td><input type = "time" name = "start_time" placeholder = "Enter the starting time" value="<c:out value = '${currtime}'/>"></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td></td>
<td><input name = "action" value = "viewEventM" type="hidden"></td>
<td><input name = "userName" value="<c:out value = '${userName}'/>" type="hidden"></td>
<td><input type = "submit" name = "submit" value = "Search"></td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
<td><input type = button value = "Back" onclick="location.href='ManagerHomePage.jsp'" ></td>
</tr>
</table>
</form>
<table>
<tr>
    <th>Event Name</th>
    <th>Event Date</th>
    <th>Start Time</th>
    <th>Duration</th>
    <th>Hall Name</th>
    <th>User FirstName</th>
    <th>User LastName</th>
    <th>Est Attendees</th>
  </tr>
  <c:forEach items="${EventModelList}" var = "item" varStatus="status">
  		<tr>
  		<td><c:out value="${item.name} "/></td>
  		<td><c:out value="${item.date} "/></td>
  		<td><c:out value="${item.startTime} "/></td>
  		<td><c:out value="${item.duration} "/></td>
  		<td><c:out value="${item.hallName} "/></td>
  		<td><c:out value="${item.firstName} "/></td>
  		<td><c:out value="${item.lastName} "/></td>
  		<td><c:out value="${item.attendees} "/></td>
        <td> <a href="<c:url value='/EventController?action=viewSelectedEventM&id=${item.eventID}'/>">View</a></td>
  		</tr>
  </c:forEach>
</table>
</center>
</body>
</html>
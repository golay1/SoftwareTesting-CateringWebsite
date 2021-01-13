<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Event Summary</title>
</head>
<body>
<center>
<h3>Event Summary</h3>
<form name ="requestEvent" action="EventController" method = "post">
<table>
<tr>
<td>First Name :</td>
<td><input type ="text" name = "firstName" value="<c:out value = '${EventModel.firstName}'/>" readonly></td>
</tr>
<tr>
<td>Last Name :</td>
<td><input type ="text" name = "lastName" value="<c:out value = '${EventModel.lastName}'/>" readonly></td>
</tr>
<tr>
<td>Event Id :</td>
<td><input type ="text" name = "eventId" value="<c:out value = '${EventModel.eventID}'/>" readonly></td>
</tr>
<tr>
<td>Date :</td>
<td><input type ="date" name = "date" placeholder = "Enter the date" value="<c:out value = '${EventModel.date}'/>" readonly></td>
</tr>
<tr>
<td>Start Time :</td>
<td><input type = "time" name = "start_time" placeholder = "Enter the time" value="<c:out value = '${EventModel.startTime}'/>" readonly></td>
</tr>
<tr>
<td>Duration :</td>
<td><input type = "text" name = "duration" value="<c:out value = '${EventModel.duration}'/>" readonly></td>
<%-- <td><input name = "err_duration" value="<c:out value = '${EventErrorMsgs.durationError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td> --%>
</tr>
<tr>
<td>Hall Name :</td>
<td><input type = "text" name = "Hall_Name" value="<c:out value = '${EventModel.hallName}'/>" readonly></td>
<%-- <td><input name = "err_HallName" value="<c:out value = '${EventErrorMsgs.hallError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td> --%>
</tr>
<tr>
<td>Est Attendees :</td>
<td><input type = "text" name = "est_attendees" placeholder = "Enter the est atendees" value="<c:out value = '${EventModel.attendees}'/>" readonly></td>
<%-- <td><input name = "err_EstAttendees" value="<c:out value = '${EventErrorMsgs.attendeesError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td> --%>
</tr>
<tr>
<td>Event Name :</td>
<td><input type = "text" name = "event_name" placeholder = "Enter the event name" value="<c:out value = '${EventModel.name}'/>" readonly></td>
</tr>
<tr>
<td>Food Type :</td>
<td><input type = "text" name = "Food_Type" value="<c:out value = '${EventModel.foodType}'/>" readonly></td>
</tr>
<tr>
<td>Meal Type :</td>
<td><input type = "text" name = "Meal_Type" value="<c:out value = '${EventModel.meal}'/>" readonly></td>
</tr>
<tr>
<td>Meal Formality :</td>
<td><input type = "text" name = "Meal_Formality" value="<c:out value = '${EventModel.formal}'/>" readonly></td>

</tr>
<tr>
<td>Drink Type :</td>
<td><input type = "text" name = "Drink_Type" value="<c:out value = '${EventModel.drinkType}'/>" readonly></td>
</tr>
<tr>
<td>Entertainment Items :</td>
<td><input type = "text" name = "entertainment" value="<c:out value = '${EventModel.entertainmentItems}'/>" readonly></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input name = "error" value="<c:out value = '${EventErrorMsgs.errorMsg}'/>"  style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
<td>&nbsp;</td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
<td><input type = button value = "Back" onclick="location.href='StaffEventSummary.jsp'" ></td>
</tr>
</table>
</form>
</center>
</body>
</html>
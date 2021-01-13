<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Request Event</title>
</head>
<body>
<center>
<h3>Book your Event</h3>
<form name ="requestEvent" action="EventController" method = "post">
<table>
<tr>
<td>Date :</td>
<td><input type ="date" name = "date" /></td>
<td><input name = "err_duration"  value="<c:out value = '${EventErrorMsgs.dateError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Start Time :</td>
<td><input type = "time" name = "start_time" placeholder = "Enter the time" ></td>
</tr>
<tr>
<td>Duration :</td>
<td><select name = "duration"><option value = "2">2 Hours</option><option value = "3">3 Hours</option><option value = "4">4 Hours</option><option value = "5">5 Hours</option><option value = "6">6 Hours</option><option value = "7">7 Hours</option><option value = "8">8 Hours</option></select></td>
<td><input name = "err_duration"  value="<c:out value = '${EventErrorMsgs.durationError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Hall Name :</td>
<td><select name = "Hall_Name"><option value = "maverick">Maverick Hall</option><option value = "kc">KC Hall</option><option value = "arlington">Arlington Hall</option><option value = "shard">Shard Hall</option><option value = "liberty">Liberty Hall</option></select></td>
<td><input name = "err_HallName" value="<c:out  value = '${EventErrorMsgs.hallError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Est Attendees :</td>
<td><input type = "text" name = "est_attendees" placeholder = "Enter the est atendees"></td>
<td><input name = "err_EstAttendees"  value="<c:out value = '${EventErrorMsgs.attendeesError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Event Name :</td>
<td><input type = "text" name = "event_name" placeholder = "Enter the event name"></td>
<td><input name = "err_EvtName"  value="<c:out value = '${EventErrorMsgs.eventNameError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Food Type :</td>
<td><select name = "Food_Type"><option value = "american">American</option><option value = "chinese">Chinese</option><option value = "french">French</option><option value = "greek">Greek</option><option value = "indian">Indian</option><option value = "italian">Italian</option><option value = "japanese">Japanese</option><option value = "mexican">Mexican</option><option value = "pizza">Pizza</option></select></td>
</tr>
<tr>
<td>Meal Type :</td>
<td><select name = "Meal_Type"><option value = "breakfast">Breakfast</option><option value = "lunch">Lunch</option><option value = "supper">Supper</option></select></td>
</tr>
<tr>
<td>Meal Formality :</td>
<td><select name = "Meal_Formality"><option value = "informal">Informal</option><option value = "formal">Formal</option></select></td>
</tr>
<tr>
<td>Drink Type :</td>
<td><select name = "Drink_Type"><option value = "standard">Non-Alcohol</option><option value = "alcohol">Alcohol</option></select></td>
</tr>
<tr>
<td>Entertainment Items :</td>
<td><select name = "entertainment"><option value = "music">Music</option><option value = "non-music">Non-Music</option></select></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input name = "error"  value="<c:out value = '${EventErrorMsgs.errorMsg}'/>"  style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
<td>&nbsp;</td>
</tr>
<tr>
<td></td>
<td><input type = "submit" name = "submit" value = "Request"></td>
<td><input name = "userName"  value="<c:out value = '${userName}'/>" type="hidden"></td>
<td><input name = "action" value = "add" type="hidden"></td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
<td><input type = button value = "Back" onclick="location.href='UserHomePage.jsp'" ></td>
</tr>
</table>
</form>
</center>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- TODO -->
<head>
<meta charset="ISO-8859-1">
<title>Manager HomePage</title>
</head>
<body>
<center>
<h3>Welcome <input name = "userName" value="<c:out value = '${userName}' />"  style ="background-color: white; border: none; width: 146px;" disabled="disabled"/></h3>
<table>
<form method = "post" action = "UserController">
<tr>
<td><button type = "submit" style="width: 175px; margin: 0 auto;">View Profile</td>
<td><input name = "action" value = "view" type="hidden"></td>
<td><input name = "userName" value="<c:out value = '${userName}'/>" type="hidden"></td>
</tr>
</form>
<tr>
<td>&nbsp;</td>
</tr>
<form method = "post" action = "UserController">
<tr>
<td><button type = "submit" style="width: 175px; margin: 0 auto;">View Event Summary</td>
<td><input name = "action" value = "eventSummaryM" type="hidden"></td>
<td><input name = "userName" value="<c:out value = '${userName}'/>" type="hidden"></td>
</tr>
</form>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button" style="width: 175px; margin: 0 auto;">Logout</button></a></td>
</tr>
</table>
</center>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Application main page</title>
<style>
td {
  text-align : center;
}
</style>
</head>
<body>
<center>
<h3>Login Here</h3>

<form method = "post" action = "UserController" name = "login">
<table>
<tr>
<td>Username :</td>
<td><input type ="text" name = "username"></td>
<%-- <td><input name = "err_username" value="<c:out value = '${UserErrorMsgs.userNameError}' />"style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td> --%>

</tr>
<tr>
<td>Password :</td>
<td><input type = "password" name = "Upassword"></td>
<%-- <td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.passwordError}' />"style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td> --%>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td><input name = "error" value="<c:out value = '${UserErrorMsgs.errorMsg}' />"  style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td><input type = "submit" name = "submit" value = "Login"></td>
<td>Not a member yet? <a href="<c:url value='registration.jsp'/>">Register</a></td>
</tr>
</table>
<input name="action" value="login" type="hidden">
</form>
</center>
</body>
</html>
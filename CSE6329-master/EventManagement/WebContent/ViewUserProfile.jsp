<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View User Profile</title>
</head>
<body>
<center>
<h3>Profile</h3>
<form action="UserController" method = "post">
<table>
<tr>
<td>Username :</td>
<td><input type = "text" name = "username" value="<c:out value = '${UserModel.userName}'/>" readonly></td>
<td><input name = "err_username" value="<c:out value = '${UserErrorMsgs.userNameError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Password :</td>
<td><input type = "password" name = "user_password" value="<c:out value = '${UserModel.password}'/>" readonly></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.passwordError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Lastname :</td>
<td><input type = "text" name = "last_name" value="<c:out value = '${UserModel.lastName}'/>"></td>
<td><input name = "err_lastname" value="<c:out value = '${UserErrorMsgs.lastNameError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Firstname :</td>
<td><input type = "text" name = "first_name" value="<c:out value = '${UserModel.firstName}'/>"></td>
<td><input name = "err_firstname" value="<c:out value = '${UserErrorMsgs.firstNameError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>UTA ID :</td>
<td><input type = "text" name ="uta_id" value="<c:out value = '${UserModel.utaId}'/>"></td>
<td><input name = "err_utaId" value="<c:out value = '${UserErrorMsgs.utaIdError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Phone Number :</td>
<td><input type = "text" name = "phone_number" value="<c:out value = '${UserModel.phoneNumber}'/>"></td>
<td><input name = "err_phno" value="<c:out value = '${UserErrorMsgs.phoneError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Email :</td>
<td><input type = "text" name = "email_id" value="<c:out value = '${UserModel.emailId}'/>"></td>
<td><input name = "err_email" value="<c:out value = '${UserErrorMsgs.emailError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Street Number :</td>
<td><input type = "text" name = "street_number" value="<c:out value = '${UserModel.streetNumber}'/>"></td>
<td><input name = "err_stno" value="<c:out value = '${UserErrorMsgs.streetNumberError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Street Name :</td>
<td><input type = "text" name = "street_name" value="<c:out value = '${UserModel.streetName}'/>"></td>
<td><input name = "err_stname" value="<c:out value = '${UserErrorMsgs.streetNameError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>City :</td>
<td><input type = "text" name = "city" value="<c:out value = '${UserModel.city}'/>"></td>
<td><input name = "err_city" value="<c:out value = '${UserErrorMsgs.cityError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>State :</td>
<td><input type = "text" name = "state" value="<c:out value = '${UserModel.state}'/>"></td>
<td><input name = "err_state" value="<c:out value = '${UserErrorMsgs.stateError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Zipcode :</td>
<td><input type = "text" name = "zip_code" value="<c:out value = '${UserModel.zipCode}'/>"></td>
<td><input name = "err_zipcode" value="<c:out value = '${UserErrorMsgs.zipCodeError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Role :</td>
<td><select name = "role"><c:forEach items="${listRoles}" var="category"><option value='${category}'>${category}</option></c:forEach></select></td>
<td><input name = "err_role" value="<c:out value = '${UserErrorMsgs.roleError}'/>" style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input name = "error" value="<c:out value = '${UserErrorMsgs.errorMsg}'/>"  style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<!-- <td><input name = "action" value = "modify" type="hidden"></td>  -->
<td><input type = "submit" name = "action" action = "modify" value = "Modify" onclick="if (confirm('Are you sure you want to modify?')) form.action='UserController?action=modify';else return false;"></td>
<td><input type = "submit" name="action" action = "delete" value = "Delete" onclick="if (confirm('Are you sure you want to delete?')) form.action='UserController?action=delete';else return false;"></td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
<td><input type = button value = "Back" onclick="location.href='UserSearch.jsp'" ></td>
</tr>
</table>
</form>
</center>
</body>
</html>
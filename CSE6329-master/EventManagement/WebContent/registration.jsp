<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
<center>
<h3>Fill in your details</h3>
<form name ="registration" action="UserController" method = "post">
<table>
<tr>
<td>Username :</td>
<td><input type = "text" name = "username" placeholder = "Enter username" value="<c:out value = '${RegistrationModel.userName}'/>"></td>
<td><input name = "err_username" value="<c:out value = '${UserErrorMsgs.userNameError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Password :</td>
<td><input type = "password" name = "user_password" placeholder = "Enter password" value="<c:out value = '${RegistrationModel.password}'/>"></td>
<td><textarea rows="8" cols="50" name = "err_password" style ="height:100px;background-color: white; border: none; width: 300px;" disabled="disabled" readonly wrap = 'hard'>${UserErrorMsgs.passwordError}</textarea></td>
</tr>
<tr>
<td>Lastname :</td>
<td><input type = "text" name = "last_name" placeholder = "Enter your last name" value="<c:out value = '${RegistrationModel.lastName}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.lastNameError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Firstname :</td>
<td><input type = "text" name = "first_name" placeholder = "Enter your first name" value="<c:out value = '${RegistrationModel.firstName}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.firstNameError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Role :</td>
<td><select name = "role" value="<c:out value = '${RegistrationModel.role}'/>"><option value = "user">User</option><option value = "manager">Manager</option><option value = "staff">Staff</option><option value = "admin">Admin</option></select></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.roleError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>UTA ID :</td>
<td><input type = "text" name ="uta_id" placeholder = "Enter the UTA ID" value="<c:out value = '${RegistrationModel.utaId}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.utaIdError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Phone Number :</td>
<td><input type = "type" name = "phone_number" placeholder = "Enter the phone number" value="<c:out value = '${RegistrationModel.phoneNumber}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.phoneError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Email :</td>
<td><input type = "text" name = "email_id" placeholder = "Enter your email id" value="<c:out value = '${RegistrationModel.emailId}'/>"></td>
<td><input name = "emailError" value="<c:out value = '${UserErrorMsgs.emailError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Street Number :</td>
<td><input type = "text" name = "street_number" placeholder = "Enter the street number" value="<c:out value = '${RegistrationModel.streetNumber}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.streetNumberError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Street Name :</td>
<td><input type = "text" name = "street_name" placeholder = "Enter the street name" value="<c:out value = '${RegistrationModel.streetName}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.streetNameError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>City :</td>
<td><input type = "text" name = "city" placeholder = "Enter the name of the city" value="<c:out value = '${RegistrationModel.city}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.cityError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>State :</td>
<td><input type = "text" name = "state" placeholder = "Enter the state" value="<c:out value = '${RegistrationModel.state}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.stateError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Zipcode :</td>
<td><input type = "text" name = "zip_code" placeholder = "Enter the zipcode" value="<c:out value = '${RegistrationModel.zipCode}'/>"></td>
<td><input name = "err_password" value="<c:out value = '${UserErrorMsgs.zipCodeError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input name = "error" value="<c:out value = '${UserErrorMsgs.errorMsg}'/>"  style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;</td>
<td><input type = "submit" name = "submit" value = "Register"></td>
</tr>
</table>
<input name="action" value="register" type="hidden">
</form>
</center>
</body>
</html>
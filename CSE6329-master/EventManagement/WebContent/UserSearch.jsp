<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search User</title>
<style>
td {
 text-align : center;
}
</style>
</head>
<body>
<center>
<h3>Search User</h3>
<form action="UserController" method = "post">
<table>
<tr>
<td>Last Name :</td>
<td><input type = "text" name = "lastName" placeholder = "Enter Last Name"  ></td>
<td><input name = "err_lastName"  value="<c:out value = '${RegistrationErrorMsgs.lastNameError}'/>" style ="background-color: white; border: none; width: 250px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td><input name = "action" value = "search" type="hidden"></td> 
<td><input type = "submit" action = "search" value = "Search"></td>
<td><input name = "userName"  value="<c:out  value = '${userName}'/>" type="hidden"></td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
<td><input type = button value = "Back" onclick="location.href='AdminHomePage.jsp'" ></td>
</tr>
</table>
</form>
<table>
<tr>
    <th>Username</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Role</th>
  </tr>
  <c:forEach items="${UserModelList}" var="item" varStatus="status">
			<tr>
			<td><c:out value="${item.userName}" /></td>
			<td><c:out value="${item.firstName}" /></td>
			<td><c:out value="${item.lastName}" /></td>
			<td><c:out value="${item.role}" /></td>
            <td> <a href="<c:url value='/UserController?action=viewUserProfile&id=${item.userName}'/>">View</a></td>
			</tr>
		</c:forEach>
</table>
</center>
</body>
</html>
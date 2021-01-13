<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Portal</title>
</head>
<body>
<center>
<h3>Pay For an Event</h3>
<form name = "paymentEvent" action = "PaymentController" method = "post">
<table>
<tr>
<td>Price :</td>
<td><input type ="text" name = "price"  value="<c:out value = '${PaymentModel.price}'/>" readonly></td>
</tr>
<tr>
<td>UserName :</td>
<td><input type ="text" name = "userName"  value="<c:out value = '${PaymentModel.username}'/>" readonly></td>
</tr>
<tr>
<td>Event ID :</td>
<td><input type ="text" name = "eventID" value="<c:out value = '${PaymentModel.eventID}'/>" readonly></td>
</tr>
<tr>
<td>Card Number :</td>
<td><input type ="text" name = "cardNo" placeholder = "Enter your card number"  value="<c:out value = '${PaymentModel.cardNumber}'/>"></td>
<td><input name = "err_cardNumber" value="<c:out value = '${PaymentErrorMsgs.ccNoError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Pin :</td>
<td><input type ="text" name = "cvv" placeholder = "Enter the Pin"  value="<c:out value = '${PaymentModel.pin}'/>"></td>
<td><input name = "err_cVV" value="<c:out value = '${PaymentErrorMsgs.ccPinError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>Expiry Date :</td>
<td><input type ="text" name = "expiryDate" placeholder = "Enter the expiry date"  value="<c:out value = '${PaymentModel.expDate}'/>"></td>
<td><input name = "err_expiryDate" value="<c:out value = '${PaymentErrorMsgs.expDateError}'/>" style ="background-color: white; border: none; width: 400px;" disabled="disabled" readonly></td>
</tr>
<tr>
<td>&nbsp;</td>
<td><input name = "error" value="<c:out value = '${PaymentErrorMsgs.error}' />"  style ="background-color: white; border: none; width: 200px;" disabled="disabled" readonly></td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td align = "center"><input type = "submit" value = "Pay" onclick="if (confirm('Are you sure you want to pay?')) form.action='PaymentController';else return false;"></td>
<td><input name = "action" value = "pay" type="hidden"></td>
</tr>
<tr>
<td><a href="<c:url value='/UserController?action=logout'/>"><button type = "button">Logout</button></a></td>
</tr>
</table>
</form>
</center>
</body>
</html>
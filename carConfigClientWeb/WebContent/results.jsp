<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
    <%@ page import="model.Automobile" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
Automobile auto = (Automobile)request.getAttribute("auto");
int price = auto.getTotalPrice();
String[] setNames = auto.getOptionSetNames();
String makeAndModel = auto.getMake() + " " + auto.getModel();
%>

<h1>Here is what you selected</h1>

<table style="width:100%" action="">
<tr bgcolor="#FDF5E6"><th><%=makeAndModel %>
<th>base price<th> <%= auto.getBasePrice() %>
<% for (String setName:setNames) { %>
  <tr>
    <td><%= setName %></td>
    <td><%= auto.getOptionChoice(setName) %></td>
    <td><%= auto.getOptionChoicePrice(setName) %></td>		
  </tr>
  <%} %>
  <tr>
  	<td>Totol Cost
  	<td>
  	<td><%= price %>
  </tr>
</table>

</body>
</html>
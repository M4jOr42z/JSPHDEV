<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Select a car to configure</title>
</head>
<body>
<form action="dispatcher">
	<br>
	<select name="models" multiple="4">
<%@ page import="java.util.*" %>	
<% ArrayList<String> models = (ArrayList<String>)request.getAttribute("models"); 
   for (int i = 0; i < models.size(); i++) {
%>
	<option value=<%= i %>><%= models.get(i) %></option>
<% } %>
	</select>
	<input type="hidden" name="requestType" value="requestOptions"/>
	<input type="submit" name="selectCar">
	</form>
</body>
</html>
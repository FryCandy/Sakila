<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>Index</title>
</head>
<body class = "container">
	<h1>index</h1>
	<ol class="list-group">
		<li><a href="<%=request.getContextPath() %>/storeList.jsp">storeList</a>
		<li><a href="<%=request.getContextPath() %>/staffList.jsp">staffList</a>
		<li><a href="<%=request.getContextPath() %>/ActorInfoList.jsp">ActorInforLIst</a>
	</ol>

</body>
</html>
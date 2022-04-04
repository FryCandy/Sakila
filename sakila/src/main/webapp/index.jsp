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
		<!-- view 7개 리스트 -->
		<li><a href="<%=request.getContextPath() %>/ActorInfoList.jsp">ActorInforLIst</a>
		<li><a href="<%=request.getContextPath() %>/customerList.jsp">customerList</a>
		<li><a href="<%=request.getContextPath() %>/filmList.jsp">filmList</a>
		<li><a href="<%=request.getContextPath() %>/nicerButSlowerFilmList.jsp">niceButSlowerFilmList</a>
		<li><a href="<%=request.getContextPath() %>/salesByFilmCategory.jsp">salesByFilmCategory</a>
		<li><a href="<%=request.getContextPath() %>/salesByStore.jsp">salesByStore</a>
		<li><a href="<%=request.getContextPath() %>/staffListView.jsp">staffListView</a>
	</ol>

</body>
</html>
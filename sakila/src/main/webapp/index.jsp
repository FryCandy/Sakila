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
	<ul class="list-group list-group-horizontal">
		<!-- 테이블 -->
		<li class="list-group-item">
			<h2>테이블 구현</h2>
			<ol class="list-group">
				<li><a href="<%=request.getContextPath() %>/table/storeList.jsp">storeList</a>
				<li><a href="<%=request.getContextPath() %>/table/staffList.jsp">staffList</a>
			</ol>
		</li>
		<!-- view 7개 리스트 -->
		<li class="list-group-item">
			<h2>뷰 구현</h2>
			<ol class="list-group">
				<li><a href="<%=request.getContextPath() %>/view/ActorInfoList.jsp">ActorInforLIst</a>
				<li><a href="<%=request.getContextPath() %>/view/customerList.jsp">customerList</a>
				<li><a href="<%=request.getContextPath() %>/view/filmList.jsp">filmList</a>
				<li><a href="<%=request.getContextPath() %>/view/nicerButSlowerFilmList.jsp">niceButSlowerFilmList</a>
				<li><a href="<%=request.getContextPath() %>/view/salesByFilmCategory.jsp">salesByFilmCategory</a>
				<li><a href="<%=request.getContextPath() %>/view/salesByStore.jsp">salesByStore</a>
				<li><a href="<%=request.getContextPath() %>/view/staffListView.jsp">staffListView</a>
			</ol>
		</li>	
		<!-- 프로시저 3개 결과 화면 -->
			<li class="list-group-item">		
			<h2>프로시저</h2>
			<ol class="list-group">
				<li><a href="<%=request.getContextPath() %>/filmInStock.jsp">filmInStock</a>
				<li><a href="<%=request.getContextPath() %>/filmNotInStock.jsp">filmNotInStock</a>
				<li><a href="<%=request.getContextPath() %>/rewardsReport.jsp">rewardsReport</a>
			</ol>
		</li>
		<!-- 상세검색-->
		<li class="list-group-item">
			<h2>상세검색</h2>
			<ol class="list-group">
				<li><a href="<%=request.getContextPath() %>/filmSearchForm.jsp">filmSearchForm</a>
				<li><a href="<%=request.getContextPath() %>/rentalSearchForm.jsp">rentalSearchForm</a>
			</ol>
		</li>
	</ul>
</body>
</html>
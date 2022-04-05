<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="dao.*" %>
<%@ page import="java.util.List" %>
<%
	//요청값 받기
	int filmId =0; //영화 정보 받기
	if(request.getParameter("filmId")!=null&&!request.getParameter("filmId").equals("")){
		filmId = Integer.parseInt(request.getParameter("filmId"));
		System.out.println(filmId+"<--filmId");
	}
	int storeId =0; //가게 정보 받기
	if(request.getParameter("storeId")!=null&&!request.getParameter("storeId").equals("")){
		storeId = Integer.parseInt(request.getParameter("storeId"));
		System.out.println(storeId+"<--storeId");
	}
	int count = 0;//총갯수
	//dao에 프로시저 호출
	FilmDao filmDao = new FilmDao();
		Map<String,Object> map = filmDao.filmInStockCall(filmId, storeId);
		List<Integer> inventoryList = (List<Integer>)map.get("list");
		count = (Integer)map.get("count");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>filmInStock</title>
</head>
<body class="container">
	<h1>filmInStock</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<form method = "post" action="<%=request.getContextPath()%>/filmInStock.jsp">
		filmId 입력 : <input type = "number" name = "filmId">	
		storeId 입력 :<input type = "number" name = "storeId">
		<button type = "submit"> 검색</button>	
	</form>
	<%
		if(filmId!=0 && storeId!=0){
	%>
	<h2><%=filmId%>번 영화가 <%=storeId%>번 가게에 <%=count%> 개 남음</h2>
	<h2>inventoryID :
	<%		
		for(int id : inventoryList) {
	%>
			<%=id%>
	<%
				}
		}
	%>
	</h2>
</body>
</html>
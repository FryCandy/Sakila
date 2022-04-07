<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import ="java.util.*" %>
<%
	StoreDao storeDao = new StoreDao();
	List<Integer> storeIdList = storeDao.selectStoreIdList();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<title>rentalSearchForm</title>
</head>
<body class = "container">
	<h1>대여 상세 검색</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<form method ="post" action ="<%=request.getContextPath() %>/rentalSearchAction.jsp">
	<table class="table table-bordered">
	<!-- 가게아이디 검색 -->
		<tr>
			<td>스토어 ID</td>
			<td>
			<%
				for(int i : storeIdList) {
			%>
				<div><input type="radio" name="storeId" value="<%=i%>"> <%=i%>번 가게</div>
			<%      
				}
			%>
			</td>
		</tr>
	<!-- 고객이름 검색 -->      
		<tr>
			<td>고객이름</td>
			<td>
				<input type="text" name="customerName">
			</td>
		</tr>
	<!-- 대여일자 -->
		<tr>
			<td>대여일자</td>
			<td>
				<input type="date" name="beginDate"> ~ <input type="date" name="endDate">
			</td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit" class="btn btn-outline-info">검색</button></td>
		</tr>
	</table>
	</form>
</body>
</html>
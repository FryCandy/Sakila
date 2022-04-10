<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.*"%>
<%
	StoreDao storeDao = new StoreDao();
	List<Map<String, Object>> list = storeDao.selectStoreList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>Store List</title>
</head>
<body class="container">
	<h1>Store List</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>storeId</th>
				<th>staffId</th>
				<th>staffName</th>
				<th>addressId</th>
				<th>staffAddress</th>
				<th>lastUpdate</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(Map m : list) {
			%>
					<tr>
						<td><%=m.get("storeId")%></td>
						<td><%=m.get("staffId")%></td>
						<td><%=m.get("staffName")%></td>
						<td><%=m.get("addressId")%></td>
						<td><%=m.get("staffAddress")%></td>
						<td><%=m.get("lastUpdate")%></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>

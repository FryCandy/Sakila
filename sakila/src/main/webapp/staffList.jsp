<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import = "dao.*" %>
<%
	//dao 값 호출
	StaffDao staffDao = new StaffDao();
	List<Map<String,Object>> list = staffDao.selectStaffList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>staffList</title>
</head>
<body class="container">
	<h1>Staff List</h1>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>staffId</th>
				<th>staffName</th>
				<th>staffAddress</th>
				<th>picture</th>
				<th>email</th>
				<th>notes</th>
				<th>username</th>
				<th>lastUpdate</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(Map m : list) {
			%>
					<tr>
						<td><%=m.get("staffId")%></td>
						<td><%=m.get("staffName")%></td>
						<td><%=m.get("staffAddress")%></td>
						<td><%=m.get("picture")%></td>
						<td><%=m.get("email")%></td>
						<td><%=m.get("notes")%></td>
						<td><%=m.get("username")%></td>
						<td><%=m.get("lastUpdate")%></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>

</body>
</html>
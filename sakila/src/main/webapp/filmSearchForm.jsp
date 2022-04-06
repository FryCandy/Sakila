<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="dao.*" %>
<%@ page import ="vo.*" %>
<%
	//dao값 요청
	CategoryDao categoryDao = new CategoryDao();
	//category 값 요청
	List<Category> categoryList = categoryDao.selectCategoryList();
	//price 값 요청
	FilmDao filmDao = new FilmDao();
	List<Double> selectFilmPriceList = filmDao.selectFilmPriceList();
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>filmSearchForm</title>
</head>
<body class = "container">
	<h1>필름 리스트 뷰 검색</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<form method ="post" action ="<%=request.getContextPath() %>/filmSearchAction.jsp">
		<table class="table table-bordered">
			<!-- 카테고리 부분, category table에서 select -->
			<tr>
				<td>category</td>
				<td>
					<select name = "category">
					<option value ="">카테고리 선택</option>
					<%
						for(Category c : categoryList){
					%>
						<option value ="<%=c.getName()%>"><%=c.getName()%></option>
					<%
						}
					%>
					</select>
				</td>
			</tr>
			<!-- rating부분 , film테이블에서 enum 값 입력 -->
			<tr>
				<td>rating</td>
				<td>
					<select name ="rating">
						<option value ="">등급 선택</option>
						<option value ="G">G</option>
						<option value ="PG">PG</option>
						<option value ="PG-13">PG-13</option>
						<option value ="R">R</option>
						<option value ="NC-17">NC-17</option>
					</select>
				</td>
			</tr>
			<!-- price부분 -->
			<tr>
				<td>price</td>
				<td>
					<input type="radio" name="price" value="-1" checked="checked"> 선택안함
				<%
					for(Double p : selectFilmPriceList){
				%>
						<input type="radio" name="price" value="<%=p%>"> <%=p%>
				<%
					}
				%>
				</td>
			</tr>
			<!-- length-->
			<tr>
				<td>length</td>
				<td>
					<input type="number" name="minLength" > 분이상
					<input type="number" name="maxLength"> 분미만
					
				</td>
			</tr>
			<!-- title부분 -->
			<tr>
				<td>제목 검색</td>
				<td>
					<input type = "text" name="title">
				</td>
			</tr>
			<!-- actors 부분 -->
			<tr>
				<td>배우 검색</td>
				<td>
					<input type = "text" name="actors">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type = "submit" class="btn btn-outline-info">검색</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
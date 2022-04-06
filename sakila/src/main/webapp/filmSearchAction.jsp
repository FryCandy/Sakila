<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="dao.*" %>
<%@ page import ="vo.*" %>
<%
	//요청값 받기
	//1.검색관련
	//category
	String category ="";
	if(request.getParameter("category")!=null){
		category = request.getParameter("category");
		System.out.println(category+"<--category");//디버깅
	}
	//rating
	String rating ="";
	if(request.getParameter("rating")!=null){
		rating = request.getParameter("rating");
		System.out.println(rating+"<--rating");//디버깅
	}
	//price
	double price =0;
	if(request.getParameter("price")!=null){
		price = Double.parseDouble(request.getParameter("price"));
		System.out.println(price+"<--price");//디버깅
	}
	//length
	int minLength =0;
	if(request.getParameter("minLength")!=null&&!request.getParameter("minLength").equals("")){
		minLength = Integer.parseInt(request.getParameter("minLength"));
		System.out.println(minLength+"<--minLength");//디버깅
	}
	int maxLength =10000;
	if(request.getParameter("maxLength")!=null&&!request.getParameter("maxLength").equals("")){
		maxLength = Integer.parseInt(request.getParameter("maxLength"));
		System.out.println(maxLength+"<--maxLength");//디버깅
	}
	//title
	String title ="";
	if(request.getParameter("title")!=null){
		title = request.getParameter("title");
		System.out.println(title+"<--title");//디버깅
	}
	//actors
	String actors ="";
	if(request.getParameter("actors")!=null){
		actors = request.getParameter("actors");
		System.out.println(actors+"<--actors");//디버깅
	}
	//2. 페이징
	int currentPage = 1; // 현재 페이지 초기 값 1
	if(request.getParameter("currentPage")!=null){
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		System.out.println(currentPage+"<--currentPage");
	}
	int rowPerPage = 10; //초기 페이지 당 행의 수 10
	if(request.getParameter("rowPerPage")!=null){
		rowPerPage = Integer.parseInt(request.getParameter("rowPerPage"));
		System.out.println(rowPerPage+"<--rowPerPage");
	}
	int beginRow = (currentPage-1)*rowPerPage; //Page 처음 행의 값 연산식
		System.out.println(beginRow+"beginRow");
	int minPage = 1;
		if(request.getParameter("minPage")!= null){
			minPage = Integer.parseInt(request.getParameter("minPage"));
			currentPage=minPage;
		}else{
			minPage = (currentPage-1)/10*10+1;//페이징에 표시 될 가장 작은 페이지숫자 :일의 자리수는 1이고, 십이상의 자리수는 currentPage와 동일한 minPage
		}
		System.out.println(minPage+"<--minPage");
	int lastPage = 0; //마지막 페이지 변수 초기화
	
	//dao 호출
	FilmDao filmDao = new FilmDao();
	//검색 후 리스트
	List<FilmList> filmList = filmDao.selectFilmListSearch(category, rating, price, minLength,maxLength, title, actors, beginRow, rowPerPage);
	//검색후 전체 게시물 수
	int totalRow = filmDao.totalRowFilmListSearch(category, rating, price, minLength,maxLength, title, actors);
	System.out.println(totalRow+"<--totalRow");
	//마지막 페이지
	lastPage = ((totalRow - 1) / rowPerPage + 1); //마지막 페이지를 구하는 연산식
	System.out.println(lastPage+"<--lastPage");
 %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>filmSearchResult</title>
</head>
<body class="container">
	<h1>필름 리스트 뷰 검색 결과</h1>
	<a href="<%=request.getContextPath()%>/filmSearchForm.jsp" >검색창으로 돌아가기</a>
	<form method="get" action="<%=request.getContextPath()%>/filmSearchAction.jsp">
		<!-- 검색정보 그대로 전달하기 -->
		<input type="hidden" name ="category" value ="<%=category%>">
		<input type="hidden" name ="rating" value ="<%=rating%>">
		<input type="hidden" name ="price" value ="<%=price%>">
		<input type="hidden" name ="minLength" value ="<%=minLength%>">
		<input type="hidden" name ="maxLength" value ="<%=maxLength%>">
		<input type="hidden" name ="title" value ="<%=title%>">
		<input type="hidden" name ="actors" value ="<%=actors%>">
		<table class="table table-bordered">
		<thead>
			<tr>
				<th>FID</th>
				<th>title</th>
				<th>description</th>
				<th>category</th>
				<th>price</th>
				<th>length</th>
				<th>rating</th>
				<th>actors</th>
			</tr>
		<tbody>
				<%
					for(FilmList f :filmList){
				%>
			<tr>
				<td><%=f.getFID()%></td>
				<td><%=f.getTitle()%></td>
				<td><%=f.getDescription()%></td>
				<td><%=f.getCategory()%></td>
				<td><%=f.getPrice()%></td>
				<td><%=f.getLength()%></td>
				<td><%=f.getRating()%></td>
				<td><%=f.getActors()%></td>
			</tr>
			</tr>
				<%
				}
				 %>
		</tbody>
	</table>
	<!-- 페이지 목록 표시 부분 -->
		<!-- 이전 목록 표시 -->
		<%
			if(minPage > 10){
		 %>
		 		<button type = "submit" value ="<%=minPage-10%>" name = "minPage" class="btn btn-outline-secondary" >이전목록</button>
		 <%
			}
		 %>
		<!-- 이전 부분 -->
		<%
			if(currentPage>1){
		%>
		 		<button type = "submit" value ="<%=currentPage-1%>" name = "currentPage" class="btn btn-outline-secondary" >이전</button>
		<%
			}
		%>
		<!-- 목록 사이 번호 표시 -->
		<%
			for(int i = minPage; i<minPage+10; i=i+1){
				if(i<=lastPage){
					if(currentPage==i){
		%>
					<button type = "submit" value ="<%=i%>" name = "currentPage" class="btn btn-outline-primary"><%=i%></button>
		<%
					}else{
		%>
					<button type = "submit" value ="<%=i%>" name = "currentPage" class="btn btn-light"><%=i%></button>
		<%
					}
				}
			}	
		%>
		<!-- 다음 부분 -->
		<%
			if(currentPage<lastPage){
		%>
	 		<button type = "submit" value ="<%=currentPage+1%>" name = "currentPage" class="btn btn-outline-secondary" >다음</button>
		<%
			}
		%>
		<!-- 다음목록 표시 -->
		<%
			if(minPage+10<=lastPage){
		 %>
		 		<button type = "submit" value ="<%=minPage+10%>" name = "minPage" class="btn btn-outline-secondary">다음목록</button>
		 <%
			}
		 %>
	</form>
</body>
</html>
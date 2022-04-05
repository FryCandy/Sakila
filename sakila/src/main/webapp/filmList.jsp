<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="dao.FilmListDao" %>
<%@ page import ="dao.FilmDao" %>
<%@ page import ="dao.StoreDao" %>
<%@ page import ="vo.FilmList" %>
<%@ page import ="java.util.*" %>
<%
	//변수 선언 및 요청 값 받기
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
	int totalRow =0; //전체행 변수 초기화
	int lastPage = 0; //마지막 페이지 변수 초기화
	
	//검색기능
	String search = "title"; //검색 카테고리 변수 초기화,기본값 title
	if(request.getParameter("search")!=null){
		search = request.getParameter("search");
		System.out.println(search+"<--search");
	}
	String keyword = ""; //검색 내용 변수 초기화
	if(request.getParameter("keyword")!=null){
		keyword = request.getParameter("keyword");
		System.out.println(keyword+"<--keyword");
	}
	//film_in_stock 프로시저를 사용하여 영화별 inventory 정보 가져오기
	int filmId =0; //영화 정보 받기
	if(request.getParameter("filmId")!=null){
		filmId = Integer.parseInt(request.getParameter("filmId"));
	}
		System.out.println(filmId+"<--filmId");
	
	
	//ActorInfoDao 호출
	List<FilmList> list = new ArrayList<FilmList>();
	FilmListDao filmListDao = new FilmListDao();
	if(keyword.equals("")){//검색전 list 호출
		list =filmListDao.selectFilmListByPage(beginRow, rowPerPage);
	}else{//검색후 list 호출
		list = filmListDao.searchFilmList(search, keyword, beginRow, rowPerPage);
	}
	//storeId 정보 호출
	StoreDao storeDao = new StoreDao();
	List<Map<String, Object>> storelist = storeDao.selectStoreList(); 
	//프로시저 값 호출
	FilmDao filmDao = new FilmDao();
	//filmInStock
	List<Map<String, Object>> filmInStockList = new ArrayList<Map<String, Object>>(); 
	//store Id에 따른 모든값을 filmInStock에 저장
	for(Map m : storelist){
	Map<String,Object> map = filmDao.filmInStockCall(filmId,(Integer)m.get("storeId"));
	map.put("storeId", m.get("storeId"));
	filmInStockList.add(map);
	}
	//filmNotInStock
	List<Map<String, Object>> filmNotInStockList = new ArrayList<Map<String, Object>>(); 
	//store Id에 따른 모든값을 filmnotInStock에 저장
	for(Map m : storelist){
	Map<String,Object> map = filmDao.filmNotInStockCall(filmId,(Integer)m.get("storeId"));
	map.put("storeId", m.get("storeId"));
	filmNotInStockList.add(map);
	}
	//전체 행의 수 값 호출
	totalRow = filmListDao.totalRow();
	System.out.println(totalRow+"<-totalRow");
	//연산식
	lastPage = ((totalRow - 1) / rowPerPage + 1); //마지막 페이지를 구하는 연산식
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>FilmList</title>
</head>
<body class = "container">
	<h1>FilmList</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<form method="post" action = "<%=request.getContextPath()%>/filmList.jsp" >
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
					for(FilmList f : list){
				%>
			<tr>
				<td><%=f.getFID()%></td>
				<td><button type ="submit" class="btn btn-light" name="filmId" value="<%=f.getFID()%>"><%=f.getTitle()%></button> </td>
				<td><%=f.getDescription()%></td>
				<td><%=f.getCategory()%></td>
				<td><%=f.getPrice()%></td>
				<td><%=f.getLength()%></td>
				<td><%=f.getRating()%></td>
				<td><%=f.getActors()%></td>
			</tr>
				<%
					if(filmInStockList.size()!=0 && f.getFID()==filmId && filmId !=0){
				%>		
						<tr>
						<td colspan="7">filmInstockList</td>
						<td>
							<button type ="submit" class="btn btn-light" name="filmId" value="0">닫기</button>
						</td>
				<%		
						for(Map m : filmInStockList){
				 %>	
				 	<tr>		
				 		<td colspan="2">storeID:<%=m.get("storeId") %></td>
						<td colspan="6"><%=m.get("list")%></td>
					</tr>
				<%
						}
				%>						
						</tr>
				<%
					}
				%>
				<%
					if(filmNotInStockList.size()!=0 && f.getFID()==filmId && filmId !=0){
				%>		
						<tr>
						<td colspan="7">filmNotInstockList</td>
						<td>
							<button type ="submit" class="btn btn-light" name="filmId" value="0">닫기</button>
						</td>
				<%		
						for(Map m : filmNotInStockList){
				 %>	
				 	<tr>		
				 		<td colspan="2">storeID:<%=m.get("storeId") %></td>
						<td colspan="6"><%=m.get("list")%></td>
					</tr>
				<%
						}
				%>						
						</tr>
				<%
					}
				}
				 %>
		</tbody>
	</table>
	<!-- 현재 페이지에 정보를 갱신하는 from -->
	<!-- 검색 기능 부분 -->
	<div>
		<select name ="search" >
			<option value = <%=search%>><%=search%></option>
			<option value ="title">title</option>
			<option value ="description">description</option>
			<option value ="category">category</option>
			<option value ="rating">rating</option>
		</select>
		<input type = text name ="keyword" value="<%=keyword %>">
	<button type = "submit" class="btn btn-outline-info">검색</button>
	</div>		
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

<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="dao.CustomerListDao" %>
<%@ page import ="vo.CustomerList" %>
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
		if(request.getParameter("msinPage")!= null){
	minPage = Integer.parseInt(request.getParameter("minPage"));
	currentPage=minPage;
		}else{
	minPage = (currentPage-1)/10*10+1;//페이징에 표시 될 가장 작은 페이지숫자 :일의 자리수는 1이고, 십이상의 자리수는 currentPage와 동일한 minPage
		}
		System.out.println(minPage+"<--minPage");
	int totalRow =0; //전체행 변수 초기화
	int lastPage = 0; //마지막 페이지 변수 초기화
	String name = ""; //배우 이름 검색
	if(request.getParameter("name")!=null){
		name = request.getParameter("name");
		System.out.println(name+"<--name");
	}
	
	//ActorInfoDao 호출
	List<CustomerList> list = new ArrayList<CustomerList>();
	CustomerListDao customerListDao = new CustomerListDao();
	//검색전 list 호출
	list = customerListDao.selectCustomerListByPage(beginRow, rowPerPage);
	totalRow = customerListDao.totalRow();
	System.out.println(totalRow+"<-totalRow");
	//검색기능
		String search ="name"; //검색 카테고리 기본값 name 
		if(request.getParameter("search")!=null){
			search = request.getParameter("search");
			System.out.println(search+"<--search");
		}
		String keyword = ""; //검색내용 변수 초기화
		if(request.getParameter("keyword")!=null){
			keyword = request.getParameter("keyword");
			System.out.println(keyword+"<--keyword");
		}
		//배우이름검색시 리스트
		if(search.equals("name")&&!keyword.equals("")){
			list = customerListDao.searchCustomerListByname(keyword, beginRow, rowPerPage);
		//영화정보검색시 리스트
		}else if(search.equals("city")&&!keyword.equals("")){
			list = customerListDao.searchCustomerListByCity(keyword, beginRow, rowPerPage) ;
		}else if(search.equals("country")&&!keyword.equals("")){
			list = customerListDao.searchCustomerListBycountry(keyword, beginRow, rowPerPage) ;
		}
	//연산식
	lastPage = ((totalRow - 1) / rowPerPage + 1); //마지막 페이지를 구하는 연산식
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>customerList</title>
</head>
<body class = "container">
	<h1>customerList</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>ID</th>
				<th>name</th>
				<th>address</th>
				<th>zipCode</th>
				<th>phone</th>
				<th>city</th>
				<th>country</th>
				<th>notes</th>
				<th>SID</th>
			</tr>
		<tbody>
				<%
				for( CustomerList c : list){
				%>
			<tr>
				<td><%=c.getID()%></td>
				<td><%=c.getName()%></td>
				<td><%=c.getAddress()%></td>
				<td><%=c.getZipCode()%></td>
				<td><%=c.getPhone()%></td>
				<td><%=c.getCity()%></td>
				<td><%=c.getCountry()%></td>
				<td><%=c.getNotes()%></td>
				<td><%=c.getSID()%></td>
			</tr>
				<%
					}
				 %>
		</tbody>
	</table>
	<!-- 현재 페이지에 정보를 갱신하는 from -->
	<form method="post" action = "<%=request.getContextPath()%>/customerList.jsp" >
	<!-- 검색 기능 부분 -->
	<div>
		<select name ="search"  value = <%=search%>>
			<option value ="name">name</option>
			<option value ="city">city</option>
			<option value ="country">country</option>
		</select>
	<input type = text name ="keyword">
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
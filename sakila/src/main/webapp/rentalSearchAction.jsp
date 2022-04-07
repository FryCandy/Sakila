<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="dao.*" %>
<%
	//요청값 받기
	//1.검색관련
	//storeId
	int storeId =-1; //선택 없음 = -1
	if(request.getParameter("storeId")!=null&&!request.getParameter("storeId").equals("")){
		storeId = Integer.parseInt(request.getParameter("storeId"));
		System.out.println(storeId+"<--storeId");//디버깅
	}
	//customerName
	String customerName ="";
	if(request.getParameter("customerName")!=null){
		customerName = request.getParameter("customerName");
		System.out.println(customerName+"<--customerName");//디버깅
	}
	//beginDate
	String beginDate ="";
	if(request.getParameter("beginDate")!=null){
		beginDate = request.getParameter("beginDate");
		System.out.println(beginDate+"<--beginDate");//디버깅
	}
	//endDate
	String endDate ="";
	if(request.getParameter("endDate")!=null){
		endDate = request.getParameter("endDate");
		System.out.println(endDate+"<--endDate");//디버깅
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
	RentalDao rentalDao = new RentalDao();
	//검색 후 리스트
	List<Map<String,Object>> rentalList = rentalDao.selectRentalSearchList(storeId, customerName, beginDate, endDate, beginRow, rowPerPage);
	//검색후 전체 게시물 수
	int totalRow =rentalDao.totalRow(storeId, customerName, beginDate, endDate);
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
	<title>rentalSearchAction</title>
</head>
<body class="container">
	<h1>대여 테이블 상세 검색 결과</h1>
	<a href="<%=request.getContextPath()%>/rentalSearchForm.jsp" >검색창으로 돌아가기</a>
	<form method="get" action="<%=request.getContextPath()%>/rentalSearchAction.jsp">
		<!-- 검색정보 그대로 전달하기 -->
		<input type="hidden" name ="storeId" value ="<%=storeId%>">
		<input type="hidden" name ="customerName" value ="<%=customerName%>">
		<input type="hidden" name =beginDate" value ="<%=beginDate%>">
		<input type="hidden" name =endDate value ="<%=endDate%>">
		<!-- 상세검색 결과 테이블 -->
		<table class="table table-bordered">
		<thead>
			<tr>
				<th>rentalId</th>
				<th>rentalDate</th>
				<th>inventoryId</th>
				<th>customerId</th>
				<th>returnDate</th>
				<th>staffId</th>
				<th>lastUpdate</th>
				<th>customerName</th>
				<th>storeId</th>
				<th>filmId</th>
				<th>title</th>
			</tr>
		<tbody>
				<%
				for(Map m : rentalList) {
				%>
			<tr>
				<td><%=m.get("rentalId")%></td>
				<td><%=m.get("rentalDate")%></td>
				<td><%=m.get("inventoryId")%></td>
				<td><%=m.get("customerId")%></td>
				<td><%=m.get("returnDate")%></td>
				<td><%=m.get("staffId")%></td>
				<td><%=m.get("lastUpdate")%></td>
				<td><%=m.get("customerName")%></td>
				<td><%=m.get("storeId")%></td>
				<td><%=m.get("filmId")%></td>
				<td><%=m.get("title")%></td>
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
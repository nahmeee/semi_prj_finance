<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" 	uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<table border=1  width=30%>
<tr>
	<th>날짜</th>
	<th>적요</th>
	<th>계정ID</th>
	<th>계정명</th>
	<th>차변</th>
	<th>대변</th>
	<th>잔액</th>
</tr>
<c:forEach var="vo" items="${KEY_SUM_FLIST}">
	<tr style="${empty vo.voucher_date ? 'font-weight:bold; color:red;' : ''}">
		<td>${vo.voucher_date}</td>
		<td>${vo.descript}</td>
		<td>${vo.account_id}</td>
		<td>${vo.account_name}</td>
		<td>${vo.debit}</td>
		<td>${vo.credit}</td>
		<td>${vo.diff}</td>
	</tr>	
</c:forEach>
</table>
<br><br>
<table border=1  width=30%>
<tr>
	<th>부모계정</th>
	<th>계정명</th>
	<th>잔액</th>
</tr>
<c:forEach var="vo" items="${KEY_STATE_FLIST}">
	<tr>
		<td>${vo.parent_type}</td>
		<td>${vo.account_name}</td>
		<td>${vo.diff}</td>
	</tr>	
</c:forEach>
</table>

</table>
<br><br>
<table border=1  width=30%>
<tr>
	<th>부모계정</th>
	<th>계정명</th>
	<th>잔액</th>
</tr>
<c:forEach var="vo" items="${KEY_INCOME_FLIST}">
	<tr>
		<td>${vo.parent_type}</td>
		<td>${vo.account_name}</td>
		<td>${vo.diff}</td>
	</tr>	
</c:forEach>
</table>




<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$( document ).ready(function() {
	//$("#btn").click( function() {  
	//    	$("#input").val();
	//});
});
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>ManchesterUnited</h1>
	<table>
		<td>${boardOne.title }</td>
		<td>${boardOne.content }</td>
		<td>${boardOne.regDate }</td>
	</table>
	<a href="/boardUpdate.do?bno=${boardOne.bno }">수정</a>
</body>
</html>
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
		<tr>
			<th>��ȣ</th>
			<th>����</th>
			<th>�ۼ���</th>
			<th>�ۼ���¥</th>
		</tr>
		<c:forEach items="${boardList}" var="board">
		
			<tr>
				<td>${board.bno }</td>

				<td><a href="/boardOne.do?bno=${board.bno }">${board.title }</a></td>
				
				<td>${board.writer}</td>
				
				<td>${board.regDate }</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/boardWrite.do">�۾���</a>
</body>
</html>
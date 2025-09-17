<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>MEMO ADD(/memo/add)</h1>

	<form action="/memo/memoForm" method="post">
		<div>
			<label>name : </label>  <span>${name}</span><br>
			<textarea name="name" /></textarea>
		</div>
		<div>
			<label>content : </label>  <span>${content}</span><br>
			<input name="content" />
		</div>
		<div>
			<input type="submit" value="메모쓰기" />
		</div>
	</form>

</body>
</html>
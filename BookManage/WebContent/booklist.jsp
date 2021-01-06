<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="database.Book"%>
<%@page import="DBTool.DBUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Sale</title>
<link rel="stylesheet" href="./css/common.css" />
</head>
<body>
	<%
		List<Book> bookList = new ArrayList<Book>();
	try {
		Connection conn = DBUtil.getConnection((String) session.getAttribute("username"),
		(String) session.getAttribute("password"));
		Statement st = conn.createStatement();
		String sql = "select* from booklist";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			/* System.out.print("找到！！"); */
			Book b = new Book();
			b.setId(rs.getInt("id"));
			b.setName(rs.getString("name"));
			b.setPrice(rs.getFloat("price"));
			b.setRemain(rs.getInt("remain"));
			bookList.add(b);
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		DBUtil.Close();
	}
	%>
	<div class="table_a">
		<table>
			<tr>
				<th>序列号</th>
				<th>书名</th>
				<th>价格</th>
				<th>剩余数量</th>
			</tr>
			<%
				for (int i = 0; i < bookList.size(); i++) {
			%>
			<tr>
				<td><%=bookList.get(i).getId()%></td>
				<td><%=bookList.get(i).getName()%></td>
				<td><%=bookList.get(i).getPrice()%></td>
				<td><%=bookList.get(i).getRemain()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	
	<div class="center">
	<h2>
		用户<%=session.getAttribute("username_u")%>请求添加<%=session.getAttribute("name")%>书<%=session.getAttribute("remain")%>本，该书的价格为<%=session.getAttribute("price")%>。
	</h2>
	</div>
	<form action="${pageContext.request.contextPath}/add_servlet"
		method="post">
		<div class="form_sub fl">
			<input type="hidden" name="agree" value="1"> <input
				type="submit" name="submit" value="同意请求">
		</div>
	</form>
	<form action="${pageContext.request.contextPath}/add_servlet"
		method="post">
		<div class="form_sub fr">
			<input type="hidden" name="agree" value="0"> <input
				type="submit" name="submit" value="拒绝请求">
		</div>
	</form>
	<div class="a">
	<a href="login.jsp">返回登录页面</a>
	</div>
</body>
</html>
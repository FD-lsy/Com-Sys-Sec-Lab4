package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
//import java.io.Writer;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import DBTool.DBUtil;

@WebServlet(name = "column_add_servlet", urlPatterns = { "/column_update" })
public class ColumnsUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ColumnsUpdateServlet() {
		super();
	}
	Logger logger=Logger.getLogger(ColumnsUpdateServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// ����������������ȡ������
		String User = request.getParameter("User");
		String operation = request.getParameter("operation");
		String priv_0 = request.getParameter("priv");
		String columnName = request.getParameter("columnName");
		String priv = "";//Ȩ��
		switch (priv_0) {
		case "s":
			priv = "Select("+columnName+")";
			break;
		case "i":
			priv = "Insert("+columnName+")";
			break;
		case "u":
			priv = "Update("+columnName+")";
			break;
		case "si":
			priv = "Select("+columnName+"),Insert("+columnName+")";
			break;
		case "su":
			priv = "Select("+columnName+"),Update("+columnName+")";
			break;
		case "iu":
			priv = "Insert("+columnName+"),Update("+columnName+")";
			break;
		case "siu":
			priv = "Select("+columnName+"),Insert("+columnName+"),Update("+columnName+")";
			break;
		}
		String username = "";
		String password = "";
		HttpSession session = request.getSession();
		username = (String) session.getAttribute("username");
		password = (String) session.getAttribute("password");
		try {
			Connection conn = DBUtil.getConnection(username, password);
			if (conn == null) {
				String a = URLEncoder.encode("���¼��", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
			}
			String sql = "";
			Statement st = conn.createStatement();
			if (operation.equals("���")) {
				sql = "grant "+ priv + "  on booklist"+ " to '" + User + "'@'localhost'";
			} else if (operation.equals("ɾ��")) {
				sql = "revoke "+ priv + " on booklist"+ " from '" + User + "'@'localhost'";
			} else {
				String a = URLEncoder.encode("�����ʽ����", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='admin.jsp'</script>");
				out.flush();
				out.close();
			}
			st.execute(sql);
			logger.info(operation + "�û�" + User + "�Ա��booklist��Ȩ��"+priv);
			String a = URLEncoder.encode("Ȩ��"+operation+"�ɹ���", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='admin.jsp'</script>");
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}

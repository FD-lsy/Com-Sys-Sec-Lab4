package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import DBTool.DBUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



@WebServlet(name = "login_servlet", urlPatterns = { "/login_servlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		Logger logger=Logger.getLogger(DealServlet.class);
		String username="";
		String password="";
		username = request.getParameter("username");
		password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.setAttribute("username",username);
		session.setAttribute("password",password);
		PrintWriter out = response.getWriter();
		try {
			Connection conn = DBUtil.getConnection(username,password);
			if(conn==null) {
				String a = URLEncoder.encode("���ȵ�¼��", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
			}else {
			Statement st = conn.createStatement();
			String sql = "select * from user where username='"+username+"' and password='"+password+"'";
			ResultSet rs = st.executeQuery(sql);
			// �û�������Ϊ��ͨ�û�
			if (rs.next()) {
				if (rs.getInt("admin") == 0) {
					String a = URLEncoder.encode("��Ϊ��ͨ�û����޷���½����Աҳ�棡", "UTF-8");
					out.print(
							"<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
					out.flush();
					out.close();
				}
				// �û�������Ϊ����Ա
				else if (rs.getInt("admin") == 1) {
					logger.info("����Ա��¼�ɹ���");
					String a = URLEncoder.encode("�����������Աҳ��", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='booklist.jsp'</script>");
					out.flush();
					out.close();
				}
			}else {
				String a = URLEncoder.encode("�û����������������������", "UTF-8");
				out.print(
						"<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
			}}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}
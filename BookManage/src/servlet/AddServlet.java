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

@WebServlet(name = "add_servlet", urlPatterns = { "/add_servlet" })
public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AddServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger logger=Logger.getLogger(DealServlet.class);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 得到请求的参数
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		String price = (String) session.getAttribute("price");
		String remain = (String) session.getAttribute("remain");
		String username_u = (String) session.getAttribute("username_u");
		String password_u = (String) session.getAttribute("password_u");
		// 得到管理员用户的信息
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String agree = request.getParameter("agree");
		if (agree.equals("0")) {
			logger.info("管理员决绝用户"+username_u+"的请求，直接返回用户页面！");
			response.sendRedirect("http://localhost:8080/BookSale/login_servlet?username="+username_u+"&password="+password_u);
		} else {
			try {
				Connection conn = DBUtil.getConnection(username, password);
				if (conn == null) {
					String a = URLEncoder.encode("需要管理员权限，管理员请登录！", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='login.jsp'</script>");
					out.flush();
					out.close();
				}
				logger.info("管理员同意请求，并根据图书信息执行添加操作！");
				Statement st = conn.createStatement();
				String sql1 = "insert into booklist(name,price,remain) values('" + name + "','" + price + "','" + remain
						+ "')";
				st.execute(sql1);
				response.sendRedirect("http://localhost:8080/BookSale/login_servlet?username="+username_u+"&password="+password_u);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DBUtil.Close();
			}
		}
	}
}

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


@WebServlet(name = "deal_servlet", urlPatterns = { "/deal_servlet" })
public class DealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DealServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Logger logger=Logger.getLogger(DealServlet.class);
		HttpSession session = request.getSession();
		//获得参数并存入session
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String remain = request.getParameter("remain");
		String username_u = request.getParameter("username_u");
		String password_u = request.getParameter("password_u");
		
		session.setAttribute("name", name);
		session.setAttribute("price", price);
		session.setAttribute("remain", remain);
		session.setAttribute("username_u", username_u);
		session.setAttribute("password_u", password_u);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
				String a = URLEncoder.encode("需要向管理员发送请求！", "UTF-8");
				logger.info("管理员服务器将用户"+username_u+"的请求信息存入session，然后跳转到管理员登录页面");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}
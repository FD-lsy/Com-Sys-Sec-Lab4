package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import DBTool.DBUtil_mysql;
import database.Priv;

@WebServlet(name = "column_priv_servlet", urlPatterns = { "/column_priv" })
public class ColumnsPrivServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ColumnsPrivServlet() {
		super();
	}
	Logger logger=Logger.getLogger(ColumnsPrivServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Priv> privlist2 = new ArrayList<Priv>();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String username = "";
		String password = "";
		HttpSession session = request.getSession();
		username = (String) session.getAttribute("username");
		password = (String) session.getAttribute("password");
		try {
			Connection conn = DBUtil_mysql.getConnection(username, password);
			if (conn == null) {
				String a = URLEncoder.encode("请登录！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
			}
			Statement st = conn.createStatement();
			String sql = "select * from columns_priv where Table_name = 'booklist'";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				/* System.out.print("找到！！"); */
				Priv p = new Priv();
				p.setUser(rs.getString("User"));
				p.setColumnName(rs.getString("Column_name"));
				String[] a = rs.getString("Column_priv").split(",|，");
				String[] b = new String[10];
				for (int i = 0; i < a.length; i++) {
					if(a[i].equals("Select")) {
						b[0]=a[i];
					}
					else if(a[i].equals("Insert")) {
						b[1]=a[i];
					}
					else if(a[i].equals("Update")) {
						b[2]=a[i];
					}
				}
				p.getMap().put("Select", b[0]);
				p.getMap().put("Insert", b[1]);
				p.getMap().put("Update", b[2]);
				privlist2.add(p);
			}
			logger.info("查询并返回对booklist表格所有行有权限的用户的Select、Insert、Update权限");
			if (privlist2.size() == 0) {
				String a = URLEncoder.encode("暂无书籍", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "');</script>");
				out.flush();
				out.close();
			} else {
				request.setAttribute("privlist2", privlist2);
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil_mysql.Close();
		}
	}
}
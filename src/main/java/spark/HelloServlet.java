package spark;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> names;
	private long count;

	public HelloServlet(List<String> names, long count) {
		this.names = names;
		this.count = count;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getQueryString();
		if (query != null) {
			if (query.contains("count")) {
				resp.getWriter().println("Count: " + count);
			}
		} else {
			resp.getWriter().println(names);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Greetable greeting = null;
		String name = req.getParameter("name");
		String lang = req.getParameter("lang");

		if (name != null) {
			names.add(name);
		} else {
			name = "Anonymous";
		}

		if (lang != null) {
			if (lang.equals("es")) {
				greeting = (n) -> "Hola, " + n;
			} else if (lang.equals("de")) {
				greeting = (n) -> "Guten tag, " + n;
			}
		} else {
			greeting = (n) -> "Hello, " + n;
		}

		resp.getWriter().println(greeting.greet(name));
	}
}

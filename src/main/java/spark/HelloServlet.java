package spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
	List<String> names;
	@Override
	public void init() throws ServletException {
		names = new ArrayList<>();
		names.add("Mehrab");
		names.add("Sutter");
		names.add("Jeremy");
		names.add("Daniel");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String lang = req.getParameter("lang");
		if (name != null && lang != null) {
			names.add(name);
			if (lang.equals("en")) {
				resp.getWriter().println(new HelloGreeter().greet(name));
			}
			else if (lang.equals("es")) {
				Greetable holaGreeter = new Greetable(){
				
					@Override
					public String greet(String name) {
						return "Hola, " + name;
					}
				};
				resp.getWriter().println(holaGreeter.greet(name));
			}
			else if (lang.equals("de")) {
				Greetable gutentagGreetable = (n) -> {return "Guten tag, " + n;};
				resp.getWriter().println(gutentagGreetable.greet(name));
				Runnable runner = () -> {System.out.println("Running thread");};
				new Thread(runner).start();
			}
		}
		
		for (String n : names) {
			resp.getWriter().println(n);
		}
		names.stream().map((n) -> {return n + "!";}).forEach((n) -> {System.out.println(n);});
		names.forEach((x) -> {System.out.println(x);});
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("sent to post");
	}
}

package ctrl;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Osap
 */
@WebServlet({"/Osap"})
public class Osap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Osap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("Hello, get a GET request from osap");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
		resOut.write("Hello world!\n");
		String clientIP = request.getRemoteAddr();
		resOut.write("Client IP: " + clientIP + "\n" );
		String clientQueryString = request.getQueryString();
		int clientport = request.getServerPort();
		resOut.write("Client Port: " + clientport + "\n");
		resOut.write("This IP has been flagged\n");
		String Protocol = request.getProtocol();
		resOut.write("Client Protocol:" + Protocol + "\n");
		String Method = request.getMethod();
		resOut.write("Client Method: " + Method + "\n");
		resOut.write("query string: "+clientQueryString+"\n");
		String foo = request.getParameter("foo");
		resOut.write("Query Param foo=" + foo + "\n");
		String URI = request.getRequestURI();
		resOut.write("Request URI: " + URI + "\n");
		String Servletpath = request.getServletPath();
		resOut.write("Request Servlet path: " + Servletpath +"\n");
		ServletContext context= this.getServletContext();
		String applicationName = context.getInitParameter("applicationName");
		String principal = context.getInitParameter("principal");
		String period = context.getInitParameter("period");
		String Interest = context.getInitParameter("Interest");
		String applicantName = context.getInitParameter("applicantName");
		resOut.write("---Info from context object---\n");
		resOut.write("Application Name: " + applicationName + "\n");
		//resOut.write("principal: " + principal + "\n");
		String contextPath=context.getContextPath();
		String realPath=context.getRealPath("Osap");
		resOut.write("Context path: " + contextPath + "\n");
		resOut.write("Real path of osap\n " + realPath + "\n");
		resOut.write("Applicant Name: " + applicantName + "\n");
		resOut.write("---Session info---\n");
		resOut.write("---Montly Payment---\n");
		//String principal1 = request.getParameter("principal");
		String principalParam = request.getParameter("principal");
		String InterestParam = request.getParameter("Interest");
		String PeriodParam = request.getParameter("period");
		if (principalParam == null)
			principalParam = principal;
		//Double dPrincipal = Double.parseDouble(principalParam);
		resOut.write("based on principal: " + Double.parseDouble(principalParam));
		if (PeriodParam == null)
			PeriodParam = period;
		resOut.write("	Period: " + Double.parseDouble(PeriodParam));
		if (InterestParam == null)
			InterestParam = Interest;
		resOut.write("	Interest " + Double.parseDouble(InterestParam) + "\n");

		Double A = Double.parseDouble(principalParam);
		Double r = Double.parseDouble(InterestParam);
		Double n = Double.parseDouble(PeriodParam);
		//resOut.write("Integer a: " + A.toString() + "\n");
		 
		Double result = ((r/12) * (A/(1-Math.pow(1+(r/12), -n))));
		resOut.write("result: " + result.toString() + "\n");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

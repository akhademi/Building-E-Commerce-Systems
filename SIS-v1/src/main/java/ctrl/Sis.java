package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.*;
import dao.*;

/**
 * Servlet implementation class Sis
 */
@WebServlet("/Sis")
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sis() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			StudentDAO sd=new StudentDAO();
			String prefix = request.getParameter("prefix");
			String strCredit = request.getParameter("credit");
			int credit = Integer.parseInt(strCredit);
			Map<String, StudentBean> std = sd.retrieve(prefix, credit);
			String result = "<table border=\"1\"> <thead> <td>Student Name </td> <td>Credits taken </td> <td>Credits to Graduate </td> </thead>";
			Iterator iterator = std.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry me = (Map.Entry) iterator.next();
				StudentBean sb=(StudentBean)me.getValue();
				result+="<tr>"+
				"<td>"+sb.getName()+"</td>"+
				"<td>"+sb.getCredit_taken()+"</td>"+
				"<td>"+sb.getCredit_graduate()+"</td>"+
				"</tr>";
			}
			result+="</thead></table>";
			
			//response.getWriter().append("data base values = ").append(result).append("\n");
			PrintWriter out = response.getWriter();
			out.printf(result);
			out.flush();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

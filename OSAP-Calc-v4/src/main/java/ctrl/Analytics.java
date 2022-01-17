package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * Servlet implementation class admin
 */
@WebServlet("/admin")
public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analytics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		model.Loan loan = (model.Loan) request.getServletContext().getAttribute("mLoan");
		Writer w = response.getWriter();
		w.append("Served at: ").append(request.getContextPath()).append("\n");
		if (loan != null) {
			Double d = loan.m_maxPrincipal;
			w.append(" current max= ").append(d.toString()).append("\n"); //Stupid Java
		}
		String strMax = request.getSession().getAttribute("ParamMaxPrincipal").toString();
		w.append("value for max principal = ").append(strMax);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

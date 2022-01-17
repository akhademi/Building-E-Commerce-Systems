package ctrl;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Loan;


/**
 * Servlet implementation class Osap
 */
@WebServlet({"/Osap"})
public class Osap extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String F_Grace = "grace";
    private static final String F_Payment = "payment";
    
    private ServletConfig m_config;
	private ServletContext m_context;
    private String ConfigPrincipal;
    private String ConfigFixedRate;
	private String ConfigGracePeriod;
	private String ConfigGrace;
	
	private String m_strMessage;
    
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
		String TargetForm = "/Result.jspx";
		HttpSession session = request.getSession();
		model.Loan loan = (Loan) m_context.getAttribute("mLoan");
		
		
		session.setAttribute("applicationName", m_context.getInitParameter("applicationName"));
		
		String ParamCalculate = request.getParameter("calculate");
		if (ParamCalculate == null)
			ParamCalculate = "false";
		
		if (ParamCalculate.compareTo("true") == 0) { // call from UI.jspx
			loan.CheckAndExtractFormParams(request);
			
			if (loan.m_hasError == false) {
			
				loan.m_graceInterest = loan.computeGraceInterest(ConfigFixedRate, ConfigGracePeriod);
				
				loan.m_monthlyPayment = loan.computePayment(loan.m_graceInterest, ConfigGracePeriod);
				
				request.setAttribute(F_Grace, loan.m_graceInterest);
					
				request.setAttribute(F_Payment, loan.m_monthlyPayment);

				loan.m_strMessage = "";

				TargetForm = "/Result.jspx";
			}
			else { // some error message 
				session.setAttribute("ParamError", loan.m_strMessage);
				TargetForm = "/UI.jspx";
			}
		}
		else { // call from result.jspx
			loan.m_strMessage = "";
			session.setAttribute("ParamError", "");
			TargetForm = "/UI.jspx";
		}
		
		session.setAttribute("ParamPrincipal", loan.m_principal);
		session.setAttribute("ParamRate", loan.m_rate);
		session.setAttribute("ParamPeriod", loan.m_period);
		session.setAttribute("ParamGrace", loan.m_grace);
		session.setAttribute("ParamError", loan.m_strMessage);
		request.getRequestDispatcher(TargetForm).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void init (ServletConfig config) throws ServletException
	{
		super.init(config);
		m_config = config;
		m_context= this.getServletContext();
		
		m_strMessage = "";
		Boolean bError = false;
		
		ConfigPrincipal = m_context.getInitParameter("principal");
		if (ConfigPrincipal == null ) {
			bError = true;
			m_strMessage = m_strMessage + "Config Principal is null\n";
		}
		ConfigFixedRate = m_context.getInitParameter("fixedRate");
		if (ConfigFixedRate == null ) {
			bError = true;
			m_strMessage = m_strMessage + "Config fixedRate is null\n";
		}
		ConfigGracePeriod = m_context.getInitParameter("gracePeriod");
		if (ConfigGracePeriod == null ) {
			bError = true;
			m_strMessage = m_strMessage + "Config Grace Period is null\n";
		}
		ConfigGrace = m_context.getInitParameter("grace");
		if (ConfigGrace == null ) {
			bError = true;
			m_strMessage = m_strMessage + "Config Grace is null\n";
		}
		
		m_context.setAttribute("mLoan", new model.Loan());

	}
}

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
    private static final String F_Grace = "grace";
    private static final String F_Payment = "payment";
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
		/*
		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
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
		*/
		Boolean bError = false;
		String strMessage = "";
		String TargetForm = "/Result.jspx";
		ServletContext context= this.getServletContext();
		Writer resOut = response.getWriter();

		
		String ConfigPrincipal = context.getInitParameter("principal");
		if (ConfigPrincipal == null ) {
			bError = true;
			strMessage += "Config Principal is null\n";
		}
		String ConfigFixedRate = context.getInitParameter("fixedRate");
		if (ConfigFixedRate == null ) {
			bError = true;
			strMessage += "Config fixedRate is null\n";
		}
		String ConfigGracePeriod = context.getInitParameter("gracePeriod");
		if (ConfigGracePeriod == null ) {
			bError = true;
			strMessage += "Config Grace Period is null\n";
		}
		String ConfigGrace = context.getInitParameter("grace");
		if (ConfigGrace == null ) {
			bError = true;
			strMessage += "Config Grace is null\n";
		}

		String ParamPrincipal = request.getParameter("principal");
		if (ParamPrincipal == null ) {
			bError = true;
			strMessage += "Param Principal is null\n";
		}
		String ParamRate = request.getParameter("rate");
		if (ParamRate == null ) {
			bError = true;
			strMessage += "Param Rate is null\n";
		}
		String ParamPeriod = request.getParameter("period");
		if (ParamPeriod == null ) {
			bError = true;
			strMessage += "Param Period is null\n";
		}
		String ParamGrace = request.getParameter("grace");
		if (ParamGrace == null ) {
			ParamGrace = "no";
		}
		String ParamCalculate = request.getParameter("calculate");
		if (ParamCalculate == null)
			ParamCalculate = "false";
		
		if (ParamCalculate.compareTo("true") == 0) { // call from UI.jspx
		
			if (bError == false) {
				Double ValueParamPrincipal = Double.parseDouble(ParamPrincipal);
				Double ValueParamRate = Double.parseDouble(ParamRate);
				Double ValueParamPeriod = Double.parseDouble(ParamPeriod);
				Double ValueConfigFixedRate = Double.parseDouble(ConfigFixedRate);
				Double ValueConfigGracePeriod = Double.parseDouble(ConfigGracePeriod);
				if (ValueConfigGracePeriod == 0) {
					ValueConfigGracePeriod = 1.0;
				}
				
				Double ValueGraceInterest = ValueParamPrincipal * ((ValueParamRate + ValueConfigFixedRate) / 12) * ValueConfigGracePeriod; 
				
				resOut.write("value grace interest : " + ValueGraceInterest.toString()); 
				Double MonthlyPayment = ((ValueParamRate /12) * (ValueParamPrincipal/(1-Math.pow(1+(ValueParamRate / 12), -ValueParamPeriod))));
				resOut.write("value pure monthly : " + MonthlyPayment.toString() + "\n"); 
				
				MonthlyPayment += (ValueGraceInterest / ValueConfigGracePeriod);
				resOut.write("value monthly Payment: " + MonthlyPayment.toString() + "\n");
				
				resOut.write("config principal: " + ConfigPrincipal + "\n");
				resOut.write("config rate: " + ConfigFixedRate + "\n");
				resOut.write("config grace period: " + ConfigGracePeriod + "\n");
				resOut.write("config grace: " + ConfigGrace + "\n");
				
				resOut.write("param principal: " + ParamPrincipal + "\n");
				resOut.write("param rate: " + ParamRate + "\n");
				resOut.write("param period: " + ParamPeriod + "\n");
				resOut.write("param grace: " + ParamGrace + "\n");
				
				if (ParamCalculate != null )
				  resOut.write("param calcuate: " + ParamCalculate + "\n");

				
				
				request.setAttribute(F_Grace, String.format("$%.2f", ValueGraceInterest));
				
				request.setAttribute(F_Payment, String.format("$%.2f", MonthlyPayment) );
				
				TargetForm = "/Result.jspx";
			}
			else {
				resOut.write("Error happened becuase " + strMessage + "\n");
				TargetForm = "/UI.jspx";
			}
		}
		else { // call originally from Result.jspx 
			TargetForm = "/UI.jspx";
		}
		
		request.getRequestDispatcher(TargetForm).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

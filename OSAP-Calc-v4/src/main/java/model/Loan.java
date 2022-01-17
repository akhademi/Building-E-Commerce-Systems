package model;

import javax.servlet.http.HttpServletRequest;

public class Loan {

	public double m_principal;
	public double m_rate;
	public double m_period;
	public String m_grace;
	public String m_strMessage;
	public boolean m_hasError;
	public double m_graceInterest;
	public double m_monthlyPayment;
	
	// Additions for Lab 4
	public double m_maxPrincipal;
	
	public Loan () {
		m_principal = 0;
		m_rate = 0;
		m_period = 0;
		m_grace = "no";
		m_strMessage = "";
		m_hasError = false;
		m_graceInterest = 0;
		m_monthlyPayment = 0;
		
		// Addition for Lab 4
		m_maxPrincipal = 0;
	}
	
	
	public void CheckAndExtractFormParams(HttpServletRequest request) {
	    m_hasError = false;
	    m_strMessage = "";
		String ParamCalculate = request.getParameter("calculate");
		if (ParamCalculate == null)
			ParamCalculate = "false";

		String ParamAjax = request.getParameter("ajax");
		if (ParamAjax == null)
			ParamAjax = "false";

		if (ParamCalculate.compareTo("true") == 0 || ParamAjax.compareTo("true") == 0) { 
			String ParamPrincipal = request.getParameter("principal");
			if (ParamPrincipal == null ) {
				m_hasError = true;
				m_strMessage = m_strMessage + "Param Principal is null\n";
			}
			String ParamRate = request.getParameter("rate");
			if (ParamRate == null ) {
				m_hasError = true;
				m_strMessage = m_strMessage + "Param Rate is null\n";
			}
			String ParamPeriod = request.getParameter("period");
			if (ParamPeriod == null ) {
				m_hasError = true;
				m_strMessage = m_strMessage + "Param Period is null\n";
			}
			String ParamGrace = request.getParameter("grace");
			if (ParamGrace == null ) {
				ParamGrace = "no";
			}
			
			if (m_hasError == false) {
				float ValueParamPrincipal = Float.parseFloat(ParamPrincipal);
				if (ValueParamPrincipal <= 0 ) {
					m_hasError = true;
					m_strMessage = m_strMessage + "Principal should be greater than 0\n";
				}
				else {
					m_principal = ValueParamPrincipal;
				}
				float ValueParamRate = Float.parseFloat(ParamRate);
				if (ValueParamRate <= 0 ) {
					m_hasError = true;
					m_strMessage = m_strMessage + "Rate should be greater than 0\n";
				}
				else {
					m_rate = ValueParamRate;
				}
				float ValueParamPeriod = Float.parseFloat(ParamPeriod);
				if (ValueParamPeriod <= 0 ) {
					m_hasError = true;
					m_strMessage = m_strMessage + "Period should be greater than 0\n";
				}
				else {
					m_period = ValueParamPeriod;
				}
				m_grace = ParamGrace;
			}
		}
		
	}
	
	public double computeGraceInterest(String strConfigFixedRate, String strConfigGracePeriod) {
		double ValueGraceInterest =0;
		
		float ValueConfigFixedRate =Float.parseFloat(strConfigFixedRate);
		float ValueConfigGracePeriod = Float.parseFloat(strConfigGracePeriod);
		if (ValueConfigGracePeriod == 0) {
			ValueConfigGracePeriod = 1;
		}
		ValueGraceInterest = m_principal * ((m_rate + ValueConfigFixedRate) / 12) * ValueConfigGracePeriod; 

		return ValueGraceInterest;
		
	}

	
	public double computePayment(double graceInterest, String strConfigGracePeriod) {
		float ValueConfigGracePeriod = Float.parseFloat(strConfigGracePeriod);
		if (ValueConfigGracePeriod == 0) {
			ValueConfigGracePeriod = 1;
		}

		double MonthlyPayment = ((m_rate /12) * (m_principal/(1-Math.pow(1+(m_rate / 12), - m_period))));

		MonthlyPayment += (graceInterest / ValueConfigGracePeriod);

		return MonthlyPayment;
		
	}

	
	//public double computePayment(String p, String a, String i, String g, String gp, String fi) throws Exception
	//public double computeGraceInterest(String p, String gp, String i, String fi) throws Exception
	
}

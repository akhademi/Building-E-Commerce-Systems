package listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements ServletRequestAttributeListener {

    /**
     * Default constructor. 
     */
    public MaxPrincipal() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent srae)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent srae)  { 
         // TODO Auto-generated method stub
    	handleEvent(srae);
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent srae)  { 
         // TODO Auto-generated method stub
    	handleEvent(srae);
    }
	
    
    void handleEvent (ServletRequestAttributeEvent event) {
    	String p = event.getName();
		model.Loan loan = (model.Loan) event.getServletContext().getAttribute("mLoan");

    	if (p.equals("principal")) {
    		//model.Loan loan = (model.Loan) event.getServletContext().getAttribute("mLoan");
    		
    		String strMax = event.getValue().toString();
    		double nMax = Double.parseDouble(strMax);
    		if (nMax > loan.m_maxPrincipal) {
    			loan.m_maxPrincipal = nMax;
    		}
    	}
    }
	
}

package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


import bean.*;
import dao.*;
import model.*;
/**
 * Servlet implementation class Sis
 */
@WebServlet("/Sis")
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    private ServletConfig m_config;
	private ServletContext m_context;
	private SisModel sis;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sis() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String exportJSON(String aName, int aCredit) throws Exception {
    	String strjson = "";
		try {
			
			JsonObjectBuilder doc = Json.createObjectBuilder();
			doc.add("namePrefix", aName).add("credit_taken", Integer.toString(aCredit));
			
			JsonArrayBuilder students=Json.createArrayBuilder();
			
			StudentDAO sd=sis.getStudentDAO();
			Map<String, StudentBean> std = sd.retrieve(aName, aCredit);

			Iterator iterator = std.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry me = (Map.Entry) iterator.next();
				StudentBean sb=(StudentBean)me.getValue();
				students.add(Json.createObjectBuilder().add("name", sb.getName()).
						 add("creditsTaken", sb.getCredit_taken())
						 .add("CreditsToGraduate", sb.getCredit_graduate()));
			}
			doc.add("students", students);
			JsonObject value = doc.build();
			
			System.out.println(value.toString()); // for debugging
			//return json
			strjson = value.toString();
			
		}
		catch (Exception eMain) {
			eMain.printStackTrace();
		}
    	return strjson;
    }
    
    // this function export list of students as XML string
	public String exportXML(String aName, int aCredit) throws Exception {
		
		String strxml = "";
		try {
			ListWrapper lw = new ListWrapper(aName, aCredit);		
			
			JAXBContext jc = JAXBContext.newInstance(lw.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			sw.write("\n");
			
			StudentDAO sd=sis.getStudentDAO();
			Map<String, StudentBean> std = sd.retrieve(aName, aCredit);

			Iterator iterator = std.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry me = (Map.Entry) iterator.next();
				StudentBean sb=(StudentBean)me.getValue();
				lw.getStudentList().add(sb);
			}
			
			marshaller.marshal(lw, new StreamResult(sw));
			System.out.println(sw.toString()); // for debugging
			//return XML
			strxml = sw.toString();
			
		}
		catch (Exception eMain) {
			eMain.printStackTrace();
		}
		return strxml;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			// getting the model
			sis = (SisModel) m_context.getAttribute("SIS");
			
			String prefix = request.getParameter("prefix");
			String strCredit = request.getParameter("credit");
			int credit = Integer.parseInt(strCredit);
			
			String ParamXML = request.getParameter("XML");
			if (ParamXML == null) 
			  ParamXML = "false";	
			
			String ParamJSON = request.getParameter("JSON");
			if (ParamJSON == null) 
			  ParamJSON = "false";

			if (ParamXML.compareTo("XML")== 0) {
				String strResult = exportXML(prefix, credit);
				if (strResult == null) {
					strResult = "No Data Found";
				}
				PrintWriter out = response.getWriter();
				out.printf(strResult);
				out.flush();
			} else if (ParamJSON.compareTo("JSON") == 0) {
				String strResult = exportJSON(prefix, credit);
				if (strResult == null) {
					strResult = "No Data Found";
				}
				PrintWriter out = response.getWriter();
				out.printf(strResult);
				out.flush();
				
			} else { // generating the student output as in Sis-v1 lab
				try {
					String result = "<table border=\"1\"> <thead> <td>Student Name </td> <td>Credits taken </td> <td>Credits to Graduate </td> </thead>";
					
					StudentDAO sd=sis.getStudentDAO();
					Map<String, StudentBean> std = sd.retrieve(prefix, credit);

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
		}
		catch (Exception eMain) {
			eMain.printStackTrace();
		}

		

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
				
		try {
			sis = SisModel.getInstance();
		}
		catch (Exception e) {
			sis = null;
			throw new ServletException("Class Not Found "+ e);
		}
		
		m_context.setAttribute("SIS", sis);

	}
}

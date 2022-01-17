package model;

import java.io.StringWriter;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dao.*;
import bean.*;

public class SisModel {
	// define the static and private field, give it a name
	private static SisModel instance;
	private DataSource ds;

	// these are the pointers to the DAO objects…DAO objects will support all
	// operations on database
	private StudentDAO studentData;
	private EnrollmentDAO enrollmentData;

	// getInstance will return that ONE instance of the pattern
	// with the the DAO objects initialized..
	public static SisModel getInstance() throws ClassNotFoundException {
		try {
			if (instance == null) {
				instance = new SisModel();
				// creating data source
				try {
					instance.ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
				} catch (NamingException e) {
					e.printStackTrace();
				}
				instance.studentData = new StudentDAO(instance.ds);
				instance.enrollmentData = new EnrollmentDAO(instance.ds);
			}
		}
		catch (Exception e) {
			instance = null;			
		}
		return instance;
	}

	public DataSource getDataSource() {
		return instance.ds;
	}
	
	public StudentDAO getStudentDAO() {
		return instance.studentData;
	}
	
	public EnrollmentDAO getEnrollmentDAO() {
		return instance.enrollmentData;
	}
	
	public String getAsXML(String aName) {
		String xmlResult = "";
		
		try {
			StudentListWrapper lw = new StudentListWrapper(aName);		
			
			JAXBContext jc = JAXBContext.newInstance(lw.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			sw.write("\n");
			
			StudentDAO sd=instance.getStudentDAO();
			Map<String, StudentBean> std = sd.retrieveByName(aName);

			Iterator iterator = std.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry me = (Map.Entry) iterator.next();
				StudentBean sb=(StudentBean)me.getValue();
				lw.getStudentList().add(sb);
			}
			
			marshaller.marshal(lw, new StreamResult(sw));
			System.out.println(sw.toString()); // for debugging
			//return XML
			xmlResult = sw.toString();
			
		}
		catch (Exception eMain) {
			eMain.printStackTrace();
		}
		
		return xmlResult;
	}
	
	
	// note that the constructor is private, cannot be called from other classes
	private SisModel() {
		instance = null;
	}
}

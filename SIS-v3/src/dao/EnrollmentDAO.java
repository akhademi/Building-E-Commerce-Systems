package dao;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.EnrollmentBean;;

public class EnrollmentDAO {

	
	private DataSource ds;
	
	public EnrollmentDAO(DataSource ads) throws ClassNotFoundException {
		ds = ads;
	}
	
	public void readAndPrintTableToConsole() throws SQLException {
		try {
			
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ENROLLMENT");
			while(rs.next()) {
				String em= rs.getString("SID");
				String fname = rs.getString("SURNAME");
				System.out.println("\t" + em+ ",\t" + fname+ "\t ");
			}//end while loop
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getEnrollmentForCourse(String cid) throws SQLException {
		String result= "";
		try {
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ENROLLMENT where cid = '" + cid + "'");
			while(rs.next()) {
				String sid= rs.getString("SID");
				int credit = rs.getInt("credit");
				//System.out.println("\t" + em+ ",\t" + fname+ "\t ");
				result = result.concat("sid=" + sid + " credit = " + Integer.toString(credit) + "\n");
			}//end while loop
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public Map<String, EnrollmentBean> retrieve(String namePrefix, int credit_taken) throws SQLException{
		String query = "select * from students where surname like '%" + namePrefix +"%'	and credit_taken >= " + credit_taken;
		Map<String, bean.EnrollmentBean> rv = new HashMap<String, bean.EnrollmentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String cseID = r.getString("SID");
			int acredit_taken = r.getInt("CREDIT_TAKEN");
			int credit_graduate = r.getInt("CREDIT_GRADUATE");
			//rv.put(cseID, new EnrollmentBean(cseID, name, acredit_taken, credit_graduate));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}

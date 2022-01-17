package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.*;

public class StudentDAO {
	private DataSource ds;
	
	public StudentDAO() throws ClassNotFoundException {
		try {
				ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void readAndPrintTableToConsole() throws SQLException {
		try {
			
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS");
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
	
	public Map<String, StudentBean> retrieve(String namePrefix, int credit_taken) throws SQLException{
		String query = "select * from students where surname like '%" + namePrefix +"%'	and credit_taken >= " + credit_taken;
		Map<String, bean.StudentBean> rv = new HashMap<String, bean.StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String cseID = r.getString("SID");
			int acredit_taken = r.getInt("CREDIT_TAKEN");
			int credit_graduate = r.getInt("CREDIT_GRADUATE");
			rv.put(cseID, new StudentBean(cseID, name, acredit_taken, credit_graduate));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}

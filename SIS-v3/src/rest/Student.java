package rest;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.SisModel;

@Path("student") // this is the path of the resource
public class Student {

	@GET
	@Path("/read/")
	@Produces("text/plain")
	public String getStudent(@QueryParam("studentName") String name) throws Exception {
		String result = "";
		result = SisModel.getInstance().getAsXML(name);

		return result;
	}

	// do not copy and paste, type it so you will better remember the
	// patterns
	@POST
	@Path("/create/")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String createStudent(
			@QueryParam("sid") String sid, 
			@QueryParam("givenName") String givenname,
			@QueryParam("surName") String surname, 
			@QueryParam("creditTaken") String credittaken,
			@QueryParam("creditGraduate") String creditgraduate) 
	throws ClassNotFoundException, SQLException, NamingException  {

		String result = "";
		boolean bSuccess = true;
		int nCreditTaken = 0;
		int nCreditGraduate = 0;
		
		try {
			nCreditTaken = Integer.parseInt(credittaken);
		}
		catch (Exception e) {
			bSuccess = false;
			result = result.concat("Invalid value for credit taken : "+ credittaken + "\n");
			nCreditTaken = 0;
		}
		
		try {
			nCreditGraduate = Integer.parseInt(creditgraduate);
		}
		catch (Exception e) {
			bSuccess = false;
			result = result.concat("Invalid value for credit graduate : " + creditgraduate + "\n");
			nCreditGraduate = 0;
		}
		
		if (bSuccess) {
			int ret = SisModel.getInstance().getStudentDAO().insert(sid, givenname, surname, nCreditTaken, nCreditGraduate);
			result = "Insert invoked. code= "+Integer.toString(ret) + "\n";
		}
		return result;

	}

	@DELETE
	@Path("/delete/")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String delete(@QueryParam("sid") String sid) 
			throws ClassNotFoundException, SQLException, NamingException {
		// add the body of the method here..
		// you should call a method from the model,
		// SIS.getInstance().delete(….)
		String result = "";
		boolean bSuccess = true;
		
		if (sid == null || sid.length()== 0) {
			bSuccess = false;
			result = "sid cannot be null";
		}
		
		if (bSuccess) {
			int ret = SisModel.getInstance().getStudentDAO().delete(sid);
			result ="Student [" + sid +"] deleted . retrun code = "+ Integer.toString(ret) + "\n"; 
		}
		return result;

	}
}

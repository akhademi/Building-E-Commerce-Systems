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

@Path("enrollment") // this is the path of the resource
public class Enrollment {

	
	@GET
	@Path("/read/")
	@Produces("text/plain")
	public String getStudent(@QueryParam("cid") String cid) throws Exception {
		String result = "";
		result = SisModel.getInstance().getEnrollmentDAO().getEnrollmentForCourse(cid);

		return result;
	}


}

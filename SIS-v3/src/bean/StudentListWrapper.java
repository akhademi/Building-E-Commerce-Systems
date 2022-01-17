package bean;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="sisReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentListWrapper {
	@XmlAttribute(name="name")
	private String name;

	@XmlElement(name="studentList")
	private List<StudentBean> list;
	
	public StudentListWrapper() {
		list = new ArrayList<StudentBean>();
	}
	
	public StudentListWrapper(String aName) {
		name = aName;
		list = new ArrayList<StudentBean>();
	}
	
	public List<StudentBean> getStudentList() {
		return list;
	}

}

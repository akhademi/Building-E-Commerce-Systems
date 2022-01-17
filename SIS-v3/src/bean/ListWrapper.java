package bean;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sisReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper
	{
	@XmlAttribute(name="namePrefix")
	private String namePrefix;
	@XmlAttribute(name="creditTaken")
	private int credit_taken;
	@XmlElement(name="studentList")
	private List<StudentBean> list;
	
	public ListWrapper() {
		list = new ArrayList<StudentBean>();
	}
	
	public ListWrapper(String aName, int acredit) {
		namePrefix = aName;
		credit_taken = acredit;
		list = new ArrayList<StudentBean>();
	}
	
	public List<StudentBean> getStudentList() {
		return list;
	}

}
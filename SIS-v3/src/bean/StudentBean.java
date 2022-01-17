package bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)

public class StudentBean {
	@XmlElement
	private String sid;
	@XmlElement
	private String name;
	@XmlElement
	private int credit_taken;
	@XmlElement
	private int credit_graduate;
	
	public StudentBean() {
		sid = "";
		name = "";
		credit_taken = 0;
		credit_graduate = 0;
	}
	
	public StudentBean(String aSid, String aName,int an_credit_taken, int an_credit_graduate) {
		sid = aSid;
		name = aName;
		credit_taken = an_credit_taken;
		credit_graduate = an_credit_graduate;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public int getCredit_taken() {
		return credit_taken;
	}

	public void setCredit_taken(int credit_taken) {
		this.credit_taken = credit_taken;
	}

	public int getCredit_graduate() {
		return credit_graduate;
	}

	public void setCredit_graduate(int credit_graduate) {
		this.credit_graduate = credit_graduate;
	}


	
}

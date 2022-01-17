package bean;

public class StudentBean {
	private String sid;
	private String Name;
	private int credit_taken;
	private int credit_graduate;
	
	public StudentBean(String aSid, String aName,int an_credit_taken, int an_credit_graduate) {
		sid = aSid;
		Name = aName;
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
		return Name;
	}

	public void setName(String aName) {
		this.Name = aName;
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

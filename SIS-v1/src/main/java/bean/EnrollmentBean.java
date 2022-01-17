package bean;

public class EnrollmentBean {
	private String CID;
	private String SID;
	private int credit;
	
	public EnrollmentBean(String aCID, String aSID, int aCredit) {
		CID = aCID;
		SID = aSID;
		credit = aCredit;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

}

package org.cl.model;

public class AtUser {

	private String uid;    //被@用户ID
	private String uname;  //被@用户昵称
	private int AtNum;  //被@次数
	private int ReNum;  //被@后回复次数
	
	public String toString(){
		return uid+"\t"+uname+"\t"+AtNum+"\t"+ReNum;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getAtNum() {
		return AtNum;
	}
	public void setAtNum(int atNum) {
		AtNum = atNum;
	}
	public int getReNum() {
		return ReNum;
	}
	public void setReNum(int reNum) {
		ReNum = reNum;
	}
	
	
	
}

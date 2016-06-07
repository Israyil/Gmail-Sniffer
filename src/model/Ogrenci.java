package model;

public class Ogrenci {
	private String group_no;
	private String number;
	private String nameSurname;
	private String note;
	
	public Ogrenci(String group_no, String number, String nameSurname, String note) {
		super();
		this.group_no = group_no;
		this.number = number;
		this.nameSurname = nameSurname;
		this.note = note;
	}

	public Ogrenci(){}
	
	public String getGroup_no() {
		return group_no;
	}
	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNameSurname() {
		return nameSurname;
	}
	public void setNameSurname(String nameSurname) {
		this.nameSurname = nameSurname;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}

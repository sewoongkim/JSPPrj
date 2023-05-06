package com.newlecture.web.entity;

import java.util.Date;

public class Notice {
	private int ID;
	private String title;
	private String writeId;
	private Date regdate;
	private int hit;
	private String files;
	private String context;
	private boolean pub;
	

	public Notice() {
	}
	


	public Notice(int iD, String title, String writeId, Date regdate, int hit, String files, String context, boolean pub) {
		ID = iD;
		this.title = title;
		this.writeId = writeId;
		this.regdate = regdate;
		this.hit = hit;
		this.files = files;
		this.context = context;
		this.pub = pub;
	}



	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getwriteId() {
		return writeId;
	}
	public void setwriteId(String writeId) {
		this.writeId = writeId;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	public String getWriteId() {
		return writeId;
	}

	public void setWriteId(String writeId) {
		this.writeId = writeId;
	}

	public boolean getPub() {
		return pub;
	}

	public void setPub(boolean pub) {
		this.pub = pub;
	}

	@Override
	public String toString() {
		return "Notice [ID=" + ID + ", title=" + title + ", writeId=" + writeId + ", regdate=" + regdate + ", hit="
				+ hit + ", files=" + files + ", context=" + context + ", pub=" + pub + "]";
	}

}

package com.newlecture.web.entity;

import java.util.Date;

public class NoticeView extends Notice {

	private int cmtCount;
	
	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

	public NoticeView() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeView(int ID, String title, String writeID, Date regdate, int hit, String files, String context,
			int cmtCount, boolean pub) {
		super(ID, title, writeID,regdate,hit,files,context,pub);
		this.cmtCount = cmtCount;
	}

}

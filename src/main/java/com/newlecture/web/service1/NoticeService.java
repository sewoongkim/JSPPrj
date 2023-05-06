package com.newlecture.web.service1;

import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList(){
		return getNoticeList("title", "", 1);
	}
	
	public List<Notice> getNoticeList(int page){
		return getNoticeList("title", "", page);
	}
	
	public List<Notice> getNoticeList(String field, String query, int page){

		List<Notice> list = new ArrayList<>();

		String sql = " SELECT * FROM " + 
				" ( " + 
				"		SELECT " + 
				"			ROW_NUMBER() OVER (ORDER BY regdate DESC, ID DESC) ROWNUM, " +
				"			id, title, write_id, context, hit, regdate " +
				"		  FROM NOTICE " +
				"	WHERE  " + field + " LIKE ?  " +
				"	) N " +
				"   LEFT JOIN (SELECT NOTICE_ID, COUNT(*) CNT FROM COMMENT GROUP BY NOTICE_ID) ON NOTICE_ID = ID " +
				"	WHERE ROWNUM BETWEEN ? AND ? " +
				"	ORDER BY ROWNUM ";
		
        String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        
        Connection con = DriverManager.getConnection(url);
 		PreparedStatement st = con.prepareStatement(sql); 
 		st.setString(1,"%" + query + "%");
 		st.setInt(2,1+ (page-1) * 5);
 		st.setInt(3, page * 5);
 		ResultSet  rs = st.executeQuery();

	 		while (rs.next()) {
	 			int ID = rs.getInt("ID");
	 			String title = rs.getString("Title");
	 			String writeID = rs.getString("Write_ID");		
	 			Date regdate = rs.getDate("regdate");
	 			int hit = rs.getInt("hit");
	 			String files = rs.getString("Files");
	 			String context = rs.getString("Context");
	 			boolean pub = rs.getBoolean("pub");
	
	 			Notice notice = new Notice(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				pub
		 				);
				list.add(notice);
	        }
	 		rs.close();
	 		st.close();
	 		con.close();
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}

	public int getNoticeCount(String field, String query) {
		String sql = "SELECT * FROM "
				+ "    (SELECT ROWNUM NUM, N.* "
				+ "      FROM  (SELECT * FROM notice a ORDER BY regdate DESC, ID DESC) N ) "
				+ " WHERE NUM BETWEEN 6 AND 10";
		return 0;
	}
	
	public Notice getNotice(int id) {
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		return null;
	}

	public Notice getNextNotice(int id) {
		return null;
	}

	public Notice getPrevNotice(int id) {
		return null;
	}
}

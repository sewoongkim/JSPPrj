package com.newlecture.web.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
	
public class NoticeService {
	
	public int removeNoticeAll (int[] ids) {
		return 0;
	}
	
	public int pubNoticeAll(String oidsCSV, String cidsCSV) {

		int result = 0;

		String sqlOpen = String.format("update notice set pub = 1 where id in (%s) ",oidsCSV) ; 
				
		String sqlClose = String.format("update notice set pub = 0 where id in (%s) ",cidsCSV) ;

		String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	        Connection con = DriverManager.getConnection(url);
	        
	 		Statement stOpen = con.createStatement();
	 		result += stOpen.executeUpdate(sqlOpen);
	 		stOpen.close();

	 		Statement stClose = con.createStatement();
	 		result += stClose.executeUpdate(sqlClose);
	 		stClose.close();

	 		con.close();
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
		
	}

	public int pubNoticeAll(List<String> oids, List<String> cids) {

		String oidsCSV = String.join(",", oids);
		String cidsCSV =String.join(",", cids);

		return pubNoticeAll(oidsCSV, cidsCSV);

	}
	
	
	public int pubNoticeAll(int[] oids, int[] cids) {

		List<String> oidsList = new ArrayList<>();
		List<String> cidsList = new ArrayList<>();

		for (int i=0; i < oids.length; i++ )
			oidsList.add(String.valueOf(oids[i]));
		
		for (int i=0; i < cids.length; i++ )
			cidsList.add(String.valueOf(cids[i]));

		
		return pubNoticeAll(oidsList, cidsList);
	}

	public int insertNotice(Notice notice) {
		
		int result = 0;
		
		String sql = "INSERT INTO NOTICE (title, context, write_id, pub,files)  VALUES (?, ?, ?, ?,?) ";

		String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	        Connection con = DriverManager.getConnection(url);
	 		PreparedStatement st = con.prepareStatement(sql);

	 		st.setString(1, notice.getTitle());
	 		st.setString(2, notice.getContext());
	 		st.setString(3, notice.getWriteId());
	 		st.setBoolean(4, notice.getPub());
	 		st.setString(5, notice.getFiles());

	 		result = st.executeUpdate();

	 		st.close();
	 		con.close();
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int deleteNotice(int id) {
		return 0;
	}
	
	public int updateNotice(Notice notice) {
		return 0;
	}
	
	public List<Notice> getNoticeNewestList(){
		return null;
	}
	
	public List<NoticeView> getNoticeList(){
		return getNoticeList("title", "", 1);
	}
	
	public List<NoticeView> getNoticeList(int page){
		return getNoticeList("title", "", page);
	}

	public List<NoticeView> getNoticePubList(){
		return getNoticePubList("title", "", 1);
	}
	
	public List<NoticeView> getNoticePubList(int page){
		return getNoticePubList("title", "", page);
	}
	
	
	public List<NoticeView> getNoticeList(String field, String query, int page){

		List<NoticeView> list = new ArrayList<>();

		String sql = " SELECT N.*, ISNULL(B.cnt,0) cmt_count FROM " + 
				" ( " + 
				"		SELECT " + 
				"			ROW_NUMBER() OVER (ORDER BY regdate DESC, ID DESC) ROWNUM, " +
				"			id, title, write_id, context, hit, regdate, files, pub " +
				"		  FROM NOTICE " +
				"	WHERE  " + field + " LIKE ?  " +
				"	) N " +
				"	LEFT JOIN (SELECT NOTICE_ID, COUNT(*) CNT FROM COMMENT GROUP BY NOTICE_ID) B ON N.ID = B.NOTICE_ID " +
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
	 			int cmtCount = rs.getInt("cmt_count");
	 			boolean pub = rs.getBoolean("pub");
	 			
	 			NoticeView notice = new NoticeView(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				cmtCount,
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

	public List<NoticeView> getNoticePubList(String field, String query, int page) {

		List<NoticeView> list = new ArrayList<>();

		String sql = " SELECT N.*, ISNULL(B.cnt,0) cmt_count FROM " + 
				" ( " + 
				"		SELECT " + 
				"			ROW_NUMBER() OVER (ORDER BY regdate DESC, ID DESC) ROWNUM, " +
				"			id, title, write_id, context, hit, regdate, files, pub " +
				"		  FROM NOTICE " +
				"	WHERE  " + field + " LIKE ?  " +
				"	AND  PUB = 1 " +
				"	) N " +
				"	LEFT JOIN (SELECT NOTICE_ID, COUNT(*) CNT FROM COMMENT GROUP BY NOTICE_ID) B ON N.ID = B.NOTICE_ID " +
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
	 			int cmtCount = rs.getInt("cmt_count");
	 			boolean pub = rs.getBoolean("pub");
	 			
	 			NoticeView notice = new NoticeView(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				cmtCount,
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

	public int getNoticeCount(){
		return getNoticeCount("title","");
	}
	
	public int getNoticeCount(String field, String query){

		int count = 0;
		
		String sql = "SELECT 	COUNT(*) CNT FROM NOTICE WHERE " + field + " LIKE ? ";
        String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	        Connection con = DriverManager.getConnection(url);
	 		PreparedStatement st = con.prepareStatement(sql); 
	 		st.setString(1,"%" + query + "%");
	 		ResultSet  rs = st.executeQuery();
	 		if(rs.next())
	 			count = rs.getInt("CNT");
	 		rs.close();
	 		st.close();
	 		con.close();
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Notice getNotice(int id) {
		Notice notice = null;
				
		String sql = "SELECT *  FROM NOTICE WHERE ID=?"; 
        String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        
	        Connection con = DriverManager.getConnection(url);
	 		PreparedStatement st = con.prepareStatement(sql); 
	 		st.setInt(1,id);
	 		ResultSet  rs = st.executeQuery();

	 		if(rs.next()) {
		 		int ID = rs.getInt("ID");
	 			String title = rs.getString("Title");
	 			String writeID = rs.getString("Write_ID");		
	 			Date regdate = rs.getDate("regdate");
	 			int hit = rs.getInt("hit");
	 			String files = rs.getString("Files");
	 			String context = rs.getString("Context");
	 			boolean pub = rs.getBoolean("pub");
		
	 			notice = new Notice(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				pub
		 				);
	        }
	 		rs.close();
	 		st.close();
	 		con.close();
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return notice;
	}

	public Notice getNextNotice(int id) {
		
		Notice notice = null;	
		
		String sql = "SELECT * FROM NOTICE WHERE ID "
				+ " IN (SELECT MIN(ID) AS CNT FROM NOTICE WHERE ID > ? ) "; 
        String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        
	        Connection con = DriverManager.getConnection(url);
	 		PreparedStatement st = con.prepareStatement(sql); 
	 		st.setInt(1,id);
	 		ResultSet  rs = st.executeQuery();

	 		if(rs.next()) {
		 		int ID = rs.getInt("ID");
	 			String title = rs.getString("Title");
	 			String writeID = rs.getString("Write_ID");		
	 			Date regdate = rs.getDate("regdate");
	 			int hit = rs.getInt("hit");
	 			String files = rs.getString("Files");
	 			String context = rs.getString("Context");
	 			boolean pub = rs.getBoolean("pub");
		
	 			notice = new Notice(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				pub
		 				);
	        }
	 		rs.close();
	 		st.close();
	 		con.close();
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return notice;
	}

	public Notice getPrevNotice(int id) {
		Notice notice = null;		

		String sql = "SELECT * FROM NOTICE WHERE ID "
				+ " IN (SELECT MAX(ID) AS CNT FROM NOTICE WHERE ID < ? ) "; 
        String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        
	        Connection con = DriverManager.getConnection(url);
	 		PreparedStatement st = con.prepareStatement(sql); 
	 		st.setInt(1,id);
	 		ResultSet  rs = st.executeQuery();

	 		if(rs.next()) {
		 		int ID = rs.getInt("ID");
	 			String title = rs.getString("Title");
	 			String writeID = rs.getString("Write_ID");		
	 			Date regdate = rs.getDate("regdate");
	 			int hit = rs.getInt("hit");
	 			String files = rs.getString("Files");
	 			String context = rs.getString("Context");
	 			boolean pub = rs.getBoolean("pub");
		
	 			notice = new Notice(
		 				ID,
		 				title,
		 				writeID,
		 				regdate,
		 				hit,
		 				files,
		 				context,
		 				pub
		 				);
	        }
	 		rs.close();
	 		st.close();
	 		con.close();
    	} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return notice;
	}


	public int deleteNoticeAll(int[] ids) {

		int result = 0;
		
		String  params = "";
		
		for(int i=0; i<ids.length; i++) {
			params += ids[i];
			if (i < ids.length-1)
				params += ",";
		}
				
		String sql = "DELETE NOTICE WHERE ID IN (" +params +  ") ";

		String url ="jdbc:sqlserver://127.0.0.1:1433;databaseName=db_mon;user=sa;password=1234;encrypt=false;";

        try {
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	        Connection con = DriverManager.getConnection(url);
	 		Statement st = con.createStatement();
	 		result = st.executeUpdate(sql);
	 		st.close();
	 		con.close();
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

}

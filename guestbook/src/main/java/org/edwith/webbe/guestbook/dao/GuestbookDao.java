package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
	
    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();

		String sql="select id,name,content,regdate from guestbook";
		
		Connection conn=DBUtil.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Long id = rs.getLong("id");
					String name = rs.getString("name");
					String content= rs.getString("content");
					Date regdate=rs.getDate("regdate");
					
					Guestbook gb = new Guestbook(id,name,content,regdate);
					list.add(gb);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

        return list;
    }

    public void addGuestbook(Guestbook guestbook){
    	
		String sql = "insert into guestbook (name,content,regdate) VALUES (?,?,?)";
		
		Connection conn=DBUtil.getConnection();
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, guestbook.getName());
			ps.setString(2, guestbook.getContent());
			
			java.sql.Date sqlDate = new java.sql.Date(guestbook.getRegdate().getTime());
			
			ps.setDate(3, sqlDate);

			ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}

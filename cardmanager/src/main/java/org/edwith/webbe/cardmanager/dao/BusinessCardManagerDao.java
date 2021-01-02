package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessCardManagerDao {
	private static String dburl="jdbc:mysql://localhost:3306/db?useSSL=false";
	private static String dbUser="db";
	private static String dbpasswd="db";
	
	public List<BusinessCard> searchBusinessCard(String keyword){
		List<BusinessCard> list=new ArrayList<>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql="select name,phone,company,create_date from card where name like '%"+keyword+"%'";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String companyName= rs.getString("company");
					Date date=rs.getDate("create_date");
					
					BusinessCard bc = new BusinessCard(name,phone,companyName);
					bc.setCreateDate(date);
					list.add(bc); // list에 반복할때마다 Role인스턴스를 생성하여 list에 추가한다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
    }

    public void addBusinessCard(BusinessCard businessCard){

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "insert into card (name,phone,company,create_date) VALUES (?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, businessCard.getName());
			ps.setString(2, businessCard.getPhone());
			ps.setString(3, businessCard.getCompanyName());
			
			java.sql.Date sqlDate = new java.sql.Date(businessCard.getCreateDate().getTime());
			
			ps.setDate(4, sqlDate);

			ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}

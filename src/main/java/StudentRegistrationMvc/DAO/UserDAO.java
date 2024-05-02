package StudentRegistrationMvc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import StudentRegistrationMvc.DTO.UserDTO;


public class UserDAO {
public static Connection con = null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	//insert
	public int addUser(UserDTO userDTO) {
		int result=0;
		String sql = "Insert into user ( name, email, password, role) values( ?, ?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userDTO.getName());
			ps.setString(2, userDTO.getEmail());
			ps.setString(3, userDTO.getPassword());
			ps.setString(4, userDTO.getRole());
			
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Insert error!!! "+e);
		}
		return result;
	}
	
	public boolean checkLogin(String email,String password) {
		String sql="Select * from user where email=? and password=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				if(email.equals(rs.getString("email")) && password.equals(rs.getString("password"))) {
					return true;
				}else return false;
			}
		} catch (SQLException e) {
			System.out.println("Select  error!!! "+e);
		}
		return false;
	}
	
	public boolean classifiedLogin(String email,String password,String role) {
		String sql="Select * from user where email=? and password=? and role=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, role);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				if(email.equals(rs.getString("email")) && password.equals(rs.getString("password")) && role.equals(rs.getString("role"))) {
					return true;
				}else return false;
			}
		} catch (SQLException e) {
			System.out.println("Select  error!!! "+e);
		}
		return false;
	}
	
	public int getId(String email,String password) {
		int result=0;
		String sql="Select id from user where email=? and password=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				return id;
			}
		} catch (SQLException e) {
			System.out.println("Select Id  error!!! "+e);
		}
		return result;
	}
	
	
	
	//select
			public List<UserDTO> allUser(){
				List<UserDTO> users=new ArrayList<>();
				String sql="SELECT * FROM user where role='user'";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						UserDTO user = new UserDTO();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setRole(rs.getString("role"));
						users.add(user);
					}
					
				}catch(SQLException e) {
					System.out.println("Select all error!!! "+e);
				}
				return users;
			}
			//delete
			public int delete(int id) {
				int result=0;
				String sql="delete from user where id=?";
				try {
					PreparedStatement ps=con.prepareStatement(sql);			
					ps.setInt(1, id);
					result=ps.executeUpdate();						
				}catch(SQLException e) {
					System.out.println("Course delete error"+e);
				}
				return result;
			}
			//update
			public int update(UserDTO userDTO) {
				int result=0;
				String sql="update user set name=?,email=?,password=?,role=? where id=?";
				try {
					PreparedStatement ps=con.prepareStatement(sql);
					ps.setString(1, userDTO.getName());
					ps.setString(2, userDTO.getEmail());
					ps.setString(3, userDTO.getPassword());
					ps.setString(4, userDTO.getRole());
					ps.setInt(5, userDTO.getId());
					result=ps.executeUpdate();
					
				}catch(SQLException e) {
					System.out.println("User update error"+e);
				}
				return result;
			}
			//select by id
			public UserDTO findById(int id) {
				UserDTO user=new UserDTO();
				String sql="select * from user where id=?";
				try {
					PreparedStatement ps=con.prepareStatement(sql);
					ps.setInt(1, id);
					
					
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						user.setEmail(rs.getString("email"));
						user.setPassword(rs.getString("password"));
						user.setRole(rs.getString("role"));
					}			
				}catch(SQLException e) {
					System.out.println(" User find by id error"+e);
				}
				return user;
			}
			
			
			
			public boolean isEmailExists(String email) {
				 boolean emailExists = false;
				 String sql = "SELECT COUNT(*) FROM user WHERE email=?";
				 try {
					 PreparedStatement ps = con.prepareStatement(sql);
					 ps.setString(1, email);
					 ResultSet rs = ps.executeQuery();
					 while(rs.next()) {
						 int count=rs.getInt(1);
						 emailExists=(count>0);
					 }
					} catch (SQLException e) {
						 System.out.println("Error checking email existence: " + e);
					}
				 return emailExists;
			}
			
//selectById
			
			public UserDTO getUserById(int id) {
				UserDTO user = new UserDTO();
				String sql = "SELECT * FROM user WHERE id=?";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, id);
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));	
					}
				} catch (SQLException e) {
					System.out.println("select user by id error" + e);
				}
				return user;
			}
			
			

}

package StudentRegistrationMvc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import StudentRegistrationMvc.DTO.CourseDTO;



public class CourseDAO {
	public static Connection con = null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	//insert
		public int addCourse(CourseDTO courseDTO) {
			int result=0;
			String sql = "Insert into course ( name,isDelete,user_id ) values( ?,?,? )";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, courseDTO.getName());
				ps.setInt(2, 0);
				ps.setInt(3, courseDTO.getUser_id());
				result = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("Insert error!!! "+e);
			}
			return result;
		}
		
		//select
		public List<CourseDTO> allCourse(){
			List<CourseDTO> courses=new ArrayList<>();
			String sql="SELECT c.*, u.name as user_name FROM course c INNER JOIN user u ON u.id = c.user_id where isDelete=0";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					CourseDTO course = new CourseDTO();
					course.setId(rs.getInt("id"));
					course.setName(rs.getString("name"));
					course.setUser_name(rs.getString("user_name"));
					courses.add(course);
				}
				
			}catch(SQLException e) {
				System.out.println("Select all error!!! "+e);
			}
			return courses;
		}
		
		//selectById
		public List<CourseDTO> getCourseById(String student_id) {
			List<CourseDTO> courses = new ArrayList<CourseDTO>();			
			String sql = "SELECT c.* FROM course c INNER JOIN  course_has_student sc  ON  c.id=sc.course_id WHERE sc.student_id=?";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, student_id);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					CourseDTO course = new CourseDTO();
					course.setId(rs.getInt("id"));
					course.setName(rs.getString("name"));
					//System.out.println("Course Name:"+rs.getString("name"));
					courses.add(course);
				}
			} catch (SQLException e) {
				System.out.println("select student by id error" + e);
			}
			return courses;
		}
		
		//delete
		public int delete(int id) {
			int result=0;
			String sql="UPDATE course SET isDelete=? where id=?";
			try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setInt(1, 1);
				
				ps.setInt(2, id);
				result=ps.executeUpdate();						
			}catch(SQLException e) {
				System.out.println("Course delete error"+e);
			}
			return result;
		}
		
		public boolean isCourseExists(String name) {
			 boolean courseExists = false;
			 String sql = "SELECT COUNT(*) FROM course WHERE name=? AND isDelete=0";
			 try {
				 PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, name);
				 //ps.setInt(2,0);
				 ResultSet rs = ps.executeQuery();
				 while(rs.next()) {
					 int count=rs.getInt(1);
					 courseExists=(count>0);
				 }
				} catch (SQLException e) {
					 System.out.println("Error checking course existence: " + e);
				}
			 return courseExists;
		}
		
		

}

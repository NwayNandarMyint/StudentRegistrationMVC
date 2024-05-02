package StudentRegistrationMvc.DAO;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import org.apache.tomcat.util.codec.binary.Base64;

import StudentRegistrationMvc.DTO.*;

public class StudentDAO {
	
public static Connection con = null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int getLastStudentId() {
	       int lastUserId = 0;
	       String sql = "SELECT MAX(id) as id FROM student";
	          
	        try {
	           PreparedStatement ps=con.prepareStatement(sql);      
	           ResultSet rs=ps.executeQuery();  
	            while(rs.next()) {
	              lastUserId = rs.getInt("id"); 
	            } 
	            
	        }catch(SQLException e) {
	           System.out.println("select Last Id error: "+e);
	        }
	          return lastUserId;
	    }
	
	//insert
		public int addStudent(StudentRequestDTO dto) {
			int result=0;
			String sql = "Insert into student ( code,name, dob, gender, phone,education,photo,isDelete) values( ?, ?, ?, ?,?,?,?,?)";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, dto.getCode());
				ps.setString(2, dto.getName());
				ps.setString(3, dto.getDob());
				ps.setString(4, dto.getGender());
				ps.setString(5, dto.getPhone());
				ps.setString(6, dto.getEducation());
				
				Blob blob =new javax.sql.rowset.serial.SerialBlob(dto.getPhoto());
			      ps.setBlob(7, blob);
			      ps.setInt(8, 0);

				result = ps.executeUpdate();
				if(result!=0) {
					for(String course:dto.getCourses()) {
						sql="INSERT INTO course_has_student(student_id,course_id) VALUES(LAST_INSERT_ID(),?)";
						ps=con.prepareStatement(sql);
						//ps.setInt(1, dto.getId());
						
						int id=Integer.parseInt(course);
						ps.setInt(1, id);
						result=ps.executeUpdate();
				
					}	
				}

			} catch (SQLException e) {
				System.out.println("Insert error!!! "+e);
			}
			return result;
		}
		//delete
				public int delete(int id) {
					int result=0;
					String sql="UPDATE student SET isDelete=? where id=?";
					try {
						PreparedStatement ps=con.prepareStatement(sql);
						ps.setInt(1, 1);
						
						ps.setInt(2, id);
						result=ps.executeUpdate();						
					}catch(SQLException e) {
						System.out.println("Student delete error"+e);
					}
					return result;
				}
	   
	  //delete
		public int deleteStudent(int id) {
			int result=0;
			String sql="DELETE FROM course_has_student WHERE student_id = ?";
			try {
				PreparedStatement ps=con.prepareStatement(sql);			
				ps.setInt(1, id);
				System.out.println("Id:"+id);
				result=ps.executeUpdate();
				
				 
				  sql="delete from student where id=?";
				 ps=con.prepareStatement(sql);			
				ps.setInt(1,id);
				System.out.println("Student Id:"+id);
				result=ps.executeUpdate();
				
			}catch(SQLException e) {
				System.out.println("Student delete error:"+e);
				
			}
			return result;
		}
	  
		 //selectbyId
	    public StudentResponseDTO getStudentByID(int id) {
	      StudentResponseDTO student=new StudentResponseDTO();
	      String sql="SELECT * FROM student where id=?";
	      
	      try {
	        PreparedStatement ps=con.prepareStatement(sql); 
	        ps.setInt(1, id);
	        ResultSet rs=ps.executeQuery();      
	        while(rs.next()) {
	        	
	        	student.setId(rs.getInt("id"));
	        	student.setCode(rs.getString("code"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				Blob blob = (Blob) rs.getBlob("photo");
	            byte [] bytes  =  blob.getBytes(1,(int) blob.length());
	            byte[] encodeBase64 = Base64.encodeBase64(bytes);
	            try {
	            	student.setPhoto(new String(encodeBase64, "UTF-8"));
	              } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	              }	  
				
				sql="SELECT c.* FROM course_has_student sc inner join course c on sc.course_id=c.id WHERE sc.student_id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet join_rs=ps.executeQuery();
				List<CourseDTO> courses=new ArrayList<CourseDTO>();
				while(join_rs.next()) {
					CourseDTO course=new CourseDTO();
					course.setId(join_rs.getInt("id"));
					course.setName(join_rs.getString("name"));
					courses.add(course);
				}
				
				student.setCourses(courses);
	        }        
	      }catch(SQLException e) {
	        System.out.println("select all error: "+e);
	      }
	      return student;
	    }
	    
	    //selectAllStudents
	    public List<StudentResponseDTO> getStudents() {
	      List<StudentResponseDTO> students=new ArrayList<StudentResponseDTO>();
	      String sql="SELECT * FROM student where isDelete=0";
	      CourseDAO couseDAO=new CourseDAO();
	      try {
	        PreparedStatement ps=con.prepareStatement(sql);      
	        ResultSet rs=ps.executeQuery();      
	        while(rs.next()) {
	        	StudentResponseDTO student=new StudentResponseDTO();
	        	student.setId(rs.getInt("id"));
	        	student.setCode(rs.getString("code"));
				student.setName(rs.getString("name"));								
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				student.setCourses(couseDAO.getCourseById(rs.getString("id")));
				Blob blob = (Blob) rs.getBlob("photo");
	            byte [] bytes  =  blob.getBytes(1,(int) blob.length());
	            byte[] encodeBase64 = Base64.encodeBase64(bytes);
	            try {
	            	student.setPhoto(new String(encodeBase64, "UTF-8"));
	              } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	              }
			
				students.add(student);
	        }        
	      }catch(SQLException e) {
	        System.out.println("select all error: "+e);
	      }
	      return students;
	    }
	    
	  //update
		public int updateStudent(StudentRequestDTO studentDTO) {
			int result=0;
			String sql="";
			//System.out.println("Photo:"+studentDTO.getPhoto());
			if(studentDTO.getPhoto().length != 0)
			 sql="UPDATE student SET name=?,dob=?,gender=?,education=?,phone=?,photo=?,code=? WHERE id=?";
			else
				sql="UPDATE student SET name=?,dob=?,gender=?,education=?,phone=?,code=? WHERE id=?";
			try {
				PreparedStatement ps=con.prepareStatement(sql);
				
				ps.setString(1, studentDTO.getName());
				ps.setString(2, studentDTO.getDob());
				ps.setString(3, studentDTO.getGender());
				ps.setString(4, studentDTO.getEducation());
				ps.setString(5, studentDTO.getPhone());
				if(studentDTO.getPhoto().length != 0) {
					Blob blob = new javax.sql.rowset.serial.SerialBlob(studentDTO.getPhoto());
					ps.setBlob(6, blob);
					ps.setString(7, studentDTO.getCode());
			        ps.setInt(8, studentDTO.getId());
			       // System.out.println("ID:"+studentDTO.getId());
				}else {
					ps.setString(6, studentDTO.getCode());
					ps.setInt(7, studentDTO.getId());
					
				}
				result=ps.executeUpdate();
				
				sql="DELETE FROM course_has_student WHERE student_id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, studentDTO.getId());
				result=ps.executeUpdate();
				
				for(String course_id:studentDTO.getCourses()) {
					sql="INSERT INTO course_has_student(student_id,course_id) VALUES(?,?)";
					ps=con.prepareStatement(sql);
					ps.setInt(1, studentDTO.getId());
					int id=Integer.parseInt(course_id);
					ps.setInt(2, id);
					
					result=ps.executeUpdate();
				}						
			}catch(SQLException e) {
				System.out.println("Student Update error: "+e);
			}		
			return result;
		}
		
		public boolean isIdExists(String id) {
			 boolean idExists = false;
			 String sql = "SELECT COUNT(*) FROM student WHERE id=?";
			 try {
				 PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, id);
				 //ps.setInt(2,0);
				 ResultSet rs = ps.executeQuery();
				 while(rs.next()) {
					 int count=rs.getInt(1);
					 idExists=(count>0);
				 }
				} catch (SQLException e) {
					 System.out.println("Error checking id existence: " + e);
				}
			 return idExists;
		}
		
		//selectAllStudents
	    public List<StudentResponseDTO> getOldStudents() {
	      List<StudentResponseDTO> students=new ArrayList<StudentResponseDTO>();
	      String sql="SELECT * FROM student where isDelete=1";
	      CourseDAO couseDAO=new CourseDAO();
	      try {
	        PreparedStatement ps=con.prepareStatement(sql);      
	        ResultSet rs=ps.executeQuery();      
	        while(rs.next()) {
	        	StudentResponseDTO student=new StudentResponseDTO();
	        	student.setId(rs.getInt("id"));
	        	student.setCode(rs.getString("code"));
				student.setName(rs.getString("name"));								
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				student.setCourses(couseDAO.getCourseById(rs.getString("id")));
				
				Blob blob = (Blob) rs.getBlob("photo");
	            byte [] bytes  =  blob.getBytes(1,(int) blob.length());
	            byte[] encodeBase64 = Base64.encodeBase64(bytes);
	            try {
	            	student.setPhoto(new String(encodeBase64, "UTF-8"));
	              } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	              }
			
				students.add(student);
	        }        
	      }catch(SQLException e) {
	        System.out.println("select all error: "+e);
	      }
	      return students;
	    }
		
	  //oldAdd
		public int oldAdd(int id) {
			int result=0;
			String sql="UPDATE student SET isDelete=? where id=?";
			try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setInt(1, 0);
				
				ps.setInt(2, id);
				result=ps.executeUpdate();						
			}catch(SQLException e) {
				System.out.println("Student delete error"+e);
			}
			return result;
		}
		
		//seeStudents
	    public List<StudentResponseDTO> getCourseByStudents(int id) {
	      List<StudentResponseDTO> students=new ArrayList<StudentResponseDTO>();
	      String sql="SELECT s.* FROM course_has_student cs inner join student s on s.id=cs.student_id where isDelete=0 and course_id=?";
	      CourseDAO couseDAO=new CourseDAO();
	      try {
	        PreparedStatement ps=con.prepareStatement(sql); 
	        ps.setInt(1, id);
	        ResultSet rs=ps.executeQuery();      
	        while(rs.next()) {
	        	StudentResponseDTO student=new StudentResponseDTO();
	        	student.setId(rs.getInt("id"));
	        	student.setCode(rs.getString("code"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setEducation(rs.getString("education"));
				student.setPhone(rs.getString("phone"));
				student.setCourses(couseDAO.getCourseById(rs.getString("id")));
				
				Blob blob = (Blob) rs.getBlob("photo");
	            byte [] bytes  =  blob.getBytes(1,(int) blob.length());
	            byte[] encodeBase64 = Base64.encodeBase64(bytes);
	            try {
	            	student.setPhoto(new String(encodeBase64, "UTF-8"));
	              } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	              }
			
				students.add(student);
	        }        
	      }catch(SQLException e) {
	        System.out.println("select all error: "+e);
	      }
	      return students;
	    }
		
	    
}

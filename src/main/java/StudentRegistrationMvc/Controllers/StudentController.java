package StudentRegistrationMvc.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import StudentRegistrationMvc.DAO.CourseDAO;
import StudentRegistrationMvc.DAO.StudentDAO;
import StudentRegistrationMvc.DAO.UserDAO;
import StudentRegistrationMvc.DTO.CourseDTO;
import StudentRegistrationMvc.DTO.StudentRequestDTO;
import StudentRegistrationMvc.DTO.StudentResponseDTO;
import StudentRegistrationMvc.DTO.UserDTO;
import StudentRegistrationMvc.Models.StudentBean;
import StudentRegistrationMvc.Persistant.IdGenerator;

@Controller
@RequestMapping("student")
public class StudentController {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CourseDAO courseDAO;
	@Autowired
	StudentDAO studentDAO;
	
	@ModelAttribute("courses")
	  List<CourseDTO> getAllCourses() {
	    List<CourseDTO> courses = courseDAO.allCourse();  
	    return courses;
	  }
	
	@GetMapping("/")
	public Object addStudent( ModelMap model,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			UserDTO dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			
			model.addAttribute("user", user);
			
			int lastid=studentDAO.getLastStudentId();
			 String generatecode=IdGenerator.generateStudentId(lastid);
			 model.addAttribute("generatedcode",generatecode);
			
//			List<CourseDTO> courses=courseDAO.allCourse();
//			model.addAttribute("courses", courses);
			
			return new ModelAndView("createStudent","student",new StudentBean());
			
		}
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/addStudent",method=RequestMethod.POST)
	public String addCourse(@ModelAttribute("student")StudentBean student,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			List<CourseDTO> courses=courseDAO.allCourse();
			
			List<String> course=student.getCourses();
			String code=student.getCode();
			String name=student.getName();
			String dob=student.getDob();
			String gender=student.getGender();
			String phone=student.getPhone();
			String education=student.getEducation();
			
			if (code.equals("") && name .equals("")  && dob .equals("") &&  gender .equals("") && phone.equals("") && education.equals("") && course.isEmpty()) {
				model.addAttribute("error", "Mail and password are required!!");
				model.addAttribute("courses", courses);
				return "createStudent";
	           
	        }
	        if(name.equals("") || name.isEmpty()) {
	        	model.addAttribute("error", "Please Enter your name!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	        }
	        if(dob.equals("") || dob.isEmpty()) {
	        	model.addAttribute("error", "Please Enter your Birthday!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	        }
	            LocalDate birthdayDate = LocalDate.parse(dob);
	            
	            // Check if the birthday is in the future
	            if (birthdayDate.isAfter(LocalDate.now())) {
	            	model.addAttribute("error", "Date of birth cannot be in the future!!");
	            	model.addAttribute("courses", courses);
					return "createStudent";
	                
	            }
	            
	            if(gender==null || gender.isEmpty()) {
		        	model.addAttribute("error", "Please Select your gender!!");
		        	model.addAttribute("courses", courses);
					return "createStudent";
		        	 
		        }
	                 
	        if(phone.equals("") || phone.isEmpty()) {
	        	model.addAttribute("error", "Please Enter your Pbone Nunber!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	        	 
	        }

	        if (!isValidPhoneNumber(phone)) {
	        	model.addAttribute("error", "Please enter a valid 11-digit number.!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	           
	        }
	        if(education.equals("") || education.isEmpty()) {
	        	model.addAttribute("error", "Please Enter your Education!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	        }
	        if(course ==null || course.isEmpty()) {
	        	model.addAttribute("error", "Course is  required!!");
	        	model.addAttribute("courses", courses);
				return "createStudent";
	 
	        }
			
			StudentRequestDTO studentDTO=new StudentRequestDTO();
			studentDTO.setCode(code);
			studentDTO.setName(name);
			studentDTO.setDob(dob);
			studentDTO.setGender(gender);
			studentDTO.setPhone(phone);
			studentDTO.setEducation(education);
			try {
				studentDTO.setPhoto(student.getPhoto().getBytes());
			} catch (IOException e) {
				 e.printStackTrace();
			}
			studentDTO.setCourses(course);
			
				
			int rs=studentDAO.addStudent(studentDTO);
				if(rs==0) {
					model.addAttribute("error","Insert Fail(SQL Error)");
					return  "redirect:/student/";
					
				}
				model.addAttribute("result", rs);
				return  "createStudent";
				
		}
		
		return "redirect:/student/";
	}
	

	@GetMapping("/displayStudent")
	public String displayStudent( ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
		
		List<StudentResponseDTO> students=studentDAO.getStudents();
		model.addAttribute("sutdents", students);
		return "displayStudents";
		
		}
			
		return "redirect:/student/displayStudent";
		
	}
	@GetMapping("/displayOldStudent")
	public String displayOldStudent( ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
		
		List<StudentResponseDTO> students=studentDAO.getOldStudents();
		model.addAttribute("sutdents", students);
		return "displayOldStudent";
		
		}
			
		return "redirect:/student/displayStudent";
		
	}
	@GetMapping("/viewStudent/{id}")
	public String viewStudent(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
		
		StudentResponseDTO sutdent=studentDAO.getStudentByID(id);
		model.addAttribute("sutdent", sutdent);
		return "viewStudent";
		
		}
			
		return "redirect:/viewStudent/";
		
	}
	
	@GetMapping("/updateStudent/{id}")
	public Object updateStudent(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			StudentResponseDTO student=studentDAO.getStudentByID(id);
			model.addAttribute("studentinform", student);
			
//			List<CourseDTO> courses=courseDAO.allCourse();
//			model.addAttribute("courses", courses);
			
			return new ModelAndView("updateStudent","student",new StudentBean());
		
		}
			
		return "redirect:/";
		
	}
	
	@PostMapping("/updateStudent")
	public String updateUser(@ModelAttribute("student") StudentBean student,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			List<String> course=student.getCourses();
			for(String c:course)System.out.println("Course ID:"+c);
			StudentRequestDTO studentDTO=new StudentRequestDTO();
			studentDTO.setId(student.getId());
			studentDTO.setCode(student.getCode());
			studentDTO.setName(student.getName());
			studentDTO.setDob(student.getDob());
			studentDTO.setGender(student.getGender());
			studentDTO.setPhone(student.getPhone());
			studentDTO.setEducation(student.getEducation());
			
			try {
				studentDTO.setPhoto(student.getPhoto().getBytes());
			} catch (IOException e) {
				 e.printStackTrace();
			}
			studentDTO.setCourses(course);
			int rs=studentDAO.updateStudent(studentDTO);
			if(rs==0) {
				model.addAttribute("error","Update Fail(SQL Error)");
				return  "updateStudent";
				
			}
			//model.addAttribute("result", rs);
			//model.addAttribute("studentinform", studentDTO);
			return  "redirect:/student/displayStudent";
			
		}
		return "redirect:/student/updateStudent";
		
	}
	
	 private boolean isValidPhoneNumber(String phone) {
	        // Check if the phone number is a number and has exactly 11 digits
	        return phone.matches("\\d{11}");

	    }
	 
	 @GetMapping("/deleteStudent/{id}")
		public String deleteStudent(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
			HttpSession session=request.getSession();
			if(session !=null && session.getAttribute("id") !=null) {
				int id1=(Integer)session.getAttribute("id");
				var dto=userDAO.findById(id1);
				UserDTO user=new UserDTO();
				user.setName(dto.getName());
				user.setRole(dto.getRole());
				model.addAttribute("user", user);
				
				StudentDAO student=new StudentDAO();
				student.delete(id);
				//student.deleteStudent(id);
			
				List<CourseDTO> courses=courseDAO.allCourse();
				model.addAttribute("courses", courses);
				return "redirect:/student/displayStudent";
			
			}
				
			return "redirect:/student/";
			
		}
	 
	 @GetMapping("/studentAdd/{id}")
		public String studentAdd(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
			HttpSession session=request.getSession();
			if(session !=null && session.getAttribute("id") !=null) {
				int id1=(Integer)session.getAttribute("id");
				var dto=userDAO.findById(id1);
				UserDTO user=new UserDTO();
				user.setName(dto.getName());
				user.setRole(dto.getRole());
				model.addAttribute("user", user);
				
				StudentDAO student=new StudentDAO();
				student.oldAdd(id);
				//student.deleteStudent(id);
			
				List<CourseDTO> courses=courseDAO.allCourse();
				model.addAttribute("courses", courses);
				return "redirect:/student/displayOldStudent";
			
			}
				
			return "redirect:/student/";
			
		}
	 
	 @GetMapping("/seeStudents/{id}")
		public String seeStudents(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
			HttpSession session=request.getSession();
			if(session !=null && session.getAttribute("id") !=null) {
				int id1=(Integer)session.getAttribute("id");
				var dto=userDAO.findById(id1);
				UserDTO user=new UserDTO();
				user.setName(dto.getName());
				user.setRole(dto.getRole());
				model.addAttribute("user", user);
				
				List<StudentResponseDTO> students=studentDAO.getCourseByStudents(id);
				model.addAttribute("sutdents", students);
				return "seeStudents";
				
			}
				
			return "redirect:/student/";
			
		}
	 
	 


}

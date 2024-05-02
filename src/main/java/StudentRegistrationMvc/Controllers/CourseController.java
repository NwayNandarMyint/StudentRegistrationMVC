package StudentRegistrationMvc.Controllers;

import java.io.IOException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import StudentRegistrationMvc.DAO.CourseDAO;
import StudentRegistrationMvc.DAO.UserDAO;
import StudentRegistrationMvc.DTO.CourseDTO;
import StudentRegistrationMvc.DTO.UserDTO;


@Controller
@RequestMapping("course")
public class CourseController {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CourseDAO courseDAO;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Object addCourse( ModelMap model,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			UserDTO dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			
			model.addAttribute("user", user);
			return new ModelAndView("addCourse","course",new CourseDTO());
			
		}
		return "redirect:/";
		
	}
	
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute("course") CourseDTO course,HttpServletRequest request,RedirectAttributes redirectAttributes,ModelMap model) {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			
			if(course.getName().equals("")) {
				model.addAttribute("user", user);
				model.addAttribute("error", "Course is required!!");
				 return  "addCourse";
			}
			else if(courseDAO.isCourseExists(course.getName())) {
				model.addAttribute("user", user);
				model.addAttribute("error", "Course already exists!!");
				return  "addCourse";
			}
			else {
				CourseDTO courseDTO=new CourseDTO();
				courseDTO.setName(course.getName());
				courseDTO.setUser_id(id);
				
				int rs=courseDAO.addCourse(courseDTO);
				if(rs==0) {
					model.addAttribute("error","Insert Fail(SQL Error)");
					return  "redirect:/course/";
					//request.getRequestDispatcher("addcourse.jsp").forward(request, response);
				}
				model.addAttribute("result", rs);
				return  "addCourse";
				}
		}
		
		return "redirect:/course/";
	}
	
	@GetMapping("/displayCourse")
	public String displayCourse( ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
		
		List<CourseDTO> courses=courseDAO.allCourse();
		model.addAttribute("courses", courses);
		return "displayCourse";
		
		}
			
		return "redirect:/";
		
	}
	
	@GetMapping("/deleteCourse/{id}")
	public String deleteUser(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			CourseDAO course=new CourseDAO();
			course.delete(id);
		
			List<CourseDTO> courses=courseDAO.allCourse();
			model.addAttribute("courses", courses);
			
			return "redirect:/course/displayCourse";
		
		}
			
		return "redirect:/course/";
		
	}
	
	


}

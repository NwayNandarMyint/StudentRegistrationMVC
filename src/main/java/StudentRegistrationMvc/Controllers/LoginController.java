package StudentRegistrationMvc.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import StudentRegistrationMvc.DAO.UserDAO;
import StudentRegistrationMvc.DTO.UserDTO;

@Controller

public class LoginController {
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	 public ModelAndView login( ModelMap model) throws IOException {
		return new ModelAndView("login","log",new UserDTO());
	}
	
	@RequestMapping(value="/login" ,method=RequestMethod.POST)
	public String loginChecking(UserDTO user,ModelMap model, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if(user.getEmail().equals("") && user.getPassword().equals("")){
			redirectAttributes.addFlashAttribute("error", "Mail and password are required!!");
			return "redirect:/";
		}
		else if(user.getEmail().equals("")) {
			redirectAttributes.addFlashAttribute("error", "Mail is required!!");
			return "redirect:/";
		}
		else if(user.getPassword().equals("")) {
			redirectAttributes.addFlashAttribute("error", "Password is required!!");
			return "redirect:/";
		}
		else{
			boolean condition=userDAO.checkLogin(user.getEmail(),user.getPassword());
		
		if(condition==true) {
			String admin="admin";
			String u="user";
			if(userDAO.classifiedLogin(user.getEmail(),user.getPassword(),admin)) {
				int id=userDAO.getId(user.getEmail(), user.getPassword());
				HttpSession session=request.getSession();
				session.setAttribute("id", id);
				return "redirect:/course/";
				}
			else if(userDAO.classifiedLogin(user.getEmail(), user.getPassword(),u)) {
				int id1=userDAO.getId(user.getEmail(), user.getPassword());
				HttpSession session=request.getSession();
				session.setAttribute("id", id1);
				return "redirect:/student/";
				}
			}
		else {
			redirectAttributes.addFlashAttribute("error", "Your Account does not exist!!");
			return "redirect:/";
		}
		
		}
		return "redirect:/"; 
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout( ModelMap model,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

}

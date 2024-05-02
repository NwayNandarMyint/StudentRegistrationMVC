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
import StudentRegistrationMvc.DAO.UserDAO;
import StudentRegistrationMvc.DTO.UserDTO;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public Object addUser( ModelMap model,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			UserDTO dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			
			model.addAttribute("user", user);
			
			return new ModelAndView("addUser","userDTO",new UserDTO());
			
		}
		return "redirect:/";
		
	}
	
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("user") UserDTO user1,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			 String name = user1.getName();
		     String email = user1.getEmail();
		     String password = user1.getPassword();
		     model.addAttribute("name", name);
		     model.addAttribute("email", email);
		     model.addAttribute("password", password);
		     
		     
		     if (name .equals("")  && email .equals("") &&  password .equals("")) {
		    	 model.addAttribute("user", user);
		    	 model.addAttribute("error", "All fields are required!!");
					 return  "addUser";   
		        }
		        if(name.equals("") || name.isEmpty()) {
		        	 model.addAttribute("user", user);
			    	 model.addAttribute("error", "Please Enter Your Name!!");
						 return  "addUser";   
			            
		        }
		        if(email.equals("") || email.isEmpty()) {
		        	model.addAttribute("user", user);
			    	 model.addAttribute("error", "Please Enter Your Email!!");
						 return  "addUser";       
		        }

		        if (!isValidEmail(email)) {
		        	model.addAttribute("user", user);
			    	 model.addAttribute("error", "Invalid email format!! Please enter a valid Gmail address!!");
						 return  "addUser";
		            
		        }

		        if (userDAO.isEmailExists(email)) {
		        	model.addAttribute("user", user);
			    	 model.addAttribute("error", "Email already exists!!");
						 return  "addUser";
		        	
		           
		        }
		        if(password.equals("") || password.isEmpty()) {
		        	model.addAttribute("user", user);
			    	 model.addAttribute("error", "Please Enter Your Password!!");
					return  "addUser";
			            
		        }

		        if (!isValidPassword(password)) {
		        	model.addAttribute("user", user);
			    	 model.addAttribute("error", "Password must be 6 characters long!!");
						 return  "addUser";
		        	
		            
		        }
			
			UserDTO userDTO=new UserDTO();
			userDTO.setName(name);
			userDTO.setEmail(email);
			userDTO.setPassword(password);
			
			userDTO.setRole(user1.getRole());
			int rs=userDAO.addUser(userDTO);
			if(rs==0) {
				model.addAttribute("user", user);
				model.addAttribute("error","Insert Fail(SQL Error)");
				return  "redirect:/user/";
				
			}
			model.addAttribute("result", rs);
			return  "addUser";
			
		}
		return "redirect:/user/";
		
	}
	
	@GetMapping("/displayUser")
	public String displayUser( ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
		
		List<UserDTO> users=userDAO.allUser();
		model.addAttribute("users", users);
		return "displayUser";
		
		}
			
		return "redirect:/";
		
	}
	
	@GetMapping("/updateUser/{id}")
	public Object updateUser(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			UserDTO user1=userDAO.findById(id);
			model.addAttribute("user1", user1);
		
			List<UserDTO> users=userDAO.allUser();
			model.addAttribute("users", users);
			return new ModelAndView("updateUser","userDTO",new UserDTO());
		
		}
			
		return "redirect:/";
		
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("user") UserDTO user1,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			
			UserDTO userDTO=new UserDTO();
			userDTO.setId(user1.getId());
			//System.out.println("ID:"+user1.getId());
			userDTO.setName(user1.getName());
			//System.out.println("Name:"+user1.getName());
			userDTO.setEmail(user1.getEmail());
			//System.out.println("Email:"+user1.getEmail());
			userDTO.setPassword(user1.getPassword());
			//System.out.println("Password:"+user1.getPassword());
			userDTO.setRole(user1.getRole());
			//System.out.println("Role:"+user1.getRole());
			int rs=userDAO.update(userDTO);
			if(rs==0) {
				model.addAttribute("user", user);
				model.addAttribute("error","Update Fail(SQL Error)");
				return  "updateUser";
				
			}
			model.addAttribute("result", rs);
			model.addAttribute("user", user);
			return  "updateUser";
			
		}
		return "redirect:/user/";
		
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable int id, ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			model.addAttribute("user", user);
			
			UserDAO user1=new UserDAO();
			user1.delete(id);
		
			List<UserDTO> users=userDAO.allUser();
			model.addAttribute("users", users);
			return "redirect:/user/displayUser";
		
		}
			
		return "redirect:/user/";
		
	}
	
	@GetMapping("/profile")
	public String viewStudent( ModelMap model,HttpServletRequest request) throws IOException{
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("id") !=null) {
			int id1=(Integer)session.getAttribute("id");
			var dto=userDAO.findById(id1);
			UserDTO user=new UserDTO();
			user.setId(dto.getId());
			user.setName(dto.getName());
			user.setRole(dto.getRole());
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());
			model.addAttribute("user", user);

		return "profile";
		
		}
			
		return "redirect:/viewStudent/";
		
	}
	
	private boolean isValidEmail(String email) {
	      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
	      return email.matches(emailRegex);
	  }
	private boolean isValidPassword(String password) {
		if(password.length()!=6) {
			return false;
		}
		return true;
	}

}

package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.exceptions.DuplicatedNameException;
import com.spring.exceptions.InvalidNameException;
import com.spring.model.Language;
import com.spring.model.User;
import com.spring.service.ILanguageService;
import com.spring.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ILanguageService languageService;
	
	// ------------------------------ Views ------------------------------------------
	
	//GET
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {

		try {
			
			List<User> users = userService.findAllUsers();
			List<Language> languages = languageService.findAllLanguages();
			
			model.addAttribute("users", users);
			model.addAttribute("languages", languages);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("findAllException", "Can't find the users");
			
		}
		
		return "user-index";
		
	}
	
	//GET
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@RequestParam("id") Long id, Model model) {
		
		try {
			
			List<Language> languages = languageService.findAllLanguages();
			User user = userService.findUserById(id);
			
			model.addAttribute("languages", languages);
			model.addAttribute("user", user);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("findByIdException", "Unexpeced error. try again later.");
		}
		
		return "user-update";
	}
	
	// --------------------------------- Process -----------------------------------
	
	//POST
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView createNewUser(@ModelAttribute User commingUser, RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/users");
		
		try {
			String languageName = commingUser.getLanguage().getName();
			
			Language language = languageService.findLanguageByName(languageName);
			
			commingUser.setLanguage(language);
			
			userService.createNewUser(commingUser);
			
		} catch(DuplicatedNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "there is already a user with that name");
			return modelAndView;
			
		} catch(InvalidNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "Invalid username");
			return modelAndView;
			
		} catch(Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "Can't create the user");
			
		}
		
		return modelAndView;
		
	}
	
	//PATCH - POST
	@RequestMapping(value="/update/{oldUsername}", method=RequestMethod.POST)
	public ModelAndView updateUser(
						@PathVariable("oldUsername")String oldUsername, 
						@ModelAttribute User commingUser, 
						RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/users");
		
		try {
			
			String languageName = commingUser.getLanguage().getName();
			
			Language language = languageService.findLanguageByName(languageName);
			
			commingUser.setLanguage(language);
			
			System.out.println(commingUser.getUsername());
			System.out.println(commingUser.getId());
			
			userService.updateUser(oldUsername, commingUser);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("updateException", "Can't update the user");
			modelAndView.setViewName("redirect:http://localhost:8080/users/update?id=" + commingUser.getId());
		} 
		
		return modelAndView;
	}
	
	//DELETE - POST
	@RequestMapping(value="/deleteUserById/{id}", method=RequestMethod.POST)
	public ModelAndView deleteUserById(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/users");
		
		try {
			
			userService.deleteUser(id);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("deleteId", id);
			redirect.addFlashAttribute("deleteException", "Can't delete that user");
			
		}
		
		return modelAndView;
		
	}

}

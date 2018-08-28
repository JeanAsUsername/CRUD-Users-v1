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
import com.spring.exceptions.LanguageInUseException;
import com.spring.model.Language;
import com.spring.service.ILanguageService;
import com.spring.service.IUserService;

@Controller
@RequestMapping("/languages")
public class LanguageController {
	
	@Autowired
	ILanguageService languageService;
	@Autowired
	IUserService userService;
	
	// ------------------------------ Views ------------------------------------------
	
	// GET
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		
		try {
			List<Language> languages = languageService.findAllLanguages();
			
			model.addAttribute("languages", languages);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("findAllException", "Can't find the languages");
		}
		
		return "index";

	}
	
	// GET
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@RequestParam("id") Long id, Model model) {
		
		try {
			
			Language language = languageService.findLanguageById(id);
			
			model.addAttribute("language", language);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("findByIdException", "Unexpeced error. try again later.");
		} 
		
		return "update";
		
	}
	
	// --------------------------------- Process -----------------------------------
	
	// POST
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView createLanguage(@ModelAttribute Language commingLanguage, RedirectAttributes redirect) {
	    
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/languages");
		
		try {
			
			languageService.createLanguage(commingLanguage);
			
		} catch (DuplicatedNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "That language already exists");
			return modelAndView;
			
		} catch(InvalidNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "Invalid language name");
			return modelAndView;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("createException", "Can't create the language");
		}
		
		return modelAndView;
	}
	
	// DELETE - POST
	@RequestMapping(value="/deleteLanguageById/{id}", method=RequestMethod.POST)
	public ModelAndView deleteLanguage(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/languages");
		
		try {
			languageService.deleteLanguage(id);
			
		} catch(LanguageInUseException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("deleteId", id);
			redirect.addFlashAttribute("deleteException", "This language is being used for an user");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("deleteId", id);
			redirect.addFlashAttribute("deleteException", "Can't delete the language");
		}
		
		return modelAndView;
	}
	
	
	// PATCH - POST
	@RequestMapping(value="/update/{oldName}", method=RequestMethod.POST)
	public ModelAndView updateLanguage(@PathVariable("oldName") String oldName, @ModelAttribute Language commingLanguage, RedirectAttributes redirect) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/languages");
		
		try {
			System.out.println(commingLanguage.getId());
			languageService.updateLanguage(oldName, commingLanguage);
			
		} catch(InvalidNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("updateException", "Invalid name");
			modelAndView.setViewName("redirect:http://localhost:8080/languages/update?id=" + commingLanguage.getId());
			
			
		}catch (DuplicatedNameException e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("updateException", "That language already exists");
			modelAndView.setViewName("redirect:http://localhost:8080/languages/update?id=" + commingLanguage.getId());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			redirect.addFlashAttribute("updateException", "Can't update the language");
			modelAndView.setViewName("redirect:http://localhost:8080/languages/update?id=" + commingLanguage.getId());
		}
		
		return modelAndView;
	}

}

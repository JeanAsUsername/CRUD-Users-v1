package com.spring.dao;

import java.util.List;

import com.spring.model.Language;

public interface ILanguageDao {
	
	public List<Language> findAllLanguages() throws Exception;
	
	public Language findLanguageById(Long id) throws Exception;
	
	public Language findLanguageByName(String name) throws Exception;
	
	public Language createLanguage(Language commingLanguage) throws Exception;
	
	public Language deleteLanguage(Long id) throws Exception;
	
	public Language updateLanguage(String oldName, Language commingLanguage) throws Exception;

}

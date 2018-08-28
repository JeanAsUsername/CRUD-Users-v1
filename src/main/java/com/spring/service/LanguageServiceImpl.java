package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.ILanguageDao;
import com.spring.exceptions.InvalidNameException;
import com.spring.model.Language;

import java.util.List;

import javax.transaction.Transactional;

@Service("LanguageService")
@Transactional
public class LanguageServiceImpl implements ILanguageService {
	
	@Autowired
	private ILanguageDao languageDao;

	@Override
	public List<Language> findAllLanguages() throws Exception {
		return languageDao.findAllLanguages();
	}

	@Override
	public Language findLanguageById(Long id) throws Exception {
		return languageDao.findLanguageById(id);
	}

	@Override
	public Language findLanguageByName(String name) throws Exception {
		return languageDao.findLanguageByName(name);
	}

	@Override
	public Language createLanguage(Language commingLanguage) throws Exception {
		
		String languageName = commingLanguage.getName();
		
		if(!languageName.matches("[a-zA-Z]+") || languageName.length() > 25) {
			throw new InvalidNameException();
		}
		
		return languageDao.createLanguage(commingLanguage);
	}

	@Override
	public Language deleteLanguage(Long id) throws Exception {
		return languageDao.deleteLanguage(id);
	}
	
	@Override
	public Language updateLanguage(String oldName, Language commingLanguage) throws Exception {
		
		String languageName = commingLanguage.getName();
		
		if(!languageName.matches("[a-zA-Z+#.]+") || languageName.length() > 30) {
			throw new InvalidNameException();
		}
		
		return languageDao.updateLanguage(oldName, commingLanguage);
	}
	
}

package com.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.exceptions.DuplicatedNameException;
import com.spring.exceptions.LanguageInUseException;
import com.spring.model.Language;
import com.spring.model.User;
import com.spring.service.IUserService;

@Repository
public class LanguageDaoImpl implements ILanguageDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private IUserService userService;

	@Override
	public List<Language> findAllLanguages() throws Exception {

			String hql = "from Language";
			Query query = entityManager.createQuery(hql);
			
			// fetch the languages
			@SuppressWarnings("unchecked")
			List<Language> languages = query.getResultList();
			
			return languages;
			
	}

	@Override
	public Language findLanguageById(Long id) throws Exception {
		
			
			String hql = "from Language where id=:id";
			Query query = entityManager.createQuery(hql).setParameter("id", id);
			
			Language language = (Language) query.getSingleResult();
			
			return language;
			
		
	}

	@Override
	public Language findLanguageByName(String name) throws Exception {
		
		try {
			
			String hql = "from Language where name=:name";
			Query query = entityManager.createQuery(hql).setParameter("name", name);
			
			Language language = (Language) query.getSingleResult();
			
			return language;

			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception();
		}
		
	}

	@Override
	public Language createLanguage(Language commingLanguage) throws Exception {
			
		List<Language> languages = this.findAllLanguages();
			
		for (Language language:languages) {
				 // if there is a language with the same name
			if (language.getName().equalsIgnoreCase(commingLanguage.getName())) {
				throw new DuplicatedNameException();
			}
		}
			
		entityManager.persist(commingLanguage);
			
		return commingLanguage;
		
	}

	@Override
	public Language deleteLanguage(Long id) throws Exception {
			
		Language languageToDelete = this.findLanguageById(id);
		List<User> users = userService.findAllUsers();
		
		for (User user:users) {
			
			// if the language is being used for an user, thrown an Exception.
			if (user.getLanguage().getName().equalsIgnoreCase(languageToDelete.getName())) {
				throw new LanguageInUseException();
			}
		}
			
		String hql = "delete from Language where id= :id";
		Query query = entityManager.createQuery(hql).setParameter("id", id);
			
		query.executeUpdate();
			
		return languageToDelete;

	}
	
	@Override
	public Language updateLanguage(String oldName, Language commingLanguage) throws Exception {
			
		List<Language> languages = this.findAllLanguages();
		Language oldLanguage = this.findLanguageByName(oldName);
		
		for (Language language:languages) {
				 // if there is a language with the same name
			if (language.getName().equalsIgnoreCase(commingLanguage.getName()) 
				&& 
				!commingLanguage.getName().equalsIgnoreCase(oldName)) {
					
					throw new DuplicatedNameException();
					
				} else if (commingLanguage.getId() != oldLanguage.getId()) {
					
					throw new Exception();
					
				}
		}
			
		String hql = "update Language set name=:name where id=:id";
		Query query = entityManager.createQuery(hql)
						.setParameter("name", commingLanguage.getName())
						.setParameter("id", commingLanguage.getId());
			
		query.executeUpdate();
			
		Language updatedLanguage = this.findLanguageById(commingLanguage.getId());
			
		return updatedLanguage;	
		
	}
	
}

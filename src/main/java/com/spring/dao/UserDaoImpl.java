package com.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.spring.exceptions.DuplicatedNameException;
import com.spring.model.User;

@Repository
public class UserDaoImpl implements IUserDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> findAllUsers() throws Exception {
		
		String hql = "from User";
		
		@SuppressWarnings("unchecked")
		List<User> users = entityManager.createQuery(hql).getResultList();

		return users;
	}

	@Override
	public User findUserById(Long id) {
		
		String hql = "from User where id=:id";
		Query query = entityManager.createQuery(hql).setParameter("id", id);
		
		User user = (User) query.getSingleResult();
		
		return user;
		
	}
	
	@Override
	public User findUserByUsername(String username) throws Exception {
		
		String hql = "from User where username=:username";
		Query query = entityManager.createQuery(hql).setParameter("username", username);
		
		User user = (User) query.getSingleResult();
		
		return user;
	}

	@Override
	public User createNewUser(User commingUser) throws Exception {
		
		List<User> users = this.findAllUsers();
		
		for (User user:users) {
			if (commingUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				throw new DuplicatedNameException();
			}
		}
		
		entityManager.persist(commingUser);
		
		return commingUser;
	}

	@Override
	public User updateUser(String oldUsername, User commingUser) throws Exception {
		
		User oldUser = this.findUserByUsername(oldUsername);
		
		
		if (!commingUser.getUsername().equalsIgnoreCase(oldUsername) || commingUser.getId() != oldUser.getId()) {
			throw new Exception();
		}
		
		String hql = "update User set language=:language where id=:id";
		Query query = entityManager.createQuery(hql)
							.setParameter("language", commingUser.getLanguage())
							.setParameter("id", commingUser.getId());
		
		query.executeUpdate();
		
		User updatedUser = this.findUserById(commingUser.getId());
		
		return updatedUser;
	}

	@Override
	public User deleteUser(Long id) throws Exception {
		
		User userToDelete = this.findUserById(id);
		
		String hql = "delete from User where id=:id";
		Query query = entityManager.createQuery(hql).setParameter("id", id);
		
		query.executeUpdate();
		
		return userToDelete;
	}
	
}

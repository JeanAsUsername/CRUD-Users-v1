package com.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.IUserDao;
import com.spring.exceptions.InvalidNameException;
import com.spring.model.User;

@Transactional
@Service("UserService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<User> findAllUsers() throws Exception {
		return userDao.findAllUsers();
	}

	@Override
	public User findUserById(Long id) throws Exception {
		return userDao.findUserById(id);
	}
	
	@Override
	public User findUserByUsername(String username) throws Exception {
		return userDao.findUserByUsername(username);
	}

	@Override
	public User createNewUser(User commingUser) throws Exception {
		
		String username = commingUser.getUsername();
		
		if(!username.matches("[a-zA-Z+#.]+") || username.length() > 30) {
			throw new InvalidNameException();
		}
		
		return userDao.createNewUser(commingUser);
	}

	@Override
	public User updateUser(String oldUsername, User commingUser) throws Exception {
		return userDao.updateUser(oldUsername, commingUser);
	}

	@Override
	public User deleteUser(Long id) throws Exception {
		return userDao.deleteUser(id);
	}

}

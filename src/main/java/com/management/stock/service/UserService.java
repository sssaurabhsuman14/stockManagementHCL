package com.management.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.management.stock.model.UserModel;

@Service
public interface UserService {
	
	public UserModel addUser(UserModel model);
	public UserModel getUser(Long id);
	public List<UserModel> getAllUser(Long id);
	public UserModel updateUser(UserModel model);
	public UserModel deleteUser(UserModel model);

}

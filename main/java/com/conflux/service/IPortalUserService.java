package com.conflux.service;

import java.util.List;

import com.conflux.service.dto.UserT;

public interface IPortalUserService {

	public UserT addUser(UserT userT);

	public void updateUser(UserT userT);

	public void deleteUser(UserT userT);

	public UserT getUserById(int id);

	public List<UserT> getUsers();

	public UserT getUserByUsername(String username);

	public boolean existsUserByUsername(String username);
}

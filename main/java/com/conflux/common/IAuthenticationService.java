package com.conflux.common;

import javax.annotation.security.RolesAllowed;

public interface IAuthenticationService {
	
	boolean login(String username, String password);

	@RolesAllowed({"ROLE_ADMIN","ROLE_REGISTERED"})
	void logout();
}

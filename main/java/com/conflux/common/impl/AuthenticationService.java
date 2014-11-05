package com.conflux.common.impl;

import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.conflux.common.IAuthenticationService;

@Service("authenticationService")
public class AuthenticationService implements IAuthenticationService {

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager;

	@Override
	public boolean login(String username, String password) {
		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							username, password));
			if (authenticate.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(
						authenticate);
				return true;
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		// currentUser.unauthenticate();
	}

}

package com.conflux.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.conflux.common.ApplicationConstants;
import com.conflux.service.IPortalUserService;
import com.conflux.service.dto.UserT;

/*
 * Spring-security requires an implementation of UserDetailService. 
 */
@Service("userDetailsService")
public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	IPortalUserService userService;

	/*
	 * Mock for users from database. In the real application users will be
	 * retrieved from database and proper Spring UserDetails object will be
	 * created then for each database user.
	 */
	private HashMap<String, org.springframework.security.core.userdetails.User> users = new HashMap<String, org.springframework.security.core.userdetails.User>();

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		/**
		 * users list is updated after each login, hence has to be refreshed for
		 * every authentication attempt
		 **/

		init();

		org.springframework.security.core.userdetails.User user = users
				.get(username);

		if (user == null) {
			throw new UsernameNotFoundException("UserAccount for name \""
					+ username + "\" not found.");
		}

		return user;
	}

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init() {

		// sample roles
		Collection<GrantedAuthority> adminAuthorities = new ArrayList<GrantedAuthority>();
		adminAuthorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

		Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
		userAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));

		Collection<GrantedAuthority> auditorAuthorities = new ArrayList<GrantedAuthority>();
		auditorAuthorities.add(new GrantedAuthorityImpl("ROLE_AUDITOR"));

		Collection<GrantedAuthority> juniorAuditorAuthorities = new ArrayList<GrantedAuthority>();
		juniorAuditorAuthorities.add(new GrantedAuthorityImpl("ROLE_JUNIOR_AUDITOR"));

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		// get a list of all users
		List<UserT> usersInDatabase = userService.getUsers();

		for (UserT userT : usersInDatabase) {
			if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.ADMIN
					.getRoleId()) {
				users.put(
						userT.getUsername(),
						new org.springframework.security.core.userdetails.User(
								userT.getUsername(), userT.getPassword(), userT
										.isActive(), accountNonExpired,
								credentialsNonExpired, accountNonLocked,
								adminAuthorities));
			} else if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.AUDITOR
					.getRoleId()) {
				users.put(
						userT.getUsername(),
						new org.springframework.security.core.userdetails.User(
								userT.getUsername(), userT.getPassword(), userT
										.isActive(), accountNonExpired,
								credentialsNonExpired, accountNonLocked,
								auditorAuthorities));
			} else if (userT.getUserRoleT().getRoleId() == ApplicationConstants.USER_ROLE.JUNIOR_AUDITOR
					.getRoleId()) {
				users.put(
						userT.getUsername(),
						new org.springframework.security.core.userdetails.User(
								userT.getUsername(), userT.getPassword(), userT
										.isActive(), accountNonExpired,
								credentialsNonExpired, accountNonLocked,
								juniorAuditorAuthorities));
			} else {
				users.put(
						userT.getUsername(),
						new org.springframework.security.core.userdetails.User(
								userT.getUsername(), userT.getPassword(), userT
										.isActive(), accountNonExpired,
								credentialsNonExpired, accountNonLocked,
								userAuthorities));
			}
		}
	}
}

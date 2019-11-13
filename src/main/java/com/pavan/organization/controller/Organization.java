package com.pavan.organization.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.organization.model.OrganizationModel;
import com.pavan.organization.model.UserModel;
import com.pavan.organization.repository.AddressRepository;
import com.pavan.organization.repository.OrganizationRepository;
import com.pavan.organization.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class Organization {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@ApiOperation(value = "creates an organization")
	@PostMapping("/orgs")
	public OrganizationModel createOrg(@Valid @RequestBody OrganizationModel org) {
		return organizationRepository.save(org);
	}

	@ApiOperation(value = "creates an user")
	@PostMapping("/users")
	public UserModel createUser(@Valid @RequestBody UserModel user) {
		user.getAddress().stream().forEach(address -> address.setUser(user));
		userRepository.save(user);
		return user;
	}

	@ApiOperation(value = "add org to an user")
	@PostMapping("/orgs/{orgId}/users/{userId}")
	public @ResponseBody String mapUserToOrg(@PathVariable int userId, @PathVariable int orgId){
		Optional<UserModel> usermodel = userRepository.findById(userId);
		Optional<OrganizationModel> orgmodel = organizationRepository.findById(orgId);
		if (orgmodel.isPresent() && usermodel.isPresent()) {
			OrganizationModel orgnizationobj = orgmodel.get();
			orgnizationobj.getUsers().add(usermodel.get());
			organizationRepository.save(orgnizationobj);
		}
		return "success";

	}

	@ApiOperation(value = "detele org from an user")
	@DeleteMapping("/orgs/{orgId}/users/{userId}/deleteorg")
	public void deleteUserFromOrg(@PathVariable int orgId, @PathVariable int userId) {

		Optional<OrganizationModel> org = organizationRepository.findById(orgId);
		if (org.isPresent()) {
			OrganizationModel orgModel = org.get();
			Optional<UserModel> user = orgModel.getUsers().stream().filter(users -> users.getUserID() == userId)
					.findFirst();
			if (user.isPresent()) {
				orgModel.getUsers().remove(user.get());
				organizationRepository.save(orgModel);
			}
		}
	}

	@ApiOperation(value = "Read all Users who belong to a specific Organization")
	@GetMapping("/orgs/{orgId}/users")
	public Set<UserModel> getUsersFromOrg(@PathVariable int orgId) throws NotFoundException {

		Optional<OrganizationModel> org = organizationRepository.findById(orgId);
		if (org.isPresent()) {
			return org.get().getUsers();
		} else {
			throw new NotFoundException();
		}

	}

	@ApiOperation(value = "Read all Organizations to which a User belongs")
	@GetMapping("/users/{userId}/orgs")
	public Set<OrganizationModel> getOrgFroUser(@PathVariable int userId){

		Optional<UserModel> userobj = userRepository.findById(userId);
		if (userobj.isPresent()) {
			return userobj.get().getOrg();
		}
		return new HashSet<>();
	}

}

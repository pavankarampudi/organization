package com.pavan.organization.controller;

import java.util.HashSet;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.organization.model.OrganizationModel;
import com.pavan.organization.model.UserModel;
import com.pavan.organization.repository.AddressRepository;
import com.pavan.organization.repository.OrganizationRepository;
import com.pavan.organization.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/org")
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
	public UserModel createUser(@Valid @RequestBody UserModel user) throws NotFoundException {

		UserModel userobj = userRepository.save(user);
		return userobj;
	}

	@ApiOperation(value = "add org to an user")
	@GetMapping("/orgs/{orgId}/users/{userId}")
	public UserModel updateOrg(@PathVariable int userId, @PathVariable int orgId) throws NotFoundException {
		UserModel userModel = null;
		Optional<UserModel> userobj = userRepository.findById(userId);
		if (userobj.isPresent()) {
			UserModel userModelObj = userModel = userobj.get();
			organizationRepository.findById(orgId).map(org -> {
				userModelObj.setUserID(userId);
				Set<OrganizationModel> orgset = new HashSet<>();
				orgset.add(org);
				userModelObj.setOrg(orgset);
				return userRepository.save(userModelObj);
			}).orElseThrow(() -> new NotFoundException());
		} else {
			throw new NotFoundException();
		}
		return userModel;

	}

	@ApiOperation(value = "detele org from an user")
	@DeleteMapping("/orgs/{orgId}/users/{userId}/deleteorg")
	public void deleteOrg(@PathVariable int orgId, @PathVariable int userId) throws NotFoundException {
		
		Optional<OrganizationModel> org = organizationRepository.findById(orgId);
		Optional<UserModel> user = userRepository.findById(userId);
		if (org.isPresent()) {
		OrganizationModel orgmodel = org.get();
		orgmodel.getUsers().remove(user.get());
		
		}
		//orgUserMappingRepo.deleteByOrgIdAndUserId(orgId, userId);
	}

	@ApiOperation(value = "get all user belong to an organization")
	@GetMapping("/orgs/{orgId}/users")
	public List<UserModel> getUsers(@PathVariable int orgId) throws NotFoundException {

		Optional<OrganizationModel> org = organizationRepository.findById(orgId);
		if (org.isPresent()) {
			return userRepository.findByOrg(org.get());
		} else {
			throw new NotFoundException();
		}

	}

	@ApiOperation(value = "creates an user")
	@PostMapping("/orgs/{orgId}/users")
	public UserModel createUser(@Valid @RequestBody UserModel user, @PathVariable int orgId) throws NotFoundException {

		UserModel userobj = organizationRepository.findById(orgId).map(org -> {
			return userRepository.save(user);
		}).orElseThrow(() -> new NotFoundException());
		return userobj;
	}

}

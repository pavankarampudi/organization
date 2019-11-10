package com.pavan.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.organization.model.OrganizationModel;
import com.pavan.organization.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{
	
	List<UserModel> findByOrg(OrganizationModel org);

}

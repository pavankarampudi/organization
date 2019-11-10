package com.pavan.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.organization.model.AddressModel;
import com.pavan.organization.model.OrganizationModel;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationModel, Integer>{

}

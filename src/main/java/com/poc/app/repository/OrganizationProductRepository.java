package com.poc.app.repository;

import com.poc.app.domain.OrganizationProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the OrganizationProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationProductRepository extends JpaRepository<OrganizationProduct, Long> {

    List<OrganizationProduct> findByOrganizationIdAndStatusTrue(Long organization_id);

}

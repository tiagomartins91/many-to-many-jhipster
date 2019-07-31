package com.poc.app.service.mapper;

import com.poc.app.domain.*;
import com.poc.app.service.dto.OrganizationProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationProduct} and its DTO {@link OrganizationProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, ProductMapper.class})
public interface OrganizationProductMapper extends EntityMapper<OrganizationProductDTO, OrganizationProduct> {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "product.id", target = "productId")
    OrganizationProductDTO toDto(OrganizationProduct organizationProduct);

    @Mapping(source = "organizationId", target = "organization")
    @Mapping(source = "productId", target = "product")
    OrganizationProduct toEntity(OrganizationProductDTO organizationProductDTO);

    default OrganizationProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganizationProduct organizationProduct = new OrganizationProduct();
        organizationProduct.setId(id);
        return organizationProduct;
    }
}

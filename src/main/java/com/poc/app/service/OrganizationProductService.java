package com.poc.app.service;

import com.poc.app.domain.OrganizationProduct;
import com.poc.app.repository.OrganizationProductRepository;
import com.poc.app.repository.ProductRepository;
import com.poc.app.service.dto.OrganizationProductDTO;
import com.poc.app.service.dto.ProductDTO;
import com.poc.app.service.mapper.OrganizationProductMapper;
import com.poc.app.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrganizationProduct}.
 */
@Service
@Transactional
public class OrganizationProductService {

    private final Logger log = LoggerFactory.getLogger(OrganizationProductService.class);

    private final OrganizationProductRepository organizationProductRepository;

    private final OrganizationProductMapper organizationProductMapper;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public OrganizationProductService(OrganizationProductRepository organizationProductRepository, OrganizationProductMapper organizationProductMapper, ProductRepository productRepository, ProductMapper productMapper) {
        this.organizationProductRepository = organizationProductRepository;
        this.organizationProductMapper = organizationProductMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a organizationProduct.
     *
     * @param organizationProductDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganizationProductDTO save(OrganizationProductDTO organizationProductDTO) {
        log.debug("Request to save OrganizationProduct : {}", organizationProductDTO);
        OrganizationProduct organizationProduct = organizationProductMapper.toEntity(organizationProductDTO);
        organizationProduct = organizationProductRepository.save(organizationProduct);
        return organizationProductMapper.toDto(organizationProduct);
    }

    /**
     * Get all the organizationProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganizationProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationProducts");
        return organizationProductRepository.findAll(pageable)
            .map(organizationProductMapper::toDto);
    }


    /**
     * Get one organizationProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganizationProductDTO> findOne(Long id) {
        log.debug("Request to get OrganizationProduct : {}", id);
        return organizationProductRepository.findById(id)
            .map(organizationProductMapper::toDto);
    }

    /**
     * Delete the organizationProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganizationProduct : {}", id);
        organizationProductRepository.deleteById(id);
    }


    public List<ProductDTO> getActiveProductsByOrganizationId(Long organizationId) {
        List<OrganizationProductDTO> organizationProductDTOS = organizationProductMapper.toDto(organizationProductRepository.findByOrganizationIdAndStatusTrue(organizationId));
        List<ProductDTO> productDTOList = new ArrayList<>();

        organizationProductDTOS.forEach(each ->{

            Optional<ProductDTO> productDTOOptional = productRepository.findById(each.getId()).map(productMapper::toDto);

            if(productDTOOptional.isPresent()){
                productDTOList.add(productDTOOptional.get());
            }
        });

        return productDTOList;
    }
}

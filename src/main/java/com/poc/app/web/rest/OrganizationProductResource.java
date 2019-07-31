package com.poc.app.web.rest;

import com.poc.app.service.OrganizationProductService;
import com.poc.app.service.dto.ProductDTO;
import com.poc.app.web.rest.errors.BadRequestAlertException;
import com.poc.app.service.dto.OrganizationProductDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.poc.app.domain.OrganizationProduct}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationProductResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationProductResource.class);

    private static final String ENTITY_NAME = "organizationProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationProductService organizationProductService;

    public OrganizationProductResource(OrganizationProductService organizationProductService) {
        this.organizationProductService = organizationProductService;
    }

    /**
     * {@code POST  /organization-products} : Create a new organizationProduct.
     *
     * @param organizationProductDTO the organizationProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationProductDTO, or with status {@code 400 (Bad Request)} if the organizationProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-products")
    public ResponseEntity<OrganizationProductDTO> createOrganizationProduct(@RequestBody OrganizationProductDTO organizationProductDTO) throws URISyntaxException {
        log.debug("REST request to save OrganizationProduct : {}", organizationProductDTO);
        if (organizationProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationProductDTO result = organizationProductService.save(organizationProductDTO);
        return ResponseEntity.created(new URI("/api/organization-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-products} : Updates an existing organizationProduct.
     *
     * @param organizationProductDTO the organizationProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationProductDTO,
     * or with status {@code 400 (Bad Request)} if the organizationProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-products")
    public ResponseEntity<OrganizationProductDTO> updateOrganizationProduct(@RequestBody OrganizationProductDTO organizationProductDTO) throws URISyntaxException {
        log.debug("REST request to update OrganizationProduct : {}", organizationProductDTO);
        if (organizationProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationProductDTO result = organizationProductService.save(organizationProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organization-products} : get all the organizationProducts.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationProducts in body.
     */
    @GetMapping("/organization-products")
    public ResponseEntity<List<OrganizationProductDTO>> getAllOrganizationProducts(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of OrganizationProducts");
        Page<OrganizationProductDTO> page = organizationProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-products/:id} : get the "id" organizationProduct.
     *
     * @param id the id of the organizationProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-products/{id}")
    public ResponseEntity<OrganizationProductDTO> getOrganizationProduct(@PathVariable Long id) {
        log.debug("REST request to get OrganizationProduct : {}", id);
        Optional<OrganizationProductDTO> organizationProductDTO = organizationProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationProductDTO);
    }

    /**
     * {@code DELETE  /organization-products/:id} : delete the "id" organizationProduct.
     *
     * @param id the id of the organizationProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-products/{id}")
    public ResponseEntity<Void> deleteOrganizationProduct(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationProduct : {}", id);
        organizationProductService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/organization-products/custom/{id}")
    public ResponseEntity<List<ProductDTO>> getOrganizationProductByOrganizationId(@PathVariable Long id) {
        List<ProductDTO> productDTOList = organizationProductService.getActiveProductsByOrganizationId(id);
        return ResponseEntity.ok().body(productDTOList);
    }
}

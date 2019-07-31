package com.poc.app.web.rest;

import com.poc.app.AppTestApp;
import com.poc.app.domain.OrganizationProduct;
import com.poc.app.repository.OrganizationProductRepository;
import com.poc.app.service.OrganizationProductService;
import com.poc.app.service.dto.OrganizationProductDTO;
import com.poc.app.service.mapper.OrganizationProductMapper;
import com.poc.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.poc.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OrganizationProductResource} REST controller.
 */
@SpringBootTest(classes = AppTestApp.class)
public class OrganizationProductResourceIT {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private OrganizationProductRepository organizationProductRepository;

    @Autowired
    private OrganizationProductMapper organizationProductMapper;

    @Autowired
    private OrganizationProductService organizationProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrganizationProductMockMvc;

    private OrganizationProduct organizationProduct;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationProductResource organizationProductResource = new OrganizationProductResource(organizationProductService);
        this.restOrganizationProductMockMvc = MockMvcBuilders.standaloneSetup(organizationProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationProduct createEntity(EntityManager em) {
        OrganizationProduct organizationProduct = new OrganizationProduct()
            .status(DEFAULT_STATUS);
        return organizationProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationProduct createUpdatedEntity(EntityManager em) {
        OrganizationProduct organizationProduct = new OrganizationProduct()
            .status(UPDATED_STATUS);
        return organizationProduct;
    }

    @BeforeEach
    public void initTest() {
        organizationProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizationProduct() throws Exception {
        int databaseSizeBeforeCreate = organizationProductRepository.findAll().size();

        // Create the OrganizationProduct
        OrganizationProductDTO organizationProductDTO = organizationProductMapper.toDto(organizationProduct);
        restOrganizationProductMockMvc.perform(post("/api/organization-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProductDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganizationProduct in the database
        List<OrganizationProduct> organizationProductList = organizationProductRepository.findAll();
        assertThat(organizationProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationProduct testOrganizationProduct = organizationProductList.get(organizationProductList.size() - 1);
        assertThat(testOrganizationProduct.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOrganizationProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationProductRepository.findAll().size();

        // Create the OrganizationProduct with an existing ID
        organizationProduct.setId(1L);
        OrganizationProductDTO organizationProductDTO = organizationProductMapper.toDto(organizationProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationProductMockMvc.perform(post("/api/organization-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationProduct in the database
        List<OrganizationProduct> organizationProductList = organizationProductRepository.findAll();
        assertThat(organizationProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganizationProducts() throws Exception {
        // Initialize the database
        organizationProductRepository.saveAndFlush(organizationProduct);

        // Get all the organizationProductList
        restOrganizationProductMockMvc.perform(get("/api/organization-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrganizationProduct() throws Exception {
        // Initialize the database
        organizationProductRepository.saveAndFlush(organizationProduct);

        // Get the organizationProduct
        restOrganizationProductMockMvc.perform(get("/api/organization-products/{id}", organizationProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizationProduct.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizationProduct() throws Exception {
        // Get the organizationProduct
        restOrganizationProductMockMvc.perform(get("/api/organization-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationProduct() throws Exception {
        // Initialize the database
        organizationProductRepository.saveAndFlush(organizationProduct);

        int databaseSizeBeforeUpdate = organizationProductRepository.findAll().size();

        // Update the organizationProduct
        OrganizationProduct updatedOrganizationProduct = organizationProductRepository.findById(organizationProduct.getId()).get();
        // Disconnect from session so that the updates on updatedOrganizationProduct are not directly saved in db
        em.detach(updatedOrganizationProduct);
        updatedOrganizationProduct
            .status(UPDATED_STATUS);
        OrganizationProductDTO organizationProductDTO = organizationProductMapper.toDto(updatedOrganizationProduct);

        restOrganizationProductMockMvc.perform(put("/api/organization-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProductDTO)))
            .andExpect(status().isOk());

        // Validate the OrganizationProduct in the database
        List<OrganizationProduct> organizationProductList = organizationProductRepository.findAll();
        assertThat(organizationProductList).hasSize(databaseSizeBeforeUpdate);
        OrganizationProduct testOrganizationProduct = organizationProductList.get(organizationProductList.size() - 1);
        assertThat(testOrganizationProduct.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizationProduct() throws Exception {
        int databaseSizeBeforeUpdate = organizationProductRepository.findAll().size();

        // Create the OrganizationProduct
        OrganizationProductDTO organizationProductDTO = organizationProductMapper.toDto(organizationProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationProductMockMvc.perform(put("/api/organization-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationProduct in the database
        List<OrganizationProduct> organizationProductList = organizationProductRepository.findAll();
        assertThat(organizationProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganizationProduct() throws Exception {
        // Initialize the database
        organizationProductRepository.saveAndFlush(organizationProduct);

        int databaseSizeBeforeDelete = organizationProductRepository.findAll().size();

        // Delete the organizationProduct
        restOrganizationProductMockMvc.perform(delete("/api/organization-products/{id}", organizationProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationProduct> organizationProductList = organizationProductRepository.findAll();
        assertThat(organizationProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationProduct.class);
        OrganizationProduct organizationProduct1 = new OrganizationProduct();
        organizationProduct1.setId(1L);
        OrganizationProduct organizationProduct2 = new OrganizationProduct();
        organizationProduct2.setId(organizationProduct1.getId());
        assertThat(organizationProduct1).isEqualTo(organizationProduct2);
        organizationProduct2.setId(2L);
        assertThat(organizationProduct1).isNotEqualTo(organizationProduct2);
        organizationProduct1.setId(null);
        assertThat(organizationProduct1).isNotEqualTo(organizationProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationProductDTO.class);
        OrganizationProductDTO organizationProductDTO1 = new OrganizationProductDTO();
        organizationProductDTO1.setId(1L);
        OrganizationProductDTO organizationProductDTO2 = new OrganizationProductDTO();
        assertThat(organizationProductDTO1).isNotEqualTo(organizationProductDTO2);
        organizationProductDTO2.setId(organizationProductDTO1.getId());
        assertThat(organizationProductDTO1).isEqualTo(organizationProductDTO2);
        organizationProductDTO2.setId(2L);
        assertThat(organizationProductDTO1).isNotEqualTo(organizationProductDTO2);
        organizationProductDTO1.setId(null);
        assertThat(organizationProductDTO1).isNotEqualTo(organizationProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationProductMapper.fromId(null)).isNull();
    }
}

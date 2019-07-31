package com.poc.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.poc.app.domain.OrganizationProduct} entity.
 */
public class OrganizationProductDTO implements Serializable {

    private Long id;

    private Boolean status;


    private Long organizationId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationProductDTO organizationProductDTO = (OrganizationProductDTO) o;
        if (organizationProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizationProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizationProductDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", organization=" + getOrganizationId() +
            ", product=" + getProductId() +
            "}";
    }
}

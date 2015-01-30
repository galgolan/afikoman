package com.biocatch.afikoman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A CompanyOrganizationPairing.
 */
@Document(collection = "T_COMPANYORGANIZATIONPAIRING")
public class CompanyOrganizationPairing implements Serializable {

    @Id
    private String id;

    @Field("company")
    private String company;

    @Field("organization")
    private String organization;

    @Field("company_id")
    private Integer companyId;

    @Field("organization_id")
    private Integer organizationId;

    @Field("metadata")
    private String metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyOrganizationPairing companyOrganizationPairing = (CompanyOrganizationPairing) o;

        if (id != null ? !id.equals(companyOrganizationPairing.id) : companyOrganizationPairing.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CompanyOrganizationPairing{" +
                "id=" + id +
                ", Company='" + company + "'" +
                ", Organization='" + organization + "'" +
                ", CompanyId='" + companyId + "'" +
                ", OrganizationId='" + organizationId + "'" +
                ", Metadata='" + metadata + "'" +
                '}';
    }
}

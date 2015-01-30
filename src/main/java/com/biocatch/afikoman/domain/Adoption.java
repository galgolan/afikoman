package com.biocatch.afikoman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Adoption.
 */
@Document(collection = "T_ADOPTION")
public class Adoption implements Serializable {

    @Id
    private String id;

    @Field("employee_name")
    private String employeeName;

    @Field("employee_email")
    private String employeeEmail;

    @Field("company")
    private String company;

    @Field("organization")
    private String organization;

    @Field("kid")
    private String kid;

    @Field("kid_id")
    private String kidId;

    @Field("organization_id")
    private String organizationId;

    @Field("company_id")
    private String companyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
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

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Adoption adoption = (Adoption) o;

        if (id != null ? !id.equals(adoption.id) : adoption.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Adoption{" +
                "id=" + id +
                ", employeeName='" + employeeName + "'" +
                ", employeeEmail='" + employeeEmail + "'" +
                ", company='" + company + "'" +
                ", organization='" + organization + "'" +
                ", kid='" + kid + "'" +
                ", kidId='" + kidId + "'" +
                ", organizationId='" + organizationId + "'" +
                ", companyId='" + companyId + "'" +
                '}';
    }
}

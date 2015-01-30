package com.biocatch.afikoman.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.biocatch.afikoman.domain.util.CustomDateTimeDeserializer;
import com.biocatch.afikoman.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Company.
 */
@Document(collection = "T_COMPANY")
public class Company implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("date_added")
    private DateTime dateAdded;

    @Field("password")
    private String password;

    @Field("poc_email")
    private String pocEmail;

    @Field("metadata")
    private String metadata;

    @Field("description")
    private String description;

    @Field("website")
    private String website;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(DateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", dateAdded='" + dateAdded + "'" +
                ", password='" + password + "'" +
                ", pocEmail='" + pocEmail + "'" +
                ", metadata='" + metadata + "'" +
                ", description='" + description + "'" +
                ", website='" + website + "'" +
                '}';
    }
}

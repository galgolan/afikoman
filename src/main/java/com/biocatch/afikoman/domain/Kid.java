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
 * A Kid.
 */
@Document(collection = "T_KID")
public class Kid implements Serializable {

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("date_added")
    private DateTime dateAdded;

    @Field("organization")
    private String organization;

    @Field("desired_gift")
    private String desiredGift;

    @Field("personal_note")
    private String personalNote;

    @Field("metadata")
    private String metadata;

    @Field("picture")
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(DateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDesiredGift() {
        return desiredGift;
    }

    public void setDesiredGift(String desiredGift) {
        this.desiredGift = desiredGift;
    }

    public String getPersonalNote() {
        return personalNote;
    }

    public void setPersonalNote(String personalNote) {
        this.personalNote = personalNote;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Kid kid = (Kid) o;

        if (id != null ? !id.equals(kid.id) : kid.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Kid{" +
                "id=" + id +
                ", firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", dateAdded='" + dateAdded + "'" +
                ", organization='" + organization + "'" +
                ", desiredGift='" + desiredGift + "'" +
                ", personalNote='" + personalNote + "'" +
                ", metadata='" + metadata + "'" +
                ", picture='" + picture + "'" +
                '}';
    }
}

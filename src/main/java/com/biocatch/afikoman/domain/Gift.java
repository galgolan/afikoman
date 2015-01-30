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
 * A Gift.
 */
@Document(collection = "T_GIFT")
public class Gift implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("date_added")
    private DateTime dateAdded;

    @Field("in_stock")
    private Boolean inStock;

    @Field("price")
    private Integer price;

    @Field("picture")
    private String picture;

    @Field("metadata")
    private String metadata;

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

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

        Gift gift = (Gift) o;

        if (id != null ? !id.equals(gift.id) : gift.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", dateAdded='" + dateAdded + "'" +
                ", inStock='" + inStock + "'" +
                ", price='" + price + "'" +
                ", picture='" + picture + "'" +
                ", metadata='" + metadata + "'" +
                '}';
    }
}

package de.beosign.quizzer.jpatest;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import de.beosign.quizzer.model.AbstractBaseEntity;

@Entity
public class Address extends AbstractBaseEntity {
    public enum Type {
        WORK, HOME
    }

    private String street;
    private String zipCode;
    private String country;
    private Type type;

    private Person person;

    public Address() {
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", zipCode=" + zipCode + ", country=" + country + ", type=" + type + "]";
    }

}

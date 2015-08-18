package de.beosign.quizzer.jpatest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import de.beosign.quizzer.model.LongKeyBaseEntity;

@Entity
public class Person extends LongKeyBaseEntity {
    public enum Sex {
        MALE, FEMALE
    }

    private String lastName;
    private String firstName;
    private Sex sex;
    private Date dateOfBirth;

    private List<Address> addresses = new ArrayList<Address>();
    private List<Club> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Past
    @Temporal(TemporalType.DATE)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PersonClubs", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "club") })
    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public String toString() {
        return "Person [lastName=" + lastName + ", firstName=" + firstName + ", sex=" + sex + ", dateOfBirth=" + dateOfBirth + ", addresses=" + addresses
                + ", clubs=" + clubs + "]";
    }

}

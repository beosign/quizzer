package de.beosign.quizzer.jpatest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

@Entity
public class Club {
    private String name;
    private int fee;
    private int constitutionYear;
    private List<Person> members = new ArrayList<>();

    private Long version;

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getConstitutionYear() {
        return constitutionYear;
    }

    public void setConstitutionYear(int constitutionYear) {
        this.constitutionYear = constitutionYear;
    }

    @ManyToMany(mappedBy = "clubs")
    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Club [name=" + name + ", fee=" + fee + ", constitutionYear=" + constitutionYear + ", version=" + version + "]";
    }

}

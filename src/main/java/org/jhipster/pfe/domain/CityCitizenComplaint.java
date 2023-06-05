package org.jhipster.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.pfe.domain.enumeration.Complaintstate;

/**
 * A CityCitizenComplaint.
 */
@Entity
@Table(name = "city_citizen_complaint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "citycitizencomplaint")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityCitizenComplaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sharewithpublic")
    private Boolean sharewithpublic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "complaintstate", nullable = false)
    private Complaintstate complaintstate;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phonenumber;

    @NotNull
    @Column(name = "googlemapsx", nullable = false)
    private String googlemapsx;

    @NotNull
    @Column(name = "googlemapy", nullable = false)
    private String googlemapy;

    @ManyToOne(optional = false)
    @NotNull
    private ComplaintCategory complaintCategory;

    @ManyToOne(optional = true)
    private User user;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_city_citizen_complaint__city_citizen_photo",
        joinColumns = @JoinColumn(name = "city_citizen_complaint_id"),
        inverseJoinColumns = @JoinColumn(name = "city_citizen_photo_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CityCitizenPhoto> cityCitizenPhotos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CityCitizenComplaint id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public CityCitizenComplaint address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public CityCitizenComplaint description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public CityCitizenComplaint date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getSharewithpublic() {
        return this.sharewithpublic;
    }

    public CityCitizenComplaint sharewithpublic(Boolean sharewithpublic) {
        this.setSharewithpublic(sharewithpublic);
        return this;
    }

    public void setSharewithpublic(Boolean sharewithpublic) {
        this.sharewithpublic = sharewithpublic;
    }

    public Complaintstate getComplaintstate() {
        return this.complaintstate;
    }

    public CityCitizenComplaint complaintstate(Complaintstate complaintstate) {
        this.setComplaintstate(complaintstate);
        return this;
    }

    public void setComplaintstate(Complaintstate complaintstate) {
        this.complaintstate = complaintstate;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public CityCitizenComplaint firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public CityCitizenComplaint lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public CityCitizenComplaint email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public CityCitizenComplaint phonenumber(String phonenumber) {
        this.setPhonenumber(phonenumber);
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getGooglemapsx() {
        return this.googlemapsx;
    }

    public CityCitizenComplaint googlemapsx(String googlemapsx) {
        this.setGooglemapsx(googlemapsx);
        return this;
    }

    public void setGooglemapsx(String googlemapsx) {
        this.googlemapsx = googlemapsx;
    }

    public String getGooglemapy() {
        return this.googlemapy;
    }

    public CityCitizenComplaint googlemapy(String googlemapy) {
        this.setGooglemapy(googlemapy);
        return this;
    }

    public void setGooglemapy(String googlemapy) {
        this.googlemapy = googlemapy;
    }

    public ComplaintCategory getComplaintCategory() {
        return this.complaintCategory;
    }

    public void setComplaintCategory(ComplaintCategory complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public CityCitizenComplaint complaintCategory(ComplaintCategory complaintCategory) {
        this.setComplaintCategory(complaintCategory);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CityCitizenComplaint user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<CityCitizenPhoto> getCityCitizenPhotos() {
        return this.cityCitizenPhotos;
    }

    public void setCityCitizenPhotos(Set<CityCitizenPhoto> cityCitizenPhotos) {
        this.cityCitizenPhotos = cityCitizenPhotos;
    }

    public CityCitizenComplaint cityCitizenPhotos(Set<CityCitizenPhoto> cityCitizenPhotos) {
        this.setCityCitizenPhotos(cityCitizenPhotos);
        return this;
    }

    public CityCitizenComplaint addCityCitizenPhoto(CityCitizenPhoto cityCitizenPhoto) {
        this.cityCitizenPhotos.add(cityCitizenPhoto);
        return this;
    }

    public CityCitizenComplaint removeCityCitizenPhoto(CityCitizenPhoto cityCitizenPhoto) {
        this.cityCitizenPhotos.remove(cityCitizenPhoto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityCitizenComplaint)) {
            return false;
        }
        return id != null && id.equals(((CityCitizenComplaint) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityCitizenComplaint{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", sharewithpublic='" + getSharewithpublic() + "'" +
            ", complaintstate='" + getComplaintstate() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", email='" + getEmail() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", googlemapsx='" + getGooglemapsx() + "'" +
            ", googlemapy='" + getGooglemapy() + "'" +
            "}";
    }
}

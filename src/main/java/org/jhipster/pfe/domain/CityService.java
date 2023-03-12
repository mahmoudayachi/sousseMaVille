package org.jhipster.pfe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CityService.
 */
@Entity
@Table(name = "city_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cityservice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @NotNull
    @Size(max = 2000)
    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    @NotNull
    @Size(max = 150)
    @Column(name = "tooltip", length = 150, nullable = false)
    private String tooltip;

    @NotNull
    @Column(name = "icon", nullable = false)
    private String icon;

    @NotNull
    @Min(value = 1)
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @ManyToOne(optional = false)
    @NotNull
    private CityServiceState cityservicestate;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_city_service__userrole",
        joinColumns = @JoinColumn(name = "city_service_id"),
        inverseJoinColumns = @JoinColumn(name = "userrole_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userroles" }, allowSetters = true)
    private Set<UserRole> userroles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CityService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public CityService title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public CityService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltip() {
        return this.tooltip;
    }

    public CityService tooltip(String tooltip) {
        this.setTooltip(tooltip);
        return this;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getIcon() {
        return this.icon;
    }

    public CityService icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return this.order;
    }

    public CityService order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public CityServiceState getCityservicestate() {
        return this.cityservicestate;
    }

    public void setCityservicestate(CityServiceState cityServiceState) {
        this.cityservicestate = cityServiceState;
    }

    public CityService cityservicestate(CityServiceState cityServiceState) {
        this.setCityservicestate(cityServiceState);
        return this;
    }

    public Set<UserRole> getUserroles() {
        return this.userroles;
    }

    public void setUserroles(Set<UserRole> userRoles) {
        this.userroles = userRoles;
    }

    public CityService userroles(Set<UserRole> userRoles) {
        this.setUserroles(userRoles);
        return this;
    }

    public CityService addUserrole(UserRole userRole) {
        this.userroles.add(userRole);
        return this;
    }

    public CityService removeUserrole(UserRole userRole) {
        this.userroles.remove(userRole);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityService)) {
            return false;
        }
        return id != null && id.equals(((CityService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityService{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", tooltip='" + getTooltip() + "'" +
            ", icon='" + getIcon() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}

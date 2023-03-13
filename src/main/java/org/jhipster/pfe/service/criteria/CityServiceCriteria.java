package org.jhipster.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.jhipster.pfe.domain.CityService} entity. This class is used
 * in {@link org.jhipster.pfe.web.rest.CityServiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /city-services?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityServiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter tooltip;

    private StringFilter icon;

    private IntegerFilter order;

    private LongFilter cityservicestateId;

    private LongFilter userroleId;

    private Boolean distinct;

    public CityServiceCriteria() {}

    public CityServiceCriteria(CityServiceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.tooltip = other.tooltip == null ? null : other.tooltip.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.cityservicestateId = other.cityservicestateId == null ? null : other.cityservicestateId.copy();
        this.userroleId = other.userroleId == null ? null : other.userroleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CityServiceCriteria copy() {
        return new CityServiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getTooltip() {
        return tooltip;
    }

    public StringFilter tooltip() {
        if (tooltip == null) {
            tooltip = new StringFilter();
        }
        return tooltip;
    }

    public void setTooltip(StringFilter tooltip) {
        this.tooltip = tooltip;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public StringFilter icon() {
        if (icon == null) {
            icon = new StringFilter();
        }
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public IntegerFilter order() {
        if (order == null) {
            order = new IntegerFilter();
        }
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public LongFilter getCityservicestateId() {
        return cityservicestateId;
    }

    public LongFilter cityservicestateId() {
        if (cityservicestateId == null) {
            cityservicestateId = new LongFilter();
        }
        return cityservicestateId;
    }

    public void setCityservicestateId(LongFilter cityservicestateId) {
        this.cityservicestateId = cityservicestateId;
    }

    public LongFilter getUserroleId() {
        return userroleId;
    }

    public LongFilter userroleId() {
        if (userroleId == null) {
            userroleId = new LongFilter();
        }
        return userroleId;
    }

    public void setUserroleId(LongFilter userroleId) {
        this.userroleId = userroleId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CityServiceCriteria that = (CityServiceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(tooltip, that.tooltip) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(order, that.order) &&
            Objects.equals(cityservicestateId, that.cityservicestateId) &&
            Objects.equals(userroleId, that.userroleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, tooltip, icon, order, cityservicestateId, userroleId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityServiceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (tooltip != null ? "tooltip=" + tooltip + ", " : "") +
            (icon != null ? "icon=" + icon + ", " : "") +
            (order != null ? "order=" + order + ", " : "") +
            (cityservicestateId != null ? "cityservicestateId=" + cityservicestateId + ", " : "") +
            (userroleId != null ? "userroleId=" + userroleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

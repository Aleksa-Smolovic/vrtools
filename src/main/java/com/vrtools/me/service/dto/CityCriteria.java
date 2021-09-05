package com.vrtools.me.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.vrtools.me.domain.City} entity. This class is used
 * in {@link com.vrtools.me.web.rest.CityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter population;

    private BooleanFilter isCapital;

    private LongFilter countryId;

    public CityCriteria() {
    }

    public CityCriteria(CityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.population = other.population == null ? null : other.population.copy();
        this.isCapital = other.isCapital == null ? null : other.isCapital.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
    }

    @Override
    public CityCriteria copy() {
        return new CityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getPopulation() {
        return population;
    }

    public void setPopulation(IntegerFilter population) {
        this.population = population;
    }

    public BooleanFilter getIsCapital() {
        return isCapital;
    }

    public void setIsCapital(BooleanFilter isCapital) {
        this.isCapital = isCapital;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CityCriteria that = (CityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(population, that.population) &&
            Objects.equals(isCapital, that.isCapital) &&
            Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        population,
        isCapital,
        countryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (population != null ? "population=" + population + ", " : "") +
                (isCapital != null ? "isCapital=" + isCapital + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}

package com.vrtools.me.repository;

import com.vrtools.me.domain.City;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Spring Data  repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    @Query(nativeQuery = true, value = "select c.name, c.population, c.is_capital as 'isCapital', co.name as 'countryName' from city c inner join country co on co.id = c.country_id")
    List<Map<String, ?>> getCityReportData();

}

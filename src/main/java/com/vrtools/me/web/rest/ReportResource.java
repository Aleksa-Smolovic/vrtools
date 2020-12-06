package com.vrtools.me.web.rest;

import com.vrtools.me.repository.CityRepository;
import com.vrtools.me.repository.CountryRepository;
import com.vrtools.me.service.JasperReportService;
import com.vrtools.me.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/reports/")
public class ReportResource {

    private final JasperReportService reportService;

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    public ReportResource(JasperReportService reportService, CityRepository cityRepository, CountryRepository countryRepository) {
        this.reportService = reportService;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/all-cities")
    public ResponseEntity<Resource> generateCitiesReport() throws IOException, JRException {
        String title = "Gradovi";
        HttpHeaders headers = ResponseUtil.prepareResponseHeadersForDownload(title + ".pdf");
        Resource report = reportService.generateReport("cities", title, cityRepository.findAll());
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

    @GetMapping("/all-cities/v2")
    public ResponseEntity<Resource> generateCitiesReportV2() throws IOException, JRException {
        String title = "Gradovi";
        HttpHeaders headers = ResponseUtil.prepareResponseHeadersForDownload(title + ".pdf");
        Resource report = reportService.generateReportV2("cities2", title, cityRepository.getCityReportData());
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

    @GetMapping("/all-countries")
    public ResponseEntity<Resource> generateCountriesReport() throws IOException, JRException {
        String title = "Države";
        HttpHeaders headers = ResponseUtil.prepareResponseHeadersForDownload(title + ".pdf");
        Resource report = reportService.generateReportV3("countries", "citiesSubReport", title, countryRepository.findAll());
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

}

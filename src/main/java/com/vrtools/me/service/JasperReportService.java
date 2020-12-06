package com.vrtools.me.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class JasperReportService {

    private final static String templateDir = "reports/templates/";
    private final static String reportExtension = ".jrxml";

    private JasperReport getReportTemplate(String reportName) throws IOException, JRException {
        return JasperCompileManager.compileReport(new ClassPathResource(templateDir + reportName + reportExtension).getInputStream());
    }

    public Resource generateReport(String reportName, String title, Collection<?> data) throws IOException, JRException {
        JasperReport report = getReportTemplate(reportName);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("data", new JRBeanCollectionDataSource(new ArrayList<>(data)));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        File file = File.createTempFile(LocalDate.now().toString(), UUID.randomUUID() + ".pdf");
        JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());

        return new FileSystemResource(file);
    }

    public Resource generateReportV2(String reportName, String title, Collection<Map<String, ?>> data) throws IOException, JRException {
        JasperReport report = getReportTemplate(reportName);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("data", new JRMapCollectionDataSource(data));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        File file = File.createTempFile(LocalDate.now().toString(), UUID.randomUUID() + ".pdf");
        JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());

        return new FileSystemResource(file);
    }

    public Resource generateReportV3(String reportName, String subReportName, String title, Collection<?> data) throws IOException, JRException {
        JasperReport report = getReportTemplate(reportName);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", title);
        parameters.put("data", new JRBeanCollectionDataSource(data));
        parameters.put("subReport", getReportTemplate(subReportName));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        File file = File.createTempFile(LocalDate.now().toString(), UUID.randomUUID() + ".pdf");
        JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());

        return new FileSystemResource(file);
    }

}

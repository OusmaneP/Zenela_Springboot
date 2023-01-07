package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.Report;
import com.podosoft.zenela.Repositories.ReportRepository;
import com.podosoft.zenela.Services.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }
}

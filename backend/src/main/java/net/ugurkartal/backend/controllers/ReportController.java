package net.ugurkartal.backend.controllers;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.services.abstracts.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    @SuppressWarnings("unused")
    private final ReportService reportService;
}
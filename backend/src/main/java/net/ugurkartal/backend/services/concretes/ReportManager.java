package net.ugurkartal.backend.services.concretes;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.abstracts.ReportService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportManager implements ReportService {
    @SuppressWarnings("unused")
    private final ProductRepository productRepository;
}
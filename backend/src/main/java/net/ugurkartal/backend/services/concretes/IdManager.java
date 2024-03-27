package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.services.abstracts.IdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdManager implements IdService {
    @Override
    public String generateProductId() {
        return "PRD-" + UUID.randomUUID();
    }

    @Override
    public String generateCategoryId() {
            return "CAT-" + UUID.randomUUID();
    }

    @Override
    public String generateSupplierId() {
        return "SUP-" + UUID.randomUUID();
    }

    @Override
    public String generatePurchaseId() {
        return "PRC-" + UUID.randomUUID();
    }
}
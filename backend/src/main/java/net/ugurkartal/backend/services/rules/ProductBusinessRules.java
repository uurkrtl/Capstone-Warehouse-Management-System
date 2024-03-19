package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.messages.ProductMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBusinessRules {
    private final ProductRepository productRepository;

    public void checkIfProductNameExists(String productName) {
        if(this.productRepository.existsByName(productName)) {
            throw new DuplicateRecordException(ProductMessage.PRODUCT_NAME_ALREADY_EXISTS);
        }
    }
}
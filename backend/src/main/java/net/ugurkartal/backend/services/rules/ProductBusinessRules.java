package net.ugurkartal.backend.services.rules;

import lombok.RequiredArgsConstructor;
import net.ugurkartal.backend.core.exceptions.types.DuplicateRecordException;
import net.ugurkartal.backend.core.exceptions.types.RecordNotFoundException;
import net.ugurkartal.backend.core.exceptions.types.StockNotZeroException;
import net.ugurkartal.backend.models.Product;
import net.ugurkartal.backend.repositories.ProductRepository;
import net.ugurkartal.backend.services.messages.ProductMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductBusinessRules {
    private final ProductRepository productRepository;

    public void checkIfProductNameExists(String productName) {
        if(this.productRepository.existsByName(productName)) {
            throw new DuplicateRecordException(ProductMessage.PRODUCT_NAME_ALREADY_EXISTS);
        }
    }

    public void checkIfProductNameExists(String productName, String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            if(!product.getName().equals(productName) && productRepository.existsByName(productName)) {
                throw new DuplicateRecordException(ProductMessage.PRODUCT_NAME_ALREADY_EXISTS);
            }
        }
    }

    public void checkIfProductByIdNotFound(String id) {
        if(!this.productRepository.existsById(id)) {
            throw new RecordNotFoundException(ProductMessage.PRODUCT_NOT_FOUND);
        }
    }

    public void checkIfProductStockNotZero(String id, boolean status) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            if(product.getStock() > 0 && !status) {
                throw new StockNotZeroException(ProductMessage.PRODUCT_STOCK_NOT_ZERO);
            }
        }
    }
}
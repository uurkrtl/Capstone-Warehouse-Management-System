package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.rules.SupplierBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class SupplierManagerTest {
    @InjectMocks
    private SupplierManager supplierManager;
    private ModelMapper modelMapper;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierBusinessRules supplierBusinessRules;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addSupplier_whenSupplierRequestIsValid_shouldReturnSupplierCreatedResponse() {
        // Given
        Supplier supplier = Supplier.builder()
                .name("Supplier")
                .contactName("Contact Name")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        SupplierCreatedResponse expectedResponse = SupplierCreatedResponse.builder()
                .id("1")
                .name("Supplier")
                .contactName("Contact Name")
                .build();

        SupplierRequest supplierRequest = SupplierRequest.builder()
                .name("Supplier")
                .contactName("Contact Name")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        // When
        when(idService.generateSupplierId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(supplierRequest, Supplier.class)).thenReturn(supplier);
        when(supplierRepository.save(supplier)).thenReturn(supplier);
        when(modelMapper.map(supplier, SupplierCreatedResponse.class)).thenReturn(SupplierCreatedResponse.builder().id("1").build());

        SupplierCreatedResponse actualResponse = supplierManager.addSupplier(supplierRequest);

        // Then
        verify(supplierBusinessRules, times(1)).checkIfSupplierNameExists(anyString());
        verify(supplierRepository, times(1)).save(supplier);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}
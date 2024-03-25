package net.ugurkartal.backend.services.concretes;

import net.ugurkartal.backend.core.mappers.ModelMapperService;
import net.ugurkartal.backend.models.Supplier;
import net.ugurkartal.backend.repositories.SupplierRepository;
import net.ugurkartal.backend.services.abstracts.IdService;
import net.ugurkartal.backend.services.dtos.requests.SupplierRequest;
import net.ugurkartal.backend.services.dtos.responses.SupplierCreatedResponse;
import net.ugurkartal.backend.services.dtos.responses.SupplierGetAllResponse;
import net.ugurkartal.backend.services.rules.SupplierBusinessRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void getAllSuppliers_shouldReturnsListOfSuppliers() {
        // Given
        List<Supplier> suppliers = List.of(
                Supplier.builder().id("1").build(),
                Supplier.builder().id("2").build()
        );

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<SupplierGetAllResponse> response = supplierManager.getAllSuppliers();

        // Then
        assertEquals(2, response.size());
    }

    @Test
    void getSupplierById_whenSupplierExists_shouldReturnSupplier() {
        // Given
        Supplier supplier = Supplier.builder().id("1").build();
        SupplierCreatedResponse expectedResponse = SupplierCreatedResponse.builder().id("1").build();

        // When
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(supplier, SupplierCreatedResponse.class)).thenReturn(expectedResponse);
        when(supplierRepository.findById("1")).thenReturn(Optional.of(supplier));

        SupplierCreatedResponse actual = supplierManager.getSupplierById("1");

        // Then
        assertEquals(expectedResponse.getId(), actual.getId());
    }

    @Test
    void getSupplierById_WhenSupplierDoesNotExist_shouldThrowsNoSuchElementException() {
        // Given & When
        when(supplierRepository.findById("1")).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> supplierManager.getSupplierById("1"));
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

    @Test
    void updateSupplier_whenSupplierRequestIsValid_shouldReturnSupplierCreatedResponse() {
        // Given
        String id = "1";
        SupplierRequest request = SupplierRequest.builder()
                .name("Updated Test")
                .contactName("Updated Test")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        Supplier updatedSupplier = Supplier.builder()
                .id(id)
                .name("Updated Test")
                .contactName("Updated Test")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        SupplierCreatedResponse expectedResponse = SupplierCreatedResponse.builder()
                .id("1")
                .name("Updated Test")
                .contactName("Updated Test")
                .email("test@test.com")
                .phone("1234567890")
                .build();

        // When
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(request, Supplier.class)).thenReturn(updatedSupplier);
        when(supplierRepository.findById(id)).thenReturn(Optional.of(Supplier.builder().id(id).build()));
        when(supplierRepository.save(updatedSupplier)).thenReturn(updatedSupplier);
        when(modelMapper.map(updatedSupplier, SupplierCreatedResponse.class)).thenReturn(expectedResponse);

        SupplierCreatedResponse actualResponse = supplierManager.updateSupplier(id, request);

        // Then
        verify(supplierBusinessRules, times(1)).checkIfSupplierByIdNotFound(anyString());
        verify(supplierBusinessRules, times(1)).checkIfSupplierNameExists(anyString(), anyString());
        verify(supplierRepository, times(1)).save(updatedSupplier);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getContactName(), actualResponse.getContactName());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getPhone(), actualResponse.getPhone());
    }
}
package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Entry;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.EntryRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.PaymentRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntryServiceTest {

    @Mock
    private EntryRepositoryImpl entryRepository;

    @Mock
    private PaymentRepositoryImpl paymentRepository;

    @Mock
    private ProductRepositoryImpl productRepository;

    @InjectMocks
    private EntryService entryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("응모 생성")
    void testCreateEntry() {
        String productId = "product-123";
        String shippingAddress = "123 Main St";
        String paymentMethod = "Credit Card";
        String entryId = "entry-123";

        Product mockProduct = new Product(productId, "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, shippingAddress, paymentMethod);

        when(productRepository.findById(productId)).thenReturn(mockProduct);
        when(entryRepository.save(any(Entry.class))).thenReturn(mockEntry);

        Entry entry = entryService.createEntry(productId, shippingAddress, paymentMethod);

        assertNotNull(entry);
        assertEquals(mockProduct, entry.getProduct());
        assertEquals(shippingAddress, entry.getShippingAddress());
        assertEquals(paymentMethod, entry.getPaymentMethod());

        verify(productRepository, times(1)).findById(productId);
        verify(entryRepository, times(1)).save(any(Entry.class));
    }

    @Test
    @DisplayName("응모 후 결제")
    void testProcessPayment() {
        String entryId = "entry-123";
        PaymentDto paymentDto = new PaymentDto("123 Main St", "Credit Card", 100.0);

        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        Payment mockPayment = new Payment("payment-123", mockEntry, paymentDto.getAmount());
        mockPayment.setSuccess(true);

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment payment = entryService.processPayment(entryId, paymentDto);

        assertNotNull(payment);
        assertTrue(payment.isSuccess());
        assertEquals(paymentDto.getAmount(), payment.getAmount());

        verify(entryRepository, times(1)).findById(entryId);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("응모 확인 시 미당첨이라면 환불")
    void testProcessWinnerWithRefund() {
        String entryId = "entry-123";

        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        mockEntry.setIsWinner('2'); // 미당첨
        Payment mockPayment = new Payment("payment-123", mockEntry, 100.0);
        mockPayment.setSuccess(true);

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);
        when(paymentRepository.findById(entryId)).thenReturn(mockPayment);

        Entry entry = entryService.processWinner(entryId);

        assertNotNull(entry);
        assertEquals('2', entry.getIsWinner());
        assertFalse(mockPayment.isSuccess());

        verify(entryRepository, times(1)).findById(entryId);
        verify(paymentRepository, times(1)).findById(entryId);
        verify(paymentRepository, times(1)).save(mockPayment);
    }

    @Test
    @DisplayName("응모 확인 시 당첨이라면 상태 유지")
    void testProcessWinnerWithoutRefund() {
        String entryId = "entry-123";

        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        mockEntry.setIsWinner('1'); // 당첨

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);

        Entry entry = entryService.processWinner(entryId);

        assertNotNull(entry);
        assertEquals('1', entry.getIsWinner());

        verify(entryRepository, times(1)).findById(entryId);
        verifyNoInteractions(paymentRepository);
    }
}

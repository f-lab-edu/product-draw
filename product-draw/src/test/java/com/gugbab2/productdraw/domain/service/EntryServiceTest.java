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

        /*
        [given]
        1. 고정된 mockProduct, mockEntry 리턴
         */
        Product mockProduct = new Product(productId, "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, shippingAddress, paymentMethod);

        when(productRepository.findById(productId)).thenReturn(mockProduct);
        when(entryRepository.save(any(Entry.class))).thenReturn(mockEntry);

        /*
        [when]
        1. 응모 생성
         */
        Entry entry = entryService.createEntry(productId, shippingAddress, paymentMethod);

        /*
         [then]
         1. 응모가 not null 이어야 한다.
         2. mockProduct 와 응모 내 product 는 동일해야 한다.
         3. shippingAddress 와 응모 내 shippingAddress 는 동일해야 한다.
         4. paymentMethod 와 응모 내 paymentMethod 는 동일해야 한다.
          */
        assertNotNull(entry);
        assertEquals(mockProduct, entry.getProduct());
        assertEquals(shippingAddress, entry.getShippingAddress());
        assertEquals(paymentMethod, entry.getPaymentMethod());

//        verify(productRepository, times(1)).findById(productId);
//        verify(entryRepository, times(1)).save(any(Entry.class));
    }

    @Test
    @DisplayName("응모 후 결제")
    void testProcessPayment() {
        String entryId = "entry-123";
        String shippingAddress = "123 Main St";
        String paymentMethod = "Credit Card";
        PaymentDto paymentDto = new PaymentDto(shippingAddress, paymentMethod, 100.0);

        /*
         [given]
         1. 결제 상태 변경 : 결제 성공
         2. 고정된 mockEntry, mockPayment 리턴
         */
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        Payment mockPayment = new Payment("payment-123", mockEntry, paymentDto.getAmount());
        mockPayment.setSuccess(true);

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        /*
         [when]
         1. 결제 진행
         */
        Payment payment = entryService.processPayment(entryId, paymentDto);

        /*
         [then]
         1. 결제가 not null 이어야 한다.
         2. 결제가 성공해야 한다.
         3. paymentDto 내 금액은 결제 내 금액과 동일해야 한다.
         */
        assertNotNull(payment);
        assertTrue(payment.isSuccess());
        assertEquals(paymentDto.getAmount(), payment.getAmount());

//        verify(entryRepository, times(1)).findById(entryId);
//        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("응모 확인 시 미당첨이라면 환불")
    void testProcessWinnerWithRefund() {
        String entryId = "entry-123";

        /*
         [given]
         1. 응모 상태 변경 : 미당첨
         2. 고정된 mockEntry, mockPayment 리턴
         */
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        mockEntry.setIsWinner('2'); // 미당첨
        Payment mockPayment = new Payment("payment-123", mockEntry, 100.0);
        mockPayment.setSuccess(true);

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);
        when(paymentRepository.findById(entryId)).thenReturn(mockPayment);

        /*
         [when]
         1. 응모 상태 확인
         2. 미당첨이라면 자동 환불
         */
        Entry entry = entryService.checkWinner(entryId);

        /*
         [then]
         1. 응모가 not null 이어야 한다.
         2. 응모 결과가 미당첨이어야 한다.
         3. 결제는 성공 상태여야 한다.
         */
        assertNotNull(entry);
        assertEquals('2', entry.getIsWinner());
        assertFalse(mockPayment.isSuccess());

//        verify(entryRepository, times(1)).findById(entryId);
//        verify(paymentRepository, times(1)).findById(entryId);
//        verify(paymentRepository, times(1)).save(mockPayment);
    }

    @Test
    @DisplayName("응모 확인 시 당첨이라면 상태 유지")
    void testProcessWinnerWithoutRefund() {
        String entryId = "entry-123";

        /*
         [given]
         1. 응모 상태 변경 : 당첨
         2. 고정된 mockEntry, mockPayment 리턴
         */
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "test");
        Entry mockEntry = new Entry(entryId, mockProduct, "123 Main St", "Credit Card");
        mockEntry.setIsWinner('1'); // 당첨

        when(entryRepository.findById(entryId)).thenReturn(mockEntry);

        /*
         [when]
         1. 응모 상태 확인
         2. 당첨이라면 상태 유지
         */
        Entry entry = entryService.checkWinner(entryId);

        /*
         [then]
         1. 응모가 not null 이어야 한다.
         2. 응모 결과가 당첨이어야 한다.
         */
        assertNotNull(entry);
        assertEquals('1', entry.getIsWinner());

        verify(entryRepository, times(1)).findById(entryId);
        verifyNoInteractions(paymentRepository);
    }
}
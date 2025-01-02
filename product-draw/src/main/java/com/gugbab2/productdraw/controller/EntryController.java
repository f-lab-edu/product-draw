package com.gugbab2.productdraw.controller;

import com.gugbab2.productdraw.domain.entity.Entry;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.service.EntryService;
import com.gugbab2.productdraw.dto.EntryDto;
import com.gugbab2.productdraw.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @PostMapping("/submit")
    public ResponseEntity<Entry> createEntry(@RequestBody EntryDto entryDto) {
        Entry entry = entryService.createEntry(entryDto.getProductId(), entryDto.getShippingAddress(), entryDto.getPaymentMethod());
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    @PostMapping("/{entryId}/pay")
    public ResponseEntity<Payment> processPayment(@PathVariable String entryId, @RequestBody PaymentDto paymentDto) {
        Payment payment = entryService.processPayment(entryId, paymentDto);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PostMapping("/{entryId}/check-winner")
    public ResponseEntity<Entry> checkWinner(@PathVariable String entryId) {
        Entry entry = entryService.checkWinner(entryId);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }
}

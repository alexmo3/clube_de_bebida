package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.controller.request.SaleRequest;
import com.clubedebebida.backend.dto.SaleDTO;
import com.clubedebebida.backend.dto.SubscriptionDTO;
import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
import com.clubedebebida.backend.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Flow;

@RestController
@RequestMapping("/vendas")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<Page<SaleDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<SaleDTO> salesDTO = saleService.findAll(pageable);

        return ResponseEntity.ok(salesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
        SaleDTO subscriptionsDTO = saleService.findById(id);

        return ResponseEntity.ok(subscriptionsDTO);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> save(@Valid @RequestBody SaleRequest saleRequest) {
        Subscription subscription = new Subscription();
        subscription.setId(saleRequest.subscriptionId());

        User waiter = new User();
        waiter.setId(saleRequest.waiterId());

        SaleDTO savedSale = saleService.save(saleService.requestToDTO(saleRequest));

        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable Long id, @RequestBody SaleDTO saleDTO) {
        SaleDTO updatedSale = saleService.update(id, saleDTO);

        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.delete(id);

        return ResponseEntity.noContent().build();
    }

}

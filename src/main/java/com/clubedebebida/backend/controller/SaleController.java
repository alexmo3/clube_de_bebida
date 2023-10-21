package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.dto.SaleDTO;
import com.clubedebebida.backend.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venda")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<Page<SaleDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable
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
    public ResponseEntity<SaleDTO> save(@Valid @RequestBody SaleDTO saleDTO) {
        SaleDTO savedConsumption = saleService.save(saleDTO);

        return new ResponseEntity<>(savedConsumption, HttpStatus.CREATED);
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

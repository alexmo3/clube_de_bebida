package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.dto.BeverageDTO;
import com.clubedebebida.backend.service.BeverageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garrafa")
public class BeverageController {
    private final BeverageService beverageService;

    @Autowired
    public BeverageController(BeverageService beverageService){
        this.beverageService = beverageService;
    }

    @GetMapping
    public ResponseEntity<Page<BeverageDTO>> findAll(
            @PageableDefault (size=10, page=0, sort="name")Pageable pageable
    )
    {
        Page<BeverageDTO> beveragesDTO = beverageService.findAll(pageable);

        return ResponseEntity.ok(beveragesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeverageDTO> findById(@PathVariable Long id){
        BeverageDTO beveragesDTO = beverageService.findById(id);

        return ResponseEntity.ok(beveragesDTO);
    }

    @PostMapping
    public ResponseEntity<BeverageDTO> save(@Valid @RequestBody BeverageDTO beverageDTO){
        BeverageDTO savedBeverage = beverageService.save(beverageDTO);

        return new ResponseEntity<>(savedBeverage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeverageDTO> update(@PathVariable Long id, @RequestBody BeverageDTO beverageDTO){
        BeverageDTO updatedBeverage = beverageService.update(id, beverageDTO);

        return ResponseEntity.ok(updatedBeverage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id){
        beverageService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

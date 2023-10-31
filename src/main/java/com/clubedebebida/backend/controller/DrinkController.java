package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.dto.DrinkDTO;
import com.clubedebebida.backend.service.DrinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drink")
public class DrinkController {
    private final DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService){
        this.drinkService = drinkService;
    }

    @GetMapping
    public ResponseEntity<Page<DrinkDTO>> findAll(
            @PageableDefault (size=10, page=0, sort="name")Pageable pageable
    )
    {
        Page<DrinkDTO> drinksDTO = drinkService.findAll(pageable);

        return ResponseEntity.ok(drinksDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkDTO> findById(@PathVariable Long id){
        DrinkDTO drinksDTO = drinkService.findById(id);

        return ResponseEntity.ok(drinksDTO);
    }

    @PostMapping
    public ResponseEntity<DrinkDTO> save(@Valid @RequestBody DrinkDTO drinkDTO){
        DrinkDTO savedDrink = drinkService.save(drinkDTO);

        return new ResponseEntity<>(savedDrink, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrinkDTO> update(@PathVariable Long id, @RequestBody DrinkDTO drinkDTO){
        DrinkDTO updatedDrink = drinkService.update(id, drinkDTO);

        return ResponseEntity.ok(updatedDrink);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        drinkService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

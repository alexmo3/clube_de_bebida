
package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.controller.request.ConsumptionRequest;
import com.clubedebebida.backend.controller.request.SaleRequest;
import com.clubedebebida.backend.dto.ConsumptionDTO;
import com.clubedebebida.backend.dto.SaleDTO;
import com.clubedebebida.backend.model.Sale;
import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
import com.clubedebebida.backend.service.ConsumptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumo")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping
    public ResponseEntity<Page<ConsumptionDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<ConsumptionDTO> consumptionsDTO = consumptionService.findAll(pageable);

        return ResponseEntity.ok(consumptionsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumptionDTO> findById(@PathVariable Long id) {
        ConsumptionDTO subscriptionsDTO = consumptionService.findById(id);

        return ResponseEntity.ok(subscriptionsDTO);
    }

    @PostMapping
    public ResponseEntity<ConsumptionDTO> save(@Valid @RequestBody ConsumptionRequest consumptionRequest) {
        Subscription subscription = new Subscription();
        subscription.setId(consumptionRequest.subscriptionId());

        User waiter = new User();
        waiter.setId(consumptionRequest.waiterId());

        ConsumptionDTO savedConsumption = consumptionService.save(consumptionService.requestToDTO(consumptionRequest));
        return new ResponseEntity<>(savedConsumption, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumptionDTO> update(@PathVariable Long id, @RequestBody ConsumptionDTO consumptionDTO) {
        ConsumptionDTO updatedConsumption = consumptionService.update(id, consumptionDTO);

        return ResponseEntity.ok(updatedConsumption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consumptionService.delete(id);

        return ResponseEntity.noContent().build();
    }

}

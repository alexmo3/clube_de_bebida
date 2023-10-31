package com.clubedebebida.backend.controller;

import com.clubedebebida.backend.controller.request.SubscriptionRequest;
import com.clubedebebida.backend.dto.SaleDTO;
import com.clubedebebida.backend.dto.SubscriptionDTO;
import com.clubedebebida.backend.model.Drink;
import com.clubedebebida.backend.model.User;
import com.clubedebebida.backend.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assinatura")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable
    ) {
        Page<SubscriptionDTO> subscriptionsDTO = subscriptionService.findAll(pageable);

        return ResponseEntity.ok(subscriptionsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> findById(@PathVariable Long id) {
        SubscriptionDTO subscriptionsDTO = subscriptionService.findById(id);

        return ResponseEntity.ok(subscriptionsDTO);
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> save(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        User user = new User();
        user.setId(subscriptionRequest.userId());

        Drink drink = new Drink();
        drink.setId(subscriptionRequest.drinkId());

        SubscriptionDTO savedSubscription = subscriptionService.save(subscriptionService.requestToDTO(subscriptionRequest));

        return new ResponseEntity<>(savedSubscription, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> update(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO updatedSubscription = subscriptionService.update(id, subscriptionDTO);

        return ResponseEntity.ok(updatedSubscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<SubscriptionDTO> getBalance(@PathVariable Long id) {
        SubscriptionDTO updatedSubscription = subscriptionService.getBalanceById(id);

        return ResponseEntity.ok(updatedSubscription);
    }

    @PutMapping("/{id}/baixar")
    public ResponseEntity<SubscriptionDTO> setBalanceDown(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO updatedSubscription = subscriptionService.setBalanceDown(id, subscriptionDTO.balance());

        return ResponseEntity.ok(updatedSubscription);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<SubscriptionDTO> setBalanceUp(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO updatedSubscription = subscriptionService.setBalanceUp(id, subscriptionDTO.balance());

        return ResponseEntity.ok(updatedSubscription);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SubscriptionDTO> setStatus(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO updatedSubscription = subscriptionService.setStatus(id, subscriptionDTO.status());

        return ResponseEntity.ok(updatedSubscription);
    }

}

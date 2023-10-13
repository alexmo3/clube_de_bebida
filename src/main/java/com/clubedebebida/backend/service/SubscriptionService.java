package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.SubscriptionController;
import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Subscription;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.SubscriptionDTO;
import com.clubedebebida.backend.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public Page<SubscriptionDTO> findAll(Pageable pageable){
        Page<Subscription> Subscriptions = subscriptionRepository.findAll(pageable);

        return Subscriptions.map(this::toDTO);
    }

    public int getBalanceById(Long id) {
        Subscription subscription = this.subscriptionRepository.findById(id).orElse(null);
        if (subscription != null) {
            return subscription.getBalance();
        } else {
            // Trate o caso em que nenhuma assinatura foi encontrada com o ID especificado.
            return 0; // Pode retornar um valor padrão ou lançar uma exceção, dependendo dos requisitos.
        }
    }

    public SubscriptionDTO findById(Long id){
        Subscription Subscription = subscriptionRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Assinatura não encontrada"));
        return toDTO(Subscription);
    }

    public SubscriptionDTO save(SubscriptionDTO subscriptionDTO){
        Subscription subscription = toEntity(subscriptionDTO);
        subscription = subscriptionRepository.save(subscription);

        return toDTO(subscription);
    }

    public SubscriptionDTO update(Long id, SubscriptionDTO SubscriptionDTO){
        try {
            Subscription subscription = subscriptionRepository.getReferenceById(id);
            subscription.setName(SubscriptionDTO.name());
            subscription.setCreatedAt(SubscriptionDTO.createdAt());
            subscription.setUpdatedAt(SubscriptionDTO.updatedAt());
            subscription = subscriptionRepository.save(subscription);
            return toDTO(subscription);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    public void delete(Long id){
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionDTO toDTO(Subscription subscription) {
        return new SubscriptionDTO(
                subscription.getId(),
                subscription.getName(),
                subscription.getDescription(),
                subscription.getUserId(),
                subscription.getBeverageId(),
                subscription.getSize(),
                subscription.getBalance(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }

    public void setBalance(Long id, int total){
        try {
            Subscription subscription = subscriptionRepository.getReferenceById(id);
            int newBalance = subscription.getBalance();
            newBalance = newBalance - total;

            subscription.setBalance(newBalance);
            subscription.setUpdatedAt(LocalDateTime.now());
            subscriptionRepository.save(subscription);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    private Subscription toEntity(SubscriptionDTO subscriptionDTO){
        return new Subscription(
                subscriptionDTO.id(),
                subscriptionDTO.name(),
                subscriptionDTO.description(),
                subscriptionDTO.userId(),
                subscriptionDTO.beverageId(),
                subscriptionDTO.size(),
                subscriptionDTO.balance(),
                subscriptionDTO.createdAt(),
                subscriptionDTO.updatedAt()
        );
    }
}




package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerInsufficientBalanceException;
import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.controller.request.SubscriptionRequest;
import com.clubedebebida.backend.model.Drink;
import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
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
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Page<SubscriptionDTO> findAll(Pageable pageable) {
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

    public SubscriptionDTO findById(Long id) {
        Subscription Subscription = subscriptionRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Assinatura não encontrada"));
        return toDTO(Subscription);
    }

    public SubscriptionDTO save(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = toEntity(subscriptionDTO);
        subscription = subscriptionRepository.save(subscription);

        return toDTO(subscription);
    }

    public SubscriptionDTO update(Long id, SubscriptionDTO subscriptionDTO) {
        try {
            Subscription subscription = subscriptionRepository.getReferenceById(id);
            subscription.setName(subscriptionDTO.name());
            subscription.setSize(subscriptionDTO.size());
            subscription.setDescription(subscriptionDTO.description());
            subscription.setUpdatedAt(LocalDateTime.now());
            subscription = subscriptionRepository.save(subscription);
            return toDTO(subscription);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    public void delete(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public SubscriptionDTO setBalance(Long id, int consumo) {
        try {
            Subscription subscription = subscriptionRepository.getReferenceById(id);
            if (consumo <= subscription.getBalance()) {
                int newBalance = consumo - subscription.getBalance();
                subscription.setBalance(newBalance);
                subscription.setUpdatedAt(LocalDateTime.now());
                subscription = subscriptionRepository.save(subscription);
                return toDTO(subscription);
            } else {
                throw new ControllerInsufficientBalanceException("Saldo insuficiente");
            }
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    public SubscriptionDTO setStatus(Long id, int status) {
        try {
            Subscription subscription = subscriptionRepository.getReferenceById(id);
            subscription.setStatus(status);
            subscription.setUpdatedAt(LocalDateTime.now());
            subscription = subscriptionRepository.save(subscription);
            return toDTO(subscription);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    private SubscriptionDTO toDTO(Subscription subscription) {
        return new SubscriptionDTO(
                subscription.getId(),
                subscription.getName(),
                subscription.getDescription(),
                subscription.getUser(),
                subscription.getDrink(),
                subscription.getSize(),
                subscription.getBalance(),
                subscription.getStatus(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }


    private Subscription toEntity(SubscriptionDTO subscriptionDTO) {
        return new Subscription(
                subscriptionDTO.id(),
                subscriptionDTO.name(),
                subscriptionDTO.description(),
                subscriptionDTO.user(),
                subscriptionDTO.drink(),
                subscriptionDTO.size(),
                subscriptionDTO.balance(),
                subscriptionDTO.status(),
                subscriptionDTO.createdAt(),
                subscriptionDTO.updatedAt()
        );
    }

    public SubscriptionDTO requestToDTO(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription();

        Drink drink = new Drink();
        drink.setId(subscriptionRequest.drinkId());
        subscription.setDrink(drink);

        User user = new User();
        user.setId(subscriptionRequest.userId());
        subscription.setUser(user);

        return new SubscriptionDTO(
                null,
                subscriptionRequest.name(),
                subscriptionRequest.description(),
                user,
                drink,
                subscriptionRequest.size(),
                subscriptionRequest.balance(),
                subscriptionRequest.status(),
                null,
                null
        );
    }
}




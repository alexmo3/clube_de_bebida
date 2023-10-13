package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Subscription;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.SubscriptionDTO;
import com.clubedebebida.backend.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class SubscriptionService {
    private final SubscriptionRepository SubscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository SubscriptionRepository){
        this.SubscriptionRepository = SubscriptionRepository;
    }

    public Page<SubscriptionDTO> findAll(Pageable pageable){
        Page<Subscription> Subscriptions = SubscriptionRepository.findAll(pageable);

        return Subscriptions.map(this::toDTO);
    }

    public SubscriptionDTO findById(Long id){
        Subscription Subscription = SubscriptionRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Assinatura não encontrada"));
        return toDTO(Subscription);
    }

    public SubscriptionDTO save(SubscriptionDTO SubscriptionDTO){
        Subscription Subscription = toEntity(SubscriptionDTO);
        Subscription = SubscriptionRepository.save(Subscription);

        return toDTO(Subscription);
    }

    public SubscriptionDTO update(Long id, SubscriptionDTO SubscriptionDTO){
        try {
            Subscription Subscription = SubscriptionRepository.getReferenceById(id);
            Subscription.setName(SubscriptionDTO.name());
            Subscription.setCreatedAt(SubscriptionDTO.createdAt());
            Subscription.setUpdatedAt(SubscriptionDTO.updatedAt());
            Subscription = SubscriptionRepository.save(Subscription);
            return toDTO(Subscription);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Assinatura não encontrada");
        }
    }

    public void delete(Long id){
        SubscriptionRepository.deleteById(id);
    }

    private SubscriptionDTO toDTO(Subscription Subscription) {
        return new SubscriptionDTO(
                Subscription.getId(),
                Subscription.getName(),
                Subscription.getDescription(),
                Subscription.getUserId(),
                Subscription.getBeverageId(),
                Subscription.getSize(),
                Subscription.getBalance(),
                Subscription.getCreatedAt(),
                Subscription.getUpdatedAt()
        );
    }

    private Subscription toEntity(SubscriptionDTO SubscriptionDTO){
        return new Subscription(
                SubscriptionDTO.id(),
                SubscriptionDTO.name(),
                SubscriptionDTO.description(),
                SubscriptionDTO.userId(),
                SubscriptionDTO.beverageId(),
                SubscriptionDTO.size(),
                SubscriptionDTO.balance(),
                SubscriptionDTO.createdAt(),
                SubscriptionDTO.updatedAt()
        );
    }
}




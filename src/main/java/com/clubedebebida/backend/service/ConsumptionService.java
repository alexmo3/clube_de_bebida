package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.controller.request.ConsumptionRequest;
import com.clubedebebida.backend.model.Consumption;
import com.clubedebebida.backend.model.Sale;
import com.clubedebebida.backend.model.Subscription;
import com.clubedebebida.backend.model.User;
import com.clubedebebida.backend.repository.ConsumptionRepository;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.ConsumptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service

public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    public Page<ConsumptionDTO> findAll(Pageable pageable) {
        Page<Consumption> consumptions = consumptionRepository.findAll(pageable);

        return consumptions.map(this::toDTO);
    }

    public ConsumptionDTO findById(Long id) {
        Consumption consumption = consumptionRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Consumo não encontrado"));
        return toDTO(consumption);
    }

    public ConsumptionDTO save(ConsumptionDTO consumptionDTO) {
        Consumption consumption = toEntity(consumptionDTO);
        consumption = consumptionRepository.save(consumption);

        return toDTO(consumption);
    }

    public ConsumptionDTO update(Long id, ConsumptionDTO consumptionDTO) {
        try {
            Consumption consumption = consumptionRepository.getReferenceById(id);
            if (consumptionDTO.price().compareTo(BigDecimal.ZERO) != 0)
                consumption.setPrice(consumptionDTO.price());

            if (consumptionDTO.total() != 0)
                consumption.setTotal(consumptionDTO.total());

            consumption.setUpdatedAt(LocalDateTime.now());
            consumption = consumptionRepository.save(consumption);
            return toDTO(consumption);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Consumação não encontrada");
        }
    }

    public void delete(Long id) {
        consumptionRepository.deleteById(id);
    }

    private ConsumptionDTO toDTO(Consumption consumption) {
        return new ConsumptionDTO(
                consumption.getId(),
                consumption.getSubscription(),
                consumption.getTotal(),
                consumption.getPrice(),
                consumption.getWaiter(),
                consumption.getCreatedAt(),
                consumption.getUpdatedAt()
        );
    }

    private Consumption toEntity(ConsumptionDTO consumptionDTO) {
        return new Consumption(
                consumptionDTO.id(),
                consumptionDTO.subscription(),
                consumptionDTO.total(),
                consumptionDTO.price(),
                consumptionDTO.waiter(),
                consumptionDTO.createdAt(),
                consumptionDTO.updatedAt()
        );
    }

    public ConsumptionDTO requestToDTO(ConsumptionRequest consumptionRequest) {
        Consumption consumption = new Consumption();

        Subscription subscription = new Subscription();
        subscription.setId(consumptionRequest.subscriptionId());
        consumption.setSubscription(subscription);

        User waiter = new User();
        waiter.setId(consumptionRequest.waiterId());

        consumption.setWaiter(waiter);
        return new ConsumptionDTO(
                null,
                subscription,
                consumptionRequest.total(),
                consumptionRequest.price(),
                waiter,
                null,
                null
        );
    }


}

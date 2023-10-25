package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Consumption;
import com.clubedebebida.backend.repository.ConsumptionRepository;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.ConsumptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service

public class ConsumptionService {
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public ConsumptionService(ConsumptionRepository consumptionRepository){
        this.consumptionRepository = consumptionRepository;
    }

    public Page<ConsumptionDTO> findAll(Pageable pageable){
        Page<Consumption> consumptions = consumptionRepository.findAll(pageable);

        return consumptions.map(this::toDTO);
    }

    public ConsumptionDTO findById(Long id){
        Consumption consumption = consumptionRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Consumo não encontrado"));
        return toDTO(consumption);
    }

    public ConsumptionDTO save(ConsumptionDTO consumptionDTO){
        Consumption consumption = toEntity(consumptionDTO);
        consumption = consumptionRepository.save(consumption);

        return toDTO(consumption);
    }

    public ConsumptionDTO update(Long id, ConsumptionDTO consumptionDTO){
        try {
            Consumption consumption = consumptionRepository.getReferenceById(id);
            consumption.setPrice(consumptionDTO.price());
            consumption.setTotal(consumptionDTO.total());
            consumption.setWaiter(consumptionDTO.waiter());
            consumption.setSubscription(consumptionDTO.subscription());
            consumption.setUpdatedAt(LocalDateTime.now());
            consumption = consumptionRepository.save(consumption);
            return toDTO(consumption);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Consumação não encontrada");
        }
    }

    public void delete(Long id){
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

    private Consumption toEntity(ConsumptionDTO consumptionDTO){
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




}

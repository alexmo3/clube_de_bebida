package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Drink;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.DrinkDTO;
import com.clubedebebida.backend.repository.DrinkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class DrinkService {
    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository){
        this.drinkRepository = drinkRepository;
    }

    public Page<DrinkDTO> findAll(Pageable pageable){
        Page<Drink> drinks = drinkRepository.findAll(pageable);

        return drinks.map(this::toDTO);
    }

    public DrinkDTO findById(Long id){
        Drink drink = drinkRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Bebida não encontrada"));
        return toDTO(drink);
    }

    public DrinkDTO save(DrinkDTO drinkDTO){
        Drink drink = toEntity(drinkDTO);
        drink = drinkRepository.save(drink);

        return toDTO(drink);
    }

    public DrinkDTO update(Long id, DrinkDTO drinkDTO){
        try {
            Drink drink = drinkRepository.getReferenceById(id);
            if (drinkDTO.name() != null)
                drink.setName(drinkDTO.name());

            if (drinkDTO.description() != null)
                drink.setDescription(drinkDTO.description());

            if (drinkDTO.type() != 0)
                drink.setType(drinkDTO.type());

            if (drinkDTO.unit() != null)
                drink.setUnit(drinkDTO.unit());

            if (drinkDTO.volume() != 0)
                drink.setVolume(drinkDTO.volume());

            if (drinkDTO.stock() != 0)
                drink.setStock(drinkDTO.stock());

            if (drinkDTO.minStock() != 0)
                drink.setMinStock(drinkDTO.minStock());

            drink.setUpdatedAt(LocalDateTime.now());
            drink = drinkRepository.save(drink);
            return toDTO(drink);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Bebida não encontrada");
        }
    }

    public void delete(Long id){
        drinkRepository.deleteById(id);
    }

    private DrinkDTO toDTO(Drink drink) {
        return new DrinkDTO(
                drink.getId(),
                drink.getName(),
                drink.getDescription(),
                drink.getType(),
                drink.getUnit(),
                drink.getVolume(),
                drink.getStock(),
                drink.getMinStock(),
                drink.getCreatedAt(),
                drink.getUpdatedAt()
        );
    }

    private Drink toEntity(DrinkDTO drinkDTO){
        return new Drink(
                drinkDTO.id(),
                drinkDTO.name(),
                drinkDTO.description(),
                drinkDTO.type(),
                drinkDTO.unit(),
                drinkDTO.volume(),
                drinkDTO.stock(),
                drinkDTO.minStock(),
                drinkDTO.createdAt(),
                drinkDTO.updatedAt()
        );
    }
}



package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Beverage;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.BeverageDTO;
import com.clubedebebida.backend.repository.BeverageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
//User getUserById(Long id);
public class BeverageService {
    private final BeverageRepository beverageRepository;

    @Autowired
    public BeverageService(BeverageRepository beverageRepository){
        this.beverageRepository = beverageRepository;
    }

    public Page<BeverageDTO> findAll(Pageable pageable){
        Page<Beverage> beverages = beverageRepository.findAll(pageable);

        return beverages.map(this::toDTO);
    }

    public BeverageDTO findById(Long id){
        Beverage beverage = beverageRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Bebida não encontrada"));
        return toDTO(beverage);
    }

    public BeverageDTO save(BeverageDTO beverageDTO){
        Beverage beverage = toEntity(beverageDTO);
        beverage = beverageRepository.save(beverage);

        return toDTO(beverage);
    }

    public BeverageDTO update(Long id, BeverageDTO beverageDTO){
        try {
            Beverage beverage = beverageRepository.getReferenceById(id);
            beverage.setName(beverageDTO.name());
            beverage.setDescription(beverageDTO.description());
            beverage.setType(beverageDTO.type());
            beverage.setUnit(beverageDTO.unit());
            beverage.setVolume(beverageDTO.volume());
            beverage.setStock(beverageDTO.stock());
            beverage.setMinStock(beverageDTO.minStock());
            beverage.setCreatedAt(beverageDTO.createdAt());
            beverage.setUpdatedAt(beverageDTO.updatedAt());
            beverage = beverageRepository.save(beverage);
            return toDTO(beverage);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Garrafa não encontrada");
        }
    }

    public void delete(Long id){
        beverageRepository.deleteById(id);
    }

    private BeverageDTO toDTO(Beverage beverage) {
        return new BeverageDTO(
                beverage.getId(),
                beverage.getName(),
                beverage.getDescription(),
                beverage.getType(),
                beverage.getUnit(),
                beverage.getVolume(),
                beverage.getStock(),
                beverage.getMinStock(),
                beverage.getCreatedAt(),
                beverage.getUpdatedAt()
        );
    }

    private Beverage toEntity(BeverageDTO beverageDTO){
        return new Beverage(
                beverageDTO.id(),
                beverageDTO.name(),
                beverageDTO.description(),
                beverageDTO.type(),
                beverageDTO.unit(),
                beverageDTO.volume(),
                beverageDTO.stock(),
                beverageDTO.minStock(),
                beverageDTO.createdAt(),
                beverageDTO.updatedAt()
        );
    }
}



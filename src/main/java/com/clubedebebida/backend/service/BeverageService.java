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
    private final BeverageRepository BeverageRepository;

    @Autowired
    public BeverageService(BeverageRepository BeverageRepository){
        this.BeverageRepository = BeverageRepository;
    }

    public Page<BeverageDTO> findAll(Pageable pageable){
        Page<Beverage> Beverages = BeverageRepository.findAll(pageable);

        return Beverages.map(this::toDTO);
    }

    public BeverageDTO findById(Long id){
        Beverage Beverage = BeverageRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Bebida não encontrada"));
        return toDTO(Beverage);
    }

    public BeverageDTO save(BeverageDTO BeverageDTO){
        Beverage Beverage = toEntity(BeverageDTO);
        Beverage = BeverageRepository.save(Beverage);

        return toDTO(Beverage);
    }

    public BeverageDTO update(Long id, BeverageDTO BeverageDTO){
        try {
            Beverage Beverage = BeverageRepository.getReferenceById(id);
            Beverage.setName(BeverageDTO.name());
            Beverage.setDescription(BeverageDTO.description());
            Beverage.setType(BeverageDTO.type());
            Beverage.setUnit(BeverageDTO.unit());
            Beverage.setVolume(BeverageDTO.volume());
            Beverage.setStock(BeverageDTO.stock());
            Beverage.setMinStock(BeverageDTO.minStock());
            Beverage.setCreatedAt(BeverageDTO.createdAt());
            Beverage.setUpdatedAt(BeverageDTO.updatedAt());
            Beverage = BeverageRepository.save(Beverage);
            return toDTO(Beverage);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Garrafa não encontrada");
        }
    }

    public void delete(Long id){
        BeverageRepository.deleteById(id);
    }

    private BeverageDTO toDTO(Beverage Beverage) {
        return new BeverageDTO(
                Beverage.getId(),
                Beverage.getName(),
                Beverage.getDescription(),
                Beverage.getType(),
                Beverage.getUnit(),
                Beverage.getVolume(),
                Beverage.getStock(),
                Beverage.getMinStock(),
                Beverage.getCreatedAt(),
                Beverage.getUpdatedAt()
        );
    }

    private Beverage toEntity(BeverageDTO BeverageDTO){
        return new Beverage(
                BeverageDTO.id(),
                BeverageDTO.name(),
                BeverageDTO.description(),
                BeverageDTO.type(),
                BeverageDTO.unit(),
                BeverageDTO.volume(),
                BeverageDTO.stock(),
                BeverageDTO.minStock(),
                BeverageDTO.createdAt(),
                BeverageDTO.updatedAt()
        );
    }
}



package com.clubedebebida.backend.service;

import com.clubedebebida.backend.controller.exception.ControllerNotFoundException;
import com.clubedebebida.backend.model.Sale;
import com.clubedebebida.backend.repository.SaleRepository;
import org.springframework.stereotype.Service;
import com.clubedebebida.backend.dto.SaleDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service

public class SaleService {
    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository){
        this.saleRepository = saleRepository;
    }

    public Page<SaleDTO> findAll(Pageable pageable){
        Page<Sale> sales = saleRepository.findAll(pageable);

        return sales.map(this::toDTO);
    }

    public SaleDTO findById(Long id){
        Sale sale = saleRepository.findById(id).orElseThrow(()-> new ControllerNotFoundException("Venda não encontrada"));
        return toDTO(sale);
    }

    public SaleDTO save(SaleDTO saleDTO){
        Sale sale = toEntity(saleDTO);
        sale = saleRepository.save(sale);

        return toDTO(sale);
    }

    public SaleDTO update(Long id, SaleDTO saleDTO){
        try {
            Sale sale = saleRepository.getReferenceById(id);
            sale.setPrice(saleDTO.price());
            sale.setWaiter(saleDTO.waiter());
            sale.setSubscriptionId(saleDTO.subscriptionId());
            sale.setUpdatedAt(LocalDateTime.now());
            sale = saleRepository.save(sale);
            return toDTO(sale);

        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Consumo não encontrada");
        }
    }

    public void delete(Long id){
        saleRepository.deleteById(id);
    }

    private SaleDTO toDTO(Sale sale) {
        return new SaleDTO(
                sale.getId(),
                sale.getSubscriptionId(),
                sale.getPrice(),
                sale.getWaiter(),
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );
    }

    private Sale toEntity(SaleDTO saleDTO){
        return new Sale(
                saleDTO.id(),
                saleDTO.subscriptionId(),
                saleDTO.price(),
                saleDTO.waiter(),
                saleDTO.createdAt(),
                saleDTO.updatedAt()
        );
    }
}

package org.testTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.testTask.dto.request.RequestProductDTO;
import org.testTask.dto.response.ResponseProductDTO;
import org.testTask.entity.Product;
import org.testTask.mapper.ProductMapper;
import org.testTask.repo.ProductRepo;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ResponseProductDTO addProduct(RequestProductDTO requestProductDTO) {
        try {
            if (requestProductDTO.getName() == null || requestProductDTO.getName().isBlank()) {
                throw new SQLIntegrityConstraintViolationException("Название продукта не может быть пустым.");
            }

            if (requestProductDTO.getPrice() == null || requestProductDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new SQLIntegrityConstraintViolationException("Цена продукта должна быть числом больше нуля.");
            }

            requestProductDTO.setName(requestProductDTO.getName().toLowerCase());

            Product newProduct = productMapper.toEntity(requestProductDTO);
            productRepo.save(newProduct);
            return productMapper.toDTO(newProduct);

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException("Ошибка при добавлении продукта: " + e.getMessage(), e);
        }
    }

}

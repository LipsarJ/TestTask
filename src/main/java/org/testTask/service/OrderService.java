package org.testTask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.testTask.dto.request.RequestOrderDTO;
import org.testTask.dto.response.ResponseOrderDTO;
import org.testTask.entity.Order;
import org.testTask.entity.Product;
import org.testTask.mapper.OrderMapper;
import org.testTask.repo.OrderRepo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseOrderDTO addNewOrder(RequestOrderDTO requestOrderDTO) {
        try {
            if (requestOrderDTO.getCostumerName().isEmpty()) {
                throw new IllegalArgumentException("Имя заказчика не может быть пустым");
            }
            requestOrderDTO.setCostumerName(requestOrderDTO.getCostumerName().toLowerCase());
            Order newOrder = orderMapper.toEntity(requestOrderDTO);
            BigDecimal orderSum = BigDecimal.ZERO;

            Set<Product> products = newOrder.getProducts();
            if (products == null || products.isEmpty()) {
                throw new SQLIntegrityConstraintViolationException("Заказ должен содержать хотя бы один продукт.");
            }

            for (Product product : products) {
                orderSum = orderSum.add(product.getPrice());
            }
            newOrder.setTotalPrice(orderSum);
            orderRepo.save(newOrder);
            return orderMapper.toDTO(newOrder);

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException("Ошибка при добавлении заказа: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при добавлении заказа: " + e.getMessage(), e);
        }

    }

    public List<ResponseOrderDTO> getAllOrdersForCostumer(String costumerName) {

        List<Order> allOrdersForCostumer = orderRepo.findAllByCostumerName(costumerName);
        List<ResponseOrderDTO> allOrdersForCostumerDTO = new ArrayList<>();
        for (Order order : allOrdersForCostumer) {
            allOrdersForCostumerDTO.add(orderMapper.toDTO(order));
        }
        return allOrdersForCostumerDTO;

    }

    public void exportOrdersToJson(String directoryPath) {
        try {
            String fileName = "exported_orders.json";

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean dirsCreated = directory.mkdirs();
                if (dirsCreated) {
                    System.out.println("Создана директория: " + directory.getAbsolutePath());
                }
            }

            File file = new File(directory, fileName);

            List<Order> orders = orderRepo.findAll();

            List<ResponseOrderDTO> ordersDTO = orders.stream()
                    .map(orderMapper::toDTO)
                    .toList();

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, ordersDTO);

            System.out.println("Заказы успешно экспортированы в файл: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте заказов: " + e.getMessage());
        }
    }
}
package org.testTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.testTask.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, UUID> {

    List<Order> findAllByCostumerName(String costumerName);

}

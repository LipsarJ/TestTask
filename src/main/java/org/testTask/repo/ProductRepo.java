package org.testTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.testTask.entity.Product;

import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    Product findByName(String name);
}

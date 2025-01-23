package org.testTask.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testTask.entity.Product;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDTO {

    private String costumerName;

    private Set<Product> products;
}

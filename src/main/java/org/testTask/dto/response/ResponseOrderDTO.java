package org.testTask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDTO {
    private UUID id;

    private String costumerName;

    private Set<ResponseProductDTO> products;

    private BigDecimal totalPrice;

}

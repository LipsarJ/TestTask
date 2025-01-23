package org.testTask.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductDTO {

    @NotBlank
    private String name;

    @NotBlank
    private BigDecimal price;

}

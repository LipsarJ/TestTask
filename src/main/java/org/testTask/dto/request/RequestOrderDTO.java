package org.testTask.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderDTO {

    private String costumerName;

    private Set<RequestProductDTO> products;
}

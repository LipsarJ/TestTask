package org.testTask.mapper;

import org.mapstruct.Mapper;
import org.testTask.dto.request.RequestProductDTO;
import org.testTask.dto.response.ResponseProductDTO;
import org.testTask.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(RequestProductDTO requestProductDTO);


    ResponseProductDTO toDTO(Product product);

}

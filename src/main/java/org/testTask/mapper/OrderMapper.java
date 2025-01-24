package org.testTask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.testTask.dto.request.RequestOrderDTO;
import org.testTask.dto.response.ResponseOrderDTO;
import org.testTask.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(RequestOrderDTO requestOrderDTO);

    @Mapping(target = "products", source = "order.products")
    ResponseOrderDTO toDTO(Order order);
}

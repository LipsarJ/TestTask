package org.testTask.mapper;

import org.mapstruct.Mapper;
import org.testTask.dto.request.RequestOrderDTO;
import org.testTask.dto.response.ResponseOrderDTO;
import org.testTask.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(RequestOrderDTO requestOrderDTO);

    ResponseOrderDTO toDTO(Order order);
}

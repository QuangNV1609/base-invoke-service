package com.quangnv.msb.utils.mapper;

import com.quangnv.msb.core.dto.auth.UserAuthResponseDTO;
import com.quangnv.msb.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserAuthResponseDTO fromEntityToAuthResponse(User in);

}
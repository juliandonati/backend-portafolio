package com.juliandonati.backendPortafolio.security.mapper;

import com.juliandonati.backendPortafolio.security.domain.Role;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.dto.RoleRequestDto;
import com.juliandonati.backendPortafolio.security.dto.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    public abstract Role toEntity(RoleRequestDto roleRequestDto);

    @Mapping(target = "users", source = "role.users", qualifiedByName = "mapUsersToString")
    public abstract RoleResponseDto toResponseDto(Role role);

    @Named("mapUsersToString")
    protected Set<String> mapUsersToString(Set<User> users){
        return users.stream().map(User::getUsername).collect(Collectors.toSet());
    }
}

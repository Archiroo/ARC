package com.archiiro.nda.Reponsitory;

import com.archiiro.nda.Domain.User;
import com.archiiro.nda.Dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select new com.archiiro.nda.Dto.UserDto(entity, true) From User entity Where (1=1)")
    List<UserDto> getAllDto();

    @Query("Select count(entity.id) From User entity Where entity.userName = ?1")
    Long checkUserName(String userName);

    @Query("Select new com.archiiro.nda.Dto.UserDto(entity, true) From User entity Where (1=1)")
    Page<UserDto> getPageDto(Pageable pageable);
}

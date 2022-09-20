package com.archiiro.nda.Service;

import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import com.archiiro.nda.Dto.UserDto;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {
    List<UserDto> getAllDto();

    ResponseObject getById(Long id);

    ResponseObject createDto(Long id, UserDto userDto);

    ResponseObject updateDto(Long id, UserDto userDto);

    ResponseObject deleteDto(Long id);

    Page<UserDto> getPageDto(int pageIndex, int pageSize);

    Page<UserDto> searchByPage(SearchDto searchDto);


}

package com.archiiro.nda.Service;

import com.archiiro.nda.Dto.AdministrativeUnitDto;
import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdministrativeUnitService {
    List<AdministrativeUnitDto> getAllDto();

    List<AdministrativeUnitDto> saveOrUpdateList(List<AdministrativeUnitDto> listAdministrative);

    ResponseObject findEntityById(Long id);


    ResponseObject createDto(Long id, AdministrativeUnitDto dto);

    ResponseObject updateDto(Long id, AdministrativeUnitDto dto);

    ResponseObject deleteDto(Long id);

    Page<AdministrativeUnitDto> getPageDto(int pageIndex, int pageSize);

    Page<AdministrativeUnitDto> searchByPage(SearchDto searchDto);
}

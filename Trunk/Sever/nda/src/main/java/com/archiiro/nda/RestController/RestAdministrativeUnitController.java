package com.archiiro.nda.RestController;

import com.archiiro.nda.Dto.AdministrativeUnitDto;
import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import com.archiiro.nda.Service.AdministrativeUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrative_unit")
public class RestAdministrativeUnitController {
    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    @RequestMapping(value = "/getAllDto", method = RequestMethod.GET)
    public List<AdministrativeUnitDto> getAllDto() {
        return this.administrativeUnitService.getAllDto();
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseObject getById(@PathVariable Long id) {
        return this.administrativeUnitService.findEntityById(id);
    }

    @RequestMapping(value = "/createDto", method = RequestMethod.POST)
    public ResponseObject createDto(@RequestBody AdministrativeUnitDto dto) {
        return this.administrativeUnitService.createDto(null, dto);
    }

    @RequestMapping(value = "/updateDto/{id}", method = RequestMethod.PUT)
    public ResponseObject updateDto(@PathVariable Long id, @RequestBody AdministrativeUnitDto dto) {
        return this.administrativeUnitService.updateDto(id, dto);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteDto(@PathVariable Long id) {
        return this.administrativeUnitService.deleteDto(id);
    }

    @RequestMapping(value = "/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public Page<AdministrativeUnitDto> getPageDto(@PathVariable Integer pageIndex, @PathVariable Integer pageSize) {
        return this.administrativeUnitService.getPageDto(pageIndex, pageSize);
    }

    @RequestMapping(value = "/searchByPage")
    public Page<AdministrativeUnitDto> searchByPage(@RequestBody SearchDto searchDto) {
        return this.administrativeUnitService.searchByPage(searchDto);
    }
}

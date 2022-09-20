package com.archiiro.nda.RestController;

import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import com.archiiro.nda.Dto.UserDto;
import com.archiiro.nda.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class RestUserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAllDto", method = RequestMethod.GET)
    public List<UserDto> getAllDto() {
        return this.userService.getAllDto();
    }

    @RequestMapping(value = "/getByID/{id}", method = RequestMethod.GET)
    public ResponseObject getById(@PathVariable Long id) {
        return this.userService.getById(id);
    }

    @RequestMapping(value = "/createDto", method = RequestMethod.POST)
    public ResponseObject createDto(@RequestBody UserDto userDto) {
        return this.userService.createDto(null, userDto);
    }

    @RequestMapping(value = "/updateDto/{id}", method = RequestMethod.PUT)
    public ResponseObject updateDto(@PathVariable Long id ,@RequestBody UserDto userDto) {
        return this.userService.updateDto(id, userDto);
    }

    @RequestMapping(value = "/deleteDto/{id}", method = RequestMethod.DELETE)
    public ResponseObject deleteDto(@PathVariable Long id) {
        return this.userService.deleteDto(id);
    }

    @RequestMapping(value = "/getPageDto/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public Page<UserDto> getPageDto(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return this.userService.getPageDto(pageIndex, pageSize);
    }

    @RequestMapping(value = "/serchByPage", method = RequestMethod.POST)
    public Page<UserDto> searchByPage(@RequestBody SearchDto searchDto) {
        return this.userService.searchByPage(searchDto);
    }

}

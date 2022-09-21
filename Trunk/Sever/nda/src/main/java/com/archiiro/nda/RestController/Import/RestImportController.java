package com.archiiro.nda.RestController.Import;

import com.archiiro.nda.Domain.AdministrativeUnit;
import com.archiiro.nda.Dto.AdministrativeUnitDto;
import com.archiiro.nda.Import.ImportDataFromExcel;
import com.archiiro.nda.Service.AdministrativeUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/import")
public class RestImportController {
    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    @RequestMapping(value = "/administrative", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> importAdministrative(@RequestParam("uploadfile") MultipartFile uploadfile)
        throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
        List<AdministrativeUnitDto> listAdministrative = ImportDataFromExcel.importAdministrative(bis);
        administrativeUnitService.saveOrUpdateList(listAdministrative);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

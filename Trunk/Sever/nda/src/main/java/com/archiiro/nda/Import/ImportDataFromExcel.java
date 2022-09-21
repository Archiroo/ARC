package com.archiiro.nda.Import;

import com.archiiro.nda.Dto.AdministrativeUnitDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ImportDataFromExcel {
    private static LocalDateTime dateCreate = LocalDateTime.now();
    private static Hashtable<String, Integer> hashAdministrative = new Hashtable<String, Integer>();

    public static boolean containsUnit(String code, List<AdministrativeUnitDto> ret) {
        for(AdministrativeUnitDto auDto : ret) {
            if (auDto != null && auDto.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    // PC import data Administrative start
    public static List<AdministrativeUnitDto> importAdministrative(InputStream is) {
        List<AdministrativeUnitDto> ret = new ArrayList<AdministrativeUnitDto>();
        hashAdministrative.put("provinceCode", 1); // Tỉnh
        hashAdministrative.put("provinceName", 2); // Mã tỉnh
        hashAdministrative.put("districtCode", 3); // Huyện
        hashAdministrative.put("districtName", 4); // Mã huyện
        hashAdministrative.put("wardCode", 5); // Xã
        hashAdministrative.put("wardName", 6); // Mã xã

        try {
            @SuppressWarnings("resource")
            Workbook workbook = new XSSFWorkbook(is);
            Sheet dataSheet = workbook.getSheetAt(0);
            int rowIndex = 1;
            int num = dataSheet.getLastRowNum();
            while (rowIndex <= num) {
                Row currentRow = dataSheet.getRow(rowIndex);
                Cell currentCell = null;
                if(currentRow != null) {
                    // Cấp Tỉnh
                    AdministrativeUnitDto unitProvince = new AdministrativeUnitDto();
                    Integer index = hashAdministrative.get("provinceCode");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String provinceCode = currentCell.getStringCellValue();
                            unitProvince.setDateCreate(dateCreate);
                            unitProvince.setCode(provinceCode);
                            unitProvince.setLevel(1); // Cấp tỉnh
                        }
                    }
                    index = hashAdministrative.get("provinceName");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String provinceName = currentCell.getStringCellValue();
                            unitProvince.setName(provinceName);
                        }
                    }
                    if(!containsUnit(unitProvince.getCode(), ret)) {
                        // Kiểm tra code import trùng
                        ret.add(unitProvince);
                    } else {
                        System.out.println("Có lỗi xảy ra khi import tại dòng: " + rowIndex);
                    }

                    // Cấp Huyện
                    AdministrativeUnitDto unitDistrict = new AdministrativeUnitDto();
                    index = hashAdministrative.get("districtCode");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String districtCode = currentCell.getStringCellValue();
                            unitDistrict.setDateCreate(dateCreate);
                            unitDistrict.setCode(districtCode);
                            unitDistrict.setLevel(2); // Cấp huyện
                            unitDistrict.setParent(unitProvince);
                        }
                    }
                    index = hashAdministrative.get("districtName");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String districtName = currentCell.getStringCellValue();
                            unitDistrict.setName(districtName);
                        }
                    }
                    if(!containsUnit(unitDistrict.getCode(), ret)) {
                        ret.add(unitDistrict);
                    } else {
                        System.out.println("Có lỗi xảy ra khi import tại dòng: " + rowIndex);
                    }

                    // Cấp Xã
                    AdministrativeUnitDto unitWard = new AdministrativeUnitDto();
                    index = hashAdministrative.get("wardCode");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String wardCode = currentCell.getStringCellValue();
                            unitWard.setDateCreate(dateCreate);
                            unitWard.setCode(wardCode);
                            unitWard.setLevel(3); // Cấp xã
                            unitWard.setParent(unitDistrict);
                        }
                    }
                    index = hashAdministrative.get("wardName");
                    if(index != null) {
                        currentCell = currentRow.getCell(index);
                        if(currentCell != null && currentCell.getStringCellValue() != null) {
                            String wardName = currentCell.getStringCellValue();
                            unitWard.setName(wardName);
                        }
                    }
                    if(!containsUnit(unitWard.getCode(), ret)) {
                        ret.add(unitWard);
                    } else {
                        System.out.println("Có lỗi xảy ra khi import tại dòng: " + rowIndex);
                    }
                }
                rowIndex++;
            }
            return ret;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

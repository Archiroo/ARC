package com.archiiro.nda.Reponsitory;

import com.archiiro.nda.Domain.AdministrativeUnit;
import com.archiiro.nda.Dto.AdministrativeUnitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeUnitRepository extends JpaRepository<AdministrativeUnit, Long> {
    @Query("Select new com.archiiro.nda.Dto.AdministrativeUnitDto(entity, true) From AdministrativeUnit entity where (1=1) ")
    List<AdministrativeUnitDto> getAllDto();

    @Query("Select count(entity.id) From AdministrativeUnit entity where entity.code = ?1")
    Long checkCode(String code);

    @Query("Select new com.archiiro.nda.Dto.AdministrativeUnitDto(entity, true) From AdministrativeUnit entity order by entity.id desc")
    Page<AdministrativeUnitDto> getPageDto(Pageable pageable);
}

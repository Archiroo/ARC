package com.archiiro.nda.Reponsitory;

import com.archiiro.nda.Domain.AdministrativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeUnitRepository extends JpaRepository<AdministrativeUnit, Long> {

}

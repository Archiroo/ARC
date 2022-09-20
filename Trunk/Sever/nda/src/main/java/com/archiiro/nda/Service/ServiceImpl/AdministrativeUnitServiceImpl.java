package com.archiiro.nda.Service.ServiceImpl;

import com.archiiro.nda.Domain.AdministrativeUnit;
import com.archiiro.nda.Dto.AdministrativeUnitDto;
import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import com.archiiro.nda.Reponsitory.AdministrativeUnitRepository;
import com.archiiro.nda.Service.AdministrativeUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdministrativeUnitServiceImpl implements AdministrativeUnitService {

    @Autowired
    private  AdministrativeUnitRepository administrativeUnitRepos;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<AdministrativeUnitDto> getAllDto() {
        return this.administrativeUnitRepos.getAllDto();
    }

    @Override
    public ResponseObject findEntityById(Long id) {
        AdministrativeUnit entity = null;
        if(id != null) {
            Optional<AdministrativeUnit> administrativeUnitOptional = this.administrativeUnitRepos.findById(id);
            if(administrativeUnitOptional.isPresent()) {
                entity = administrativeUnitOptional.get();
                return new ResponseObject("200", "Success", entity);
            }
            return new ResponseObject("404", "Can't not find Adminstrative witd ID: " + id, null);
        }
        return new ResponseObject("500", "Error" , null);
    }

    @Override
    public ResponseObject createDto(Long id, AdministrativeUnitDto dto) {
        return this.updateDto(null, dto);
    }

    @Override
    public ResponseObject updateDto(Long id, AdministrativeUnitDto dto) {
        AdministrativeUnit entity = null;
        LocalDateTime currentDate = LocalDateTime.now();
        if(id != null) {
            Optional<AdministrativeUnit> administrativeUnitOptional = this.administrativeUnitRepos.findById(id);
            if (administrativeUnitOptional.isPresent()) {
                entity = administrativeUnitOptional.get();
            }
        } else if(dto != null && dto.getId() != null) {
            Optional<AdministrativeUnit> administrativeUnitOptional = this.administrativeUnitRepos.findById(id);
            if (administrativeUnitOptional.isPresent()) {
                entity = administrativeUnitOptional.get();
            }
        }
        if(entity == null) {
            entity = new AdministrativeUnit();
            entity.setDateCreate(currentDate);
        }
        if(dto.getCode() != null) {
            entity.setCode(dto.getCode());
        }
        if(dto.getName() != null) {
            entity.setName(dto.getName());
        }
        // Parent
        if(dto.getParent() != null) {
            AdministrativeUnit parentEntity = null;
            if(dto.getParent().getId() != null) {
                Optional<AdministrativeUnit> administrativeUnitParentOptional = this.administrativeUnitRepos.findById(dto.getParent().getId());
                if(administrativeUnitParentOptional != null) {
                    parentEntity = administrativeUnitParentOptional.get();
                    entity.setParent(parentEntity);
                    if(parentEntity.getLevel() != null && parentEntity.getLevel() > 0) {
                        entity.setLevel(parentEntity.getLevel() + 1);
                    }
                }
            }
        } else {
            entity.setLevel(1); // Cấp tỉnh
            entity.setParent(null);
        }
        entity = this.administrativeUnitRepos.save(entity);
        if(entity != null) {
            return new ResponseObject("200", "Success", entity);
        }
        return new ResponseObject("500", "Error", null);
    }

    @Override
    public ResponseObject deleteDto(Long id) {
        AdministrativeUnit entity = null;
        if(id != null) {
            Optional<AdministrativeUnit> administrativeUnitOptional = this.administrativeUnitRepos.findById(id);
            if(administrativeUnitOptional.isPresent()) {
                entity = administrativeUnitOptional.get();
                this.administrativeUnitRepos.delete(entity); // Trường hợp xóa tỉnh
                return new ResponseObject("200", "Success", entity);
            }
            return new ResponseObject("404", "Can't not find Adminstrative witd ID: " + id, null);
        }
        return new ResponseObject("500", "Error" , null);
    }

    @Override
    public Page<AdministrativeUnitDto> getPageDto(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return this.administrativeUnitRepos.getPageDto(pageable);
    }

    @Override
    public Page<AdministrativeUnitDto> searchByPage(SearchDto searchDto) {
        if(searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
            int pageIndex = searchDto.getPageIndex();
            int pageSize = searchDto.getPageSize();
            if(pageIndex > 0) {
                pageIndex = pageIndex - 1;
            } else {
                pageIndex = 0;
            }
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            String sql = "Select new com.archiiro.nda.Dto.AdministrativeUnitDto(entity, true) From AdministrativeUnit entity where (1=1)";
            String sqlCount = "Select count(entity.id) From AdministrativeUnit entity Where (1=1) ";
            String whereClause = "";
            String orderBy = " Order by entity.dateCreate DESC";
            if(searchDto.getNameOrCode() != null) {
                whereClause += " And entity.code Like :nameOrCode Or entity.name Like :nameOrCode ";
            }
            sql += whereClause + orderBy;
            sqlCount = whereClause;
            Query q = manager.createQuery(sql, AdministrativeUnitDto.class);
            Query qCount = manager.createQuery(sqlCount);
            if(searchDto.getNameOrCode() != null) {
                q.setParameter("nameOrCode", "%" + searchDto.getNameOrCode() + "%");
                qCount.setParameter("nameOrCode", "%" + searchDto.getNameOrCode() + "%");
            }
            q.setFirstResult(pageIndex * pageSize);
            q.setMaxResults(pageSize);
            Long numberResult = (Long) qCount.getSingleResult();
            Page<AdministrativeUnitDto> page = new PageImpl<>(q.getResultList(), pageable, pageSize);
            return page;
        }
        return null;
    }
}

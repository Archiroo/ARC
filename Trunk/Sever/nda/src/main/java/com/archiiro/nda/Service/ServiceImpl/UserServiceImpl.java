package com.archiiro.nda.Service.ServiceImpl;

import com.archiiro.nda.Domain.User;
import com.archiiro.nda.Dto.FunctionDto.ResponseObject;
import com.archiiro.nda.Dto.FunctionDto.SearchDto;
import com.archiiro.nda.Dto.UserDto;
import com.archiiro.nda.Reponsitory.UserRepository;
import com.archiiro.nda.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepos;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserDto> getAllDto() {
        return this.getAllDto();
    }

    @Override
    public ResponseObject getById(Long id) {
        User entity = null;
        if(id != null) {
            Optional<User> userOptional = this.userRepos.findById(id);
            if(userOptional.isPresent()) {
                entity = userOptional.get();
                return new ResponseObject("200", "Success", entity);
            }
            else {
                return new ResponseObject("404", "Can't not find by ID: " + id, null);
            }
        }
        return new ResponseObject("400", "Not found ID", null);
    }

    @Override
    public ResponseObject createDto(Long id, UserDto userDto) {
        return this.updateDto(null, userDto);
    }

    @Override
    public ResponseObject updateDto(Long id, UserDto userDto) {
        User entity = null;
        LocalDateTime currentDate = LocalDateTime.now();
        if(id != null) {
            Optional<User> userOptional = this.userRepos.findById(id);
            if(userOptional.isPresent()) {
                entity = userOptional.get();
            }
        }
        else if(userDto != null && userDto.getId() != null) {
            Optional<User> userOptional = this.userRepos.findById(userDto.getId());
            if(userOptional.isPresent()) {
                entity = userOptional.get();
            }
        }
        if(entity == null) {
            entity = new User();
            entity.setDateCreate(currentDate);
        }
        if(userDto.getUserName() != null) {
            entity.setUserName(userDto.getUserName());
        }
        if(userDto.getPassword() != null) {
            entity.setPassword(userDto.getPassword());
        }
        if(entity.getActive() != null) {
            entity.setActive(userDto.getActive());
        }
        this.userRepos.save(entity);
        if(entity != null) {
            return new ResponseObject("200", "Success", entity);
        }
        return new ResponseObject("500", "Error", null);
    }

    @Override
    public ResponseObject deleteDto(Long id) {
        User entity = null;
        if(id != null) {
            Optional<User> userOptional = this.userRepos.findById(id);
            if(userOptional.isPresent()) {
                entity = userOptional.get();
                this.userRepos.delete(entity);
                return new ResponseObject("200", "Success", entity);
            }
            else {
                return new ResponseObject("404", "Can't not find by ID: " + id, null);
            }
        }
        return new ResponseObject("400", "Not found ID", null);
    }

    @Override
    public Page<UserDto> getPageDto(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        return this.userRepos.getPageDto(pageable);
    }

    @Override
    public Page<UserDto> searchByPage(SearchDto searchDto) {
        if(searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
            int pageIndex = searchDto.getPageIndex();
            int pageSize = searchDto.getPageSize();
            if(pageIndex > 0) {
                pageIndex--;
            } else {
                pageIndex = 0;
            }
            String sql = "Select new com.archiiro.nda.Dto.UserDto(entity, true) From User entity Where (1=1) ";
            String sqlCount = "Select count(entity.id) From User entity Where (1=1=)";
            String whereClause = "";
            String orderBy = " Order by entity.dateCreate DESC";
            if(searchDto.getNameOrCode() != null && StringUtils.hasText(searchDto.getNameOrCode())) {
                whereClause += " And entity.userName Like : ";
            }

            sql += whereClause + orderBy;
            sqlCount += whereClause;
            Query q = manager.createQuery(sql, UserDto.class);
            Query qCount = manager.createQuery(sqlCount);

            if(searchDto.getNameOrCode() != null && StringUtils.hasText(searchDto.getNameOrCode())) {
                q.setParameter("nameOrCode", "%" + searchDto.getNameOrCode() + "%");
            }
            q.setFirstResult((pageIndex)*pageSize);
            q.setMaxResults(pageSize);
            Long numberResult = (Long) qCount.getSingleResult();
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page<UserDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
            return page;
        }
        return null;
    }
}

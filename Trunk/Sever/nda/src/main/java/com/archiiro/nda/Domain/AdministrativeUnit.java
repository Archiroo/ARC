package com.archiiro.nda.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="tbl_administrative_unit")
public class AdministrativeUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="date_create")
    private LocalDateTime dateCreate;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="level")
    private Integer level;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private AdministrativeUnit parent; // Đơn vị cha

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AdministrativeUnit> childrenAdministrative; // Đơn vị con

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public AdministrativeUnit getParent() {
        return parent;
    }

    public void setParent(AdministrativeUnit parent) {
        this.parent = parent;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Set<AdministrativeUnit> getChildrenAdministrative() {
        return childrenAdministrative;
    }

    public void setChildrenAdministrative(Set<AdministrativeUnit> childrenAdministrative) {
        this.childrenAdministrative = childrenAdministrative;
    }
}

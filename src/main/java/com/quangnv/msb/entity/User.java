package com.quangnv.msb.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@QueryEntity
@Immutable
@Table(name = "users")
public class User extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code", length = 50)
    private String departmentCode;

    @Column(name = "hris_id", length = 50)
    private String hrisId;

    @Column(name = "user_role", length = 50)
    private String userRole;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "manager_email")
    private String managerEmail;
}
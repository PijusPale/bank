package com.danske.bank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@SuperBuilder
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Version
    private int versionNum;
    private String createdBy;
    private LocalDateTime creationDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}

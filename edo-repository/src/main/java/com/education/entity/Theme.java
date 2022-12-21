package com.education.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "theme")
public class Theme extends BaseEntity {

    /**
     * Название темы
     */
    @Column(name = "name")
    private String name;

    /**
     * Дата архивации
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Дата создания
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Номер темы (код)
     */
    @Column(name = "code")
    private String code;

    /*  связка с entity Theme */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Theme parentTheme;
}
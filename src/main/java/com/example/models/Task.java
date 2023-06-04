package com.example.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Task {

    public Task(Long userId, String site) {
        this.userId = userId;
        this.site = site;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "userId", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "site", nullable = false)
    private String site;
}

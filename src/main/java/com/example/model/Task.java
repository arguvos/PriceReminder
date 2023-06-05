package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "userId", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "site", nullable = false)
    private String url;

    @NotNull
    @Column(name = "xPath", nullable = false)
    private String xPath;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date = new Date();

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<Execution> executions;
}

package com.arguvos.pricereminder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "execution")
@NoArgsConstructor
public class Execution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "date", nullable = false)
    private Date date = new Date();
    @ManyToOne
    @JsonIgnore
    private Task task;

    public Execution(@Nonnull String value, @Nonnull Task task) {
        this.value = value;
        this.task = task;
    }
}

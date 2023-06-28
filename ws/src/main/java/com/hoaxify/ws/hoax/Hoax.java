package com.hoaxify.ws.hoax;

import java.util.Date;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Hoax {

    @Id
    @GeneratedValue
    private long id;
    @Size(min = 1, max = 1000)
    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

}
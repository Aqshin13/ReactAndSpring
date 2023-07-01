package com.hoaxify.ws.hoax;

import java.util.Date;

import com.hoaxify.ws.file.FileAttachment;
import com.hoaxify.ws.user.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Hoax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Size(min = 1, max = 1000)
    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "hoax", cascade = CascadeType.REMOVE)
    private FileAttachment fileAttachment;

}
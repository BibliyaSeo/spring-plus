package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String requestDetails;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false)
    private LocalDateTime logTime;

    public Log(String requestDetails, String status, LocalDateTime logTime) {
        this.requestDetails = requestDetails;
        this.status = status;
        this.logTime = logTime;
    }
}
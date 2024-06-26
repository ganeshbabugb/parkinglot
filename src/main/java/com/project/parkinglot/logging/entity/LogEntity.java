package com.project.parkinglot.logging.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Entity class named {@link LogEntity} representing logs of the parking lot.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PARKING_LOT_LOGS")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String endpoint;

    private String method;

    @Enumerated(EnumType.STRING)
    private HttpStatus status;

    private String userInfo;

    private String errorType;

    @Column(columnDefinition = "TEXT")
    private String response;

    private String operation;

    private LocalDateTime time;

}

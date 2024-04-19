package com.razortype.cyberproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "log_block")
@EntityListeners(AuditingEntityListener.class)
public class LogBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "accept_log")
    private boolean acceptLog;

    @Column(name = "warn_count")
    private int warnCount;

    @Column(name = "error_count")
    private int errorCount;

    @JsonBackReference
    @OneToOne(mappedBy = "logBlock", cascade = CascadeType.DETACH)
    private AttackJob attackJob;

    @OneToMany(mappedBy = "logBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Log> logs = new ArrayList<>();
}

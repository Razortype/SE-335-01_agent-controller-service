package com.razortype.cyberproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.razortype.cyberproject.core.enums.AttackType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attack_job")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AttackJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "attack_name")
    private String attackName;

    @Column(name = "attack_description")
    private String attackDescription;

    @Column(name = "attack_type")
    private AttackType attackType;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_crashed")
    private boolean crashed;

    @Column(name = "is_started")
    private boolean started;

    @Column(name = "executed_at")
    private LocalDateTime executeAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "start_executed_at")
    private LocalDateTime startExecutingAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User agent;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "log_block_id", referencedColumnName = "id", unique = true)
    private LogBlock logBlock;

}

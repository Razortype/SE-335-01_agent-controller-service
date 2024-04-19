package com.razortype.cyberproject.entity;

import com.razortype.cyberproject.core.enums.LogLevel;
import com.razortype.cyberproject.core.enums.LogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "log_text")
    private String logText;

    @Column(name = "log_type")
    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Column(name = "log_level")
    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    @ManyToOne
    @JoinColumn(name = "log_block_id", nullable = false)
    private LogBlock logBlock;
}

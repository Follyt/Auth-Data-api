package com.auth.aService.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "processing_log")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false) // updatable = false говорит о том, что колонка не учавствует в обновлении записи, даже если мы её указали. При создании новой записи - да, при обновлении существующей - нет.
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_processing_log_user")
    )
    private User user;

    private String input_text;
    private String output_text;
    private OffsetDateTime created_at;

}

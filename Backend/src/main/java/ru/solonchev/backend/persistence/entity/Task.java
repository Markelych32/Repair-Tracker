package ru.solonchev.backend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "payload")
    private String payload;

    @Column(name = "is_done")
    @Setter
    private Boolean isDone;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "finished_at")
    @Setter
    private OffsetDateTime finishedAt;

    @PrePersist
    private void prePersist() {
        var now = OffsetDateTime.now();
        if (Objects.isNull(this.createdAt)) {
            this.setCreatedAt(now);
        }
        if (Objects.isNull(this.isDone)) {
            this.setIsDone(false);
        }
    }

    public Task noteFinished() {
        var now = OffsetDateTime.now();
        this.isDone = true;
        this.finishedAt = now;
        return this;
    }
}

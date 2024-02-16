package io.antoniomayk.coses.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@Table(name = "tasks")
@EntityListeners(value = [AuditingEntityListener::class])
data class Task(
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @Id @GeneratedValue var id: UUID?,
    @Schema(example = "Send copy to editor for review") @Column(name = "title") var title: String,
    @Schema(example = "- See tracked changes in document") @Column(name = "content") var content: String,
    @Schema(example = "false") @Column(name = "completed") var completed: Boolean,
    @Schema(example = "0000-00-00T00:00:00.000Z") @Column(name = "completed_at") var completedAt: Instant?,
    @Schema(example = "0000-00-00T00:00:00.000Z") @CreatedDate var createdAt: Instant?,
    @Schema(example = "0000-00-00T00:00:00.000Z") @LastModifiedDate var modifiedAt: Instant?,
)

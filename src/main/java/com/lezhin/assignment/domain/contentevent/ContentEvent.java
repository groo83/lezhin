package com.lezhin.assignment.domain.contentevent;

import com.lezhin.assignment.common.entity.BaseEntity;
import com.lezhin.assignment.domain.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_event")
public class ContentEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Builder
    public ContentEvent(Content content, LocalDateTime startDate, LocalDateTime endDate) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
package com.lezhin.assignment.domain.contentview;

import com.lezhin.assignment.common.entity.BaseEntity;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.memeber.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_view_history")
public class ContentViewHistory extends BaseEntity {// 조회 이력,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long contentId;

    @Builder
    public ContentViewHistory(Long contentId, Long memberId) {
        this.contentId = contentId;
        this.memberId = memberId;
    }

    public static ContentViewHistory create(Content content) {
        return ContentViewHistory.builder()
                .contentId(content.getId())
                .build();
    }

    public static ContentViewHistory create(Member member, Content content) {
        return ContentViewHistory.builder()
                .contentId(content.getId())
                .memberId(member.getId())
                .build();
    }
}

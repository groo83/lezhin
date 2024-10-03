package com.lezhin.assignment.domain.purchase;


import com.lezhin.assignment.common.entity.BaseEntity;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.memeber.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "purchase")
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    private Long coin;

    @Builder
    public Purchase(Member member, Content content, Long coin) {
        this.member = member;
        this.content = content;
        this.coin = coin;
    }
}
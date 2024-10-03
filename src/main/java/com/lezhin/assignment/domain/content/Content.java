package com.lezhin.assignment.domain.content;

import com.lezhin.assignment.common.entity.BaseEntity;
import com.lezhin.assignment.domain.content.enums.RatingType;
import com.lezhin.assignment.domain.content.enums.PayType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;


@Entity
@Getter
@Table(name = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genres;

    private Long coin;

    @Enumerated(EnumType.STRING)
    private PayType payType; // 무료 여부

    @Enumerated(EnumType.STRING)
    private RatingType ratingType; // 등급

    @Builder
    public Content(String title, String genres, Long coin, PayType payType, RatingType ratingType) {
        this.title = title;
        this.genres = genres;
        this.coin = coin;
        this.payType = payType;
        this.ratingType = ratingType;
    }

}

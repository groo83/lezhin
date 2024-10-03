package com.lezhin.assignment.domain.content.dto;

import com.lezhin.assignment.common.dto.BaseResDto;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.enums.PayType;
import com.lezhin.assignment.domain.content.enums.RatingType;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContentResDto {

    private Long id;

    private String title;

    private String genres;

    private Long coin;

    private PayType payType; // 무료 여부

    private RatingType ratingType; // 등급

    public static ContentResDto fromEntity (Content content) {
        return ContentResDto.builder()
                .id(content.getId())
                .title(content.getTitle())
                .payType(content.getPayType())
                .ratingType(content.getRatingType())
                .coin(content.getCoin())
                .genres(content.getGenres())
                .build();
    }
}

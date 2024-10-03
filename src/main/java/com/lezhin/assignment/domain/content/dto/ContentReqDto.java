package com.lezhin.assignment.domain.content.dto;

import com.lezhin.assignment.common.valid.ValidEnum;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.enums.PayType;
import com.lezhin.assignment.domain.content.enums.RatingType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ContentReqDto {

    private String title;

    private String genres;

    private Long coin;

    @ValidEnum(target = RatingType.class, message = "GENERAL(일반) 또는 ADULT(성인)만 입력해주세요.")
    private RatingType ratingType;

    @ValidEnum(target = PayType.class, message = "FREE(무료) 또는 PAY(유료)만 입력해주세요.")
    private PayType payType;

    public Content toEntity() {
        return Content.builder()
                .title(title)
                .coin(coin)
                .ratingType(ratingType)
                .payType(payType)
                .build();
    }
}

package com.lezhin.assignment.domain.purchase.dto;

import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.purchase.Purchase;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseResDto {
    private Long contentId;
    private Long coin;

    public static PurchaseResDto fromEntity (Purchase purchase) {
        return PurchaseResDto.builder()
                .contentId(purchase.getContent().getId())
                .coin(purchase.getCoin())
                .build();
    }
}

package com.lezhin.assignment.domain.purchase.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseReqDto {

    private Long contentId;
}

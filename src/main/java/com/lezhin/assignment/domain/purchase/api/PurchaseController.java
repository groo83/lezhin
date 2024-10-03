package com.lezhin.assignment.domain.purchase.api;

import com.lezhin.assignment.common.dto.DataResponse;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.purchase.dto.PurchaseReqDto;
import com.lezhin.assignment.domain.purchase.dto.PurchaseResDto;
import com.lezhin.assignment.domain.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    // 작품 구매 API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<PurchaseResDto> purchaseContent(@RequestBody PurchaseReqDto purchaseReqDto) {
        PurchaseResDto purchaseContent =  purchaseService.purchaseContent(purchaseReqDto);
        return DataResponse.create(purchaseContent);
    }

    // 구매가 많은 인기작품 조회 API
    @GetMapping("/top")
    public DataResponse<List<ContentResDto>> getTopPurchasedContent(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        List<ContentResDto> topPurchasedContent = purchaseService.getTopPurchasedContent(page, size);
        return DataResponse.create(topPurchasedContent);
    }
}

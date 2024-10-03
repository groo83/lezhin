package com.lezhin.assignment.domain.purchase.service;


import com.lezhin.assignment.common.code.ErrorCode;
import com.lezhin.assignment.common.util.SecurityUtil;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.contentevent.ContentEvent;
import com.lezhin.assignment.domain.contentevent.repository.ContentEventRepository;
import com.lezhin.assignment.domain.memeber.Member;
import com.lezhin.assignment.domain.memeber.repository.MemberRepository;
import com.lezhin.assignment.domain.purchase.Purchase;
import com.lezhin.assignment.domain.purchase.dto.PurchaseReqDto;
import com.lezhin.assignment.domain.purchase.dto.PurchaseResDto;
import com.lezhin.assignment.domain.purchase.repository.PurchaseRepository;
import com.lezhin.assignment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    private final ContentEventRepository eventRepository;

    @Transactional
    public PurchaseResDto purchaseContent(PurchaseReqDto purchaseReqDto) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);

        Member member = memberRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Content content = contentRepository.findById(purchaseReqDto.getContentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));

        Long coin = content.getCoin();

        // 이벤트 중인 작품이라면 coin을 0으로 설정
        if( isActiveEvent(content.getId()) ) {
            coin = 0L;
        }

        Purchase purchase = Purchase.builder()
                .member(member)
                .content(content)
                .coin(coin)
                .build();

        purchaseRepository.save(purchase);

        return PurchaseResDto.fromEntity(purchase);
    }

    public List<ContentResDto> getTopPurchasedContent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Content> popularContents = purchaseRepository.findPopularContentByPurchaseCount(pageable);

        return popularContents.stream()
                .map(ContentResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean isActiveEvent(Long contentId) {
        List<ContentEvent> activeEvent = eventRepository.findActiveEventsByContentId(contentId, LocalDateTime.now());

        if(activeEvent.isEmpty()){
            return false;
        }

        return true;
    }
}
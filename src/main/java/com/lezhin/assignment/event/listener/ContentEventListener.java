package com.lezhin.assignment.event.listener;

import com.lezhin.assignment.common.code.ErrorCode;
import com.lezhin.assignment.common.util.SecurityUtil;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import com.lezhin.assignment.domain.contentview.repository.ContentViewHistoryRepository;
import com.lezhin.assignment.domain.contentview.service.ContentViewHistoryService;
import com.lezhin.assignment.domain.memeber.Member;
import com.lezhin.assignment.domain.memeber.repository.MemberRepository;
import com.lezhin.assignment.event.ContentEvent;
import com.lezhin.assignment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentEventListener {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    private final ContentViewHistoryService viewHistoryService;

    // 대용량 트래픽 고려 : 비동기로 처리하도록 설정
    @Async
    @EventListener
    public void saveViewHistory(ContentEvent.CreateContent event) {
        // 인증 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication : {}", authentication);

        Member member = findMemberEntity();
        Content content = findContentEntity(event.getContentId());
        ContentViewHistory viewHistory = null;
        if( member != null) {
            viewHistory = ContentViewHistory.create(member, content);
        } else {
            viewHistory = ContentViewHistory.create(content);

        }

        viewHistoryService.registerViewHistory(viewHistory);
    }

    @Async
    @EventListener
    public void deleteViewHistory(ContentEvent.DeleteContent event) {
        // 인증 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication : {}", authentication);

        findMemberEntityThrowException();

        viewHistoryService.deleteViewHistory(event.getContentId());
    }

    private Member findMemberEntity() {
        return memberRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElse(null);
    }

    private Member findMemberEntityThrowException() {
        return memberRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Content findContentEntity(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
    }
}

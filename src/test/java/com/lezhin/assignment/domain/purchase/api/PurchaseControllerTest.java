package com.lezhin.assignment.domain.purchase.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.enums.PayType;
import com.lezhin.assignment.domain.content.enums.RatingType;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.contentevent.ContentEvent;
import com.lezhin.assignment.domain.contentevent.repository.ContentEventRepository;
import com.lezhin.assignment.domain.purchase.Purchase;
import com.lezhin.assignment.domain.purchase.dto.PurchaseReqDto;
import com.lezhin.assignment.domain.purchase.repository.PurchaseRepository;
import com.lezhin.assignment.domain.purchase.service.PurchaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // JSON 직렬화를 위한 ObjectMapper

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ContentEventRepository contentEventRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private Content testContent;

    @Before
    public void setUp() {

        // 테스트용 컨텐츠 생성
        testContent = contentRepository.save(Content.builder()
                .title("Test Content")
                .payType(PayType.PAID) // 유료 컨텐츠로 설정
                .coin(3L)
                .genres("Fantasy")
                .ratingType(RatingType.GENERAL)
                .build());

    }

    @Test
    @WithMockUser(roles = "USER", username = "user1@example.com") // ROLE_USER 권한을 가진 인증된 사용자로 테스트 수행
    public void testPurchaseContentDuringFreeEvent() throws Exception {
        // case
        // 무료 이벤트 생성
        ContentEvent freeEvent = ContentEvent.builder()
                .content(testContent)
                .startDate(LocalDateTime.now().minusDays(1)) // 어제부터
                .endDate(LocalDateTime.now().plusDays(1)) // 내일까지 이벤트 진행
                .build();
        contentEventRepository.save(freeEvent);

        PurchaseReqDto member = PurchaseReqDto.builder()
                .contentId(testContent.getId())
                .build();

        // when
        // 컨텐츠 구매 API 호출 (무료 이벤트 중인 경우)
        mockMvc.perform(post("/api/purchase")
                    .content(objectMapper.writeValueAsString(member))
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // then
        // 실제로 Purchase 테이블에 구매 내역이 추가되었는지 검증
        Purchase purchase = purchaseRepository.findByContentId(testContent.getId());
        assertNotNull(purchase); // 구매 내역이 존재하는지 확인
        assertEquals(testContent.getId(), purchase.getContent().getId());
        assertEquals(0L, purchase.getCoin().longValue()); // 구매 금액이 0원으로 무료 구매했는지 확인

    }

    @Test
    @WithMockUser(roles = "USER", username = "user1@example.com") // ROLE_USER 권한을 가진 인증된 사용자로 테스트 수행
    public void testPurchaseContentAfterEvent() throws Exception {

        // case
        // 마감된 무료 이벤트 생성
        ContentEvent freeEvent = ContentEvent.builder()
                .content(testContent)
                .startDate(LocalDateTime.now().minusDays(2)) // 엊그제부터
                .endDate(LocalDateTime.now().minusDays(1)) // 어제까지 이벤트 진행
                .build();
        contentEventRepository.save(freeEvent);


        PurchaseReqDto member = PurchaseReqDto.builder()
                .contentId(testContent.getId())
                .build();

        // when
        // 컨텐츠 구매 API 호출 (무료 이벤트 마감된 경우)
        mockMvc.perform(post("/api/purchase")
                        .content(objectMapper.writeValueAsString(member))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // then
        // 실제로 Purchase 테이블에 구매 내역이 추가되었는지 검증
        Purchase purchase = purchaseRepository.findByContentId(testContent.getId());
        assertNotNull(purchase); // 구매 내역이 존재하는지 확인
        assertEquals(testContent.getId(), purchase.getContent().getId());
        assertEquals(3L, purchase.getCoin().longValue()); // 구매 금액이 3코인으로 유료 구매했는지 확인

    }
}
package com.lezhin.assignment.domain.content.api;

import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.enums.PayType;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.content.service.ContentService;
import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import com.lezhin.assignment.domain.contentview.dto.ContentViewHistoryDto;
import com.lezhin.assignment.domain.contentview.repository.ContentViewHistoryRepository;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.lezhin.assignment.domain.contentview.service.ContentViewHistoryService;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 실제 스프링 환경에서 테스트
@AutoConfigureMockMvc // MockMvc 자동 설정
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private ContentViewHistoryService contentViewHistoryService;

    @Autowired
    private ContentService contentService; // 실제 서비스 주입

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentViewHistoryRepository contentViewHistoryRepository;

    private Long contentId;
    private Content testContent;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        contentId = 1L; // 테스트에 사용할 Content ID 값 설정

        // 테스트용 컨텐츠 생성
        testContent = contentRepository.save(Content.builder()
                .title("Test Content")
                .payType(PayType.PAID)
                .coin(500L)
                .build());
    }

    @Test
    @WithMockUser(roles = "USER", username = "user1@example.com") // ROLE_USER 권한을 가진 인증된 사용자로 테스트 수행
    public void getContentViewHistory_ReturnsHistoryList() throws Exception {
        // case
        Long memberId = 1L;

        // when
        // 컨텐츠 조회 API 호출
        mockMvc.perform(get("/api/content/{contentId}", testContent.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // ContentViewHistory에 해당 조회 내역이 기록되었는지 확인
        List<ContentViewHistory> viewHistories = contentViewHistoryRepository.findByContentId(testContent.getId());

        // then
        assertEquals(testContent.getId(), viewHistories.get(0).getContentId()); // 조회된 이력의 ContentId가 맞는지 확인
        assertEquals(memberId, viewHistories.get(0).getMemberId()); // 조회된 이력의 MemberId가 맞는지 확인

    }

    @Test
    @WithMockUser(roles = "USER") // ROLE_USER 권한을 가진 인증된 사용자로 테스트 수행
    public void deleteContentAndHistory() throws Exception {
        // 조회 이력 추가
        mockMvc.perform(get("/api/content/{contentId}", contentId) // API 경로 수정 필요
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/content/{contentId}", contentId) // API 경로 수정 필요
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // When: 컨트롤러로 delete 요청을 보냄
        mockMvc.perform(delete("/api/content/{contentId}", contentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Then: 204 No Content 응답이 와야 함

        mockMvc.perform(get("/api/content/{contentId}/history", contentId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

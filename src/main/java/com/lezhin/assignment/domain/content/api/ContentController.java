package com.lezhin.assignment.domain.content.api;

import com.lezhin.assignment.domain.content.dto.ContentReqDto;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.content.service.ContentService;
import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import com.lezhin.assignment.domain.contentview.dto.ContentViewHistoryDto;
import com.lezhin.assignment.domain.contentview.service.ContentViewHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;
    private final ContentViewHistoryService viewHistoryService;

    @GetMapping("/{contentId}")
    public ContentResDto getContentById(@PathVariable("contentId") Long contentId) {
        return contentService.getContentById(contentId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ContentResDto registerContent(@Valid @RequestBody ContentReqDto reqDto) {
        ContentResDto content = contentService.registerContent(reqDto);

        return content;
    }

    @GetMapping("/{contentId}/history")
    public ResponseEntity<List<ContentViewHistoryDto>> getContentViewHistory(@PathVariable("contentId") Long contentId) {
        List<ContentViewHistoryDto> historyList = viewHistoryService.getContentViewHistory(contentId);
        return ResponseEntity.ok(historyList);
    }

    @DeleteMapping("/{contentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteContentAndHistory(@PathVariable("contentId") Long contentId) {
        contentService.deleteContentAndHistory(contentId);
        return ResponseEntity.noContent().build();
    }

}

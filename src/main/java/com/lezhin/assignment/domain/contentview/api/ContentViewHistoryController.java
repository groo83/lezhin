package com.lezhin.assignment.domain.contentview.api;

import com.lezhin.assignment.common.dto.DataResponse;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.contentview.service.ContentViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contentview")
@RequiredArgsConstructor
public class ContentViewHistoryController {

    private final ContentViewHistoryService viewHistoryService;

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public DataResponse<List<ContentResDto>> getTopViewedContent(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        List<ContentResDto> topViewedContent = viewHistoryService.getTopViewedContent(page, size);

        return DataResponse.create(topViewedContent);
    }
}

package com.lezhin.assignment.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentEvent {

    @Getter
    public static class CreateContent {

        private Long contentId;

        public static CreateContent of(Long contentId) {
            return new CreateContent(contentId);
        }

        public CreateContent(final Long contentId) {
            this.contentId = contentId;
        }
    }

    @Getter
    public static class DeleteContent {

        private Long contentId;

        public static DeleteContent of(Long contentId) {
            return new DeleteContent(contentId);
        }

        public DeleteContent(final Long contentId) {
            this.contentId = contentId;
        }
    }

}

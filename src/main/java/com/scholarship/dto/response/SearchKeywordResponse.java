package com.scholarship.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchKeywordResponse {
    private String keyword;
    private Long count;
}

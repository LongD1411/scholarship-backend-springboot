package com.scholarship.service;

import com.scholarship.dto.response.SearchKeywordResponse;

import java.util.List;

public interface SearchKeywordService {
    List<SearchKeywordResponse> getTop10SearchKeyword();
}

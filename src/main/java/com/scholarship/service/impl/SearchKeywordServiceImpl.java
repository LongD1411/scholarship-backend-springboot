package com.scholarship.service.impl;

import com.scholarship.dto.response.SearchKeywordResponse;
import com.scholarship.entities.SearchKeyword;
import com.scholarship.repositories.SearchKeywordRepository;
import com.scholarship.service.SearchKeywordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchKeywordServiceImpl implements SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;
    @Override
    public List<SearchKeywordResponse> getTop10SearchKeyword() {
       List<SearchKeyword> searchKeywords =  searchKeywordRepository.findTop10ByOrderByCountDesc();
       return searchKeywords.stream().map((item) ->SearchKeywordResponse.builder().keyword(item.getKeyword()).count(item.getCount()).build()).toList();
    }
}

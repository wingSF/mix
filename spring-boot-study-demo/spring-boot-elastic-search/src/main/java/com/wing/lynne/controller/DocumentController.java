package com.wing.lynne.controller;

import com.wing.lynne.model.DocumentPojo;
import com.wing.lynne.service.DocumentRepository;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @PutMapping("saveDocument")
    @ResponseBody
    public String saveDocument(@RequestBody DocumentPojo documentPojo) {

        documentRepository.save(documentPojo);

        return "{'status':'ok'}";
    }

    @GetMapping("searchDocument")
    @ResponseBody
    public List<DocumentPojo> searchDocument(String keyword, int page, int size) {


        // 构造分页类
        Pageable pageable = PageRequest.of(page, size);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword,"reportFormName", "reportFolderName", "formColumnName"))
                .withPageable(pageable)
                .build();

        Page<DocumentPojo> pageableResult = elasticsearchTemplate.queryForPage(searchQuery, DocumentPojo.class);

        return pageableResult.getContent();
    }
}

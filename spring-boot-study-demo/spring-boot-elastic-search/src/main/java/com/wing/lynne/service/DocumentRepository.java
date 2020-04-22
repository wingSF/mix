package com.wing.lynne.service;

import com.wing.lynne.model.DocumentPojo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service
public interface DocumentRepository extends ElasticsearchRepository<DocumentPojo, String> {
}

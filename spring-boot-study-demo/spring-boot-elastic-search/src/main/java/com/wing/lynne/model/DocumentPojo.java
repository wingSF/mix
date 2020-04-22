package com.wing.lynne.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Builder
@Document(indexName = "ks_form_info")
public class DocumentPojo {

    @Id
    private String reportFormName;
    private String reportFolderName;
    private List<String> formColumnName;

}

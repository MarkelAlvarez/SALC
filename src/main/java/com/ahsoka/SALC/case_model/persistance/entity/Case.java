package com.ahsoka.SALC.case_model.persistance.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@NoArgsConstructor
@Document(indexName = "cases", shards = 1, createIndex = false)
public class Case {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String caseNumber;
    @Field(type = FieldType.Text)
    private Court court;
    @Field(type = FieldType.Text)
    private Jurisdiction jurisdiction;
    @Field(type = FieldType.Text)
    private Region region;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd/MM/yyyy")
    private String date;
    @Field(type = FieldType.Nested)
    private List<String> tags;
    @Field(type = FieldType.Text)
    private String summary;
    @Field(type = FieldType.Keyword)
    private String filename;
    @Field(type = FieldType.Text)
    private String content;
}

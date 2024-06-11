package com.example.application.data.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GeminiRequest {
    @JsonProperty("contents")
    private List<Content> contents;
}
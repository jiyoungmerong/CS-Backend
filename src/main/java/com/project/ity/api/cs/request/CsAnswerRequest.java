package com.project.ity.api.cs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CsAnswerRequest {
    @NotNull(message = "CS 주제 ID는 필수입니다.")
    private Long csId;

    @NotBlank(message = "답변 내용은 필수입니다.")
    private String content;

}

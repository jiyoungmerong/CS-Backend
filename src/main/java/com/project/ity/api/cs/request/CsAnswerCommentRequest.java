package com.project.ity.api.cs.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CsAnswerCommentRequest {

    private Long id;

    @NotBlank(message = "내용을 작성해주세요.")
    private String comment;

}

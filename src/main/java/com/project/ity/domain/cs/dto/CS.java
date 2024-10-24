package com.project.ity.domain.cs.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String csSubject;

    private String regDt;

    @OneToMany(mappedBy = "cs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CsAnswer> answers = new ArrayList<>();

}

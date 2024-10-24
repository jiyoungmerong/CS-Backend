package com.project.ity.domain.cs.repository;

import com.project.ity.domain.cs.dto.CS;
import com.project.ity.domain.cs.dto.CsAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CsAnswerRepository extends JpaRepository<CsAnswer, Long> {
    @Query("SELECT c.csSubject FROM CS c WHERE c.id = :id")
    Optional<String> findCsSubjectById(@Param("id") Long id);

    List<CsAnswer> findByCs(CS cs);

    boolean existsByUserIdAndCsId(String userId, Long id);

    // todo 현재 h2 DB로 로컬에서 작업 중 > 이후 mysql 버전으로 쿼리 수정
    @Query(value = "SELECT a.user_id AS nickName, a.like_count AS likeCount " +
            "FROM cs_answer a " +
            "WHERE a.like_count > 0 " +
            "AND TO_CHAR(a.created_at, 'yyyyMMdd') = TO_CHAR(CURRENT_DATE, 'yyyyMMdd') " +
            "GROUP BY a.user_id " +
            "ORDER BY likeCount DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3ByOrderByLikeCountDesc();

}

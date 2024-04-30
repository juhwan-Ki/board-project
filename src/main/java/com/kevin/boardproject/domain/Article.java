package com.kevin.boardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        // 인덱스 걸기
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class) // Auditing에 대한 정보를 가져옴
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // setter가 있는 필드만 변경하도록 함
    // TODO: 추후 Builder를 사용해서 setter를 사용하지 않도록 할 것
    @Setter @Column(nullable = false) private String title; // 제목 -> not null
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문 -> not null
    @Setter private String hashtag; // 해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude // 순환참조 방지
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 중복을 허용하지 않음

    // Auditing을 통해 정보를 자동으로 넣어줌
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;  // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자


    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 객체를 생성하는 static 메소드
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // id를 가지고 동일객체 비교를 하도록 equal, hashcode 재정의
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

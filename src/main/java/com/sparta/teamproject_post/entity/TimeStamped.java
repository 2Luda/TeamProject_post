package com.sparta.teamproject_post.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Entity가 자동으로 Column으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트 해준다.
public class TimeStamped {

    // 생성 시간
    @CreatedDate
    public LocalDateTime createdAt;

    // 수정 시간
    @LastModifiedDate
    public LocalDateTime modifiedAt;
}

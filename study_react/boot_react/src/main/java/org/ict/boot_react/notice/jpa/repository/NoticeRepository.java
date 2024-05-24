package org.ict.boot_react.notice.jpa.repository;

import org.ict.boot_react.notice.jpa.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함
}

package com.LimitUpFriends.shinhan.repository;

import com.LimitUpFriends.shinhan.domain.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {

    // 특정 주식의 community 글 조회
    List<CommunityEntity> findByStock_Id(int stockId);

}

package com.lostway.eventnotificator.repository;

import com.lostway.eventnotificator.repository.entity.EventChangeStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventChangeStatusRepository extends JpaRepository<EventChangeStatusEntity, Long> {
}

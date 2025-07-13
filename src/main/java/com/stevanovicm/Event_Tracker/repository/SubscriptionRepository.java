package com.stevanovicm.Event_Tracker.repository;

import com.stevanovicm.Event_Tracker.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository <Subscription, Integer>{
  List<Subscription>findByEventId(Integer eventId);
  List<Subscription>findByUserId(Long userId);

  boolean existsByUserIdAndEventId(Long userId, Integer eventId);

}

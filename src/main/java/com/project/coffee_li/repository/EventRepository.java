package com.project.coffee_li.repository;

import com.project.coffee_li.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    EventEntity findByEventDate(@NonNull LocalDate eventDate);
}

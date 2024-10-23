package com.project.coffee_li.model;

import com.project.coffee_li.util.TemporalBaseUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "event")
public class EventEntity extends TemporalBaseUtil {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @ColumnDefault("gen_random_uuid()")
    private UUID id;

    private LocalDate eventDate;
    @Enumerated(EnumType.STRING)
    private EventTypeEnum eventType;

}

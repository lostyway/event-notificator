package com.lostway.eventnotificator.repository.entity;

import com.lostway.eventdtos.EventChangeKafkaMessage;
import com.lostway.eventdtos.FieldChange;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "changelogs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "name", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<String> name;

    @Column(name = "max_places", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<Integer> maxPlaces;

    @Column(name = "date", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<LocalDateTime> date;

    @Column(name = "cost", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<Double> cost;

    @Column(name = "duration", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<Integer> duration;

    @Column(name = "location_id", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private FieldChange<Long> locationId;

    public static ChangeLogEntity getDefaultEntityFromMessage(EventChangeKafkaMessage message) {
        return ChangeLogEntity.builder()
                .eventId(message.getEventId())
                .name(message.getName())
                .maxPlaces(message.getMaxPlaces())
                .date(message.getDate())
                .cost(message.getCost())
                .duration(message.getDuration())
                .locationId(message.getLocationId())
                .build();
    }
}

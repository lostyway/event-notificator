package com.lostway.eventnotificator.mapper;

import com.lostway.eventnotificator.controller.dto.EventChangeNotification;
import com.lostway.eventnotificator.repository.ChangeLogEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChangeLogMapper {
    EventChangeNotification toEventChangeNotification(ChangeLogEntity changeLogEntity);

    List<EventChangeNotification> toEventChangeNotifications(List<ChangeLogEntity> changeLogEntities);
}

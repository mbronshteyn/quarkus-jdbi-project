package com.mbronshteyn.quarkus.util;

import com.mbronshteyn.quarkus.entity.Fruit;
import com.mbronshteyn.quarkus.entity.QueueItem;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class QueueResponseObject {
    String msg;
    QueueItem queueItem;
    List<QueueItem> queueItemList;
}

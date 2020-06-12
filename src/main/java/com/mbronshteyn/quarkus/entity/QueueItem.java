package com.mbronshteyn.quarkus.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RegisterForReflection
public class QueueItem {
    public String id;
    public Instant timeCreated;
    public String value;
}

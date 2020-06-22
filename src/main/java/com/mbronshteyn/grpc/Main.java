package com.mbronshteyn.grpc;

import com.google.protobuf.util.JsonFormat;
import com.logrhythm.core.api.v1.grpc.EmailAudience;

public class Main {
    public static void main(String... args) throws Exception {
        EmailAudience audience = EmailAudience.newBuilder()
                .setAddress("foo@example.com")
                .setName("Foo")
                .build();
        System.out.println("Here is a proto3 object rendered using toString():");
        System.out.println(audience);
        System.out.println();
        System.out.println("Here is the same object rendered using JsonFormat:");
        System.out.println(JsonFormat.printer().print(audience));
    }
}

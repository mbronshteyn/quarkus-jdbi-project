package com.mbronshteyn.expbackoff;

import com.google.common.flogger.FluentLogger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChannelService {

    private final FluentLogger logger = FluentLogger.forEnclosingClass();

    public String sendOnChannel(String msg) throws ChannelServiceException {
        logger.atInfo().log("Calling with %s", msg);
        return msg;
    }
}

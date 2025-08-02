package com.karmangames.solitaire.xenvironment.components;

import com.karmangames.solitaire.sysvshm.SysVSHMConnectionHandler;
import com.karmangames.solitaire.sysvshm.SysVSHMRequestHandler;
import com.karmangames.solitaire.sysvshm.SysVSharedMemory;
import com.karmangames.solitaire.xconnector.UnixSocketConfig;
import com.karmangames.solitaire.xconnector.XConnectorEpoll;
import com.karmangames.solitaire.xenvironment.EnvironmentComponent;
import com.karmangames.solitaire.xserver.SHMSegmentManager;
import com.karmangames.solitaire.xserver.XServer;

public class SysVSharedMemoryComponent extends EnvironmentComponent {
    private XConnectorEpoll connector;
    public final UnixSocketConfig socketConfig;
    private SysVSharedMemory sysVSharedMemory;
    private final XServer xServer;

    public SysVSharedMemoryComponent(XServer xServer, UnixSocketConfig socketConfig) {
        this.xServer = xServer;
        this.socketConfig = socketConfig;
    }

    @Override
    public void start() {
        if (connector != null) return;
        sysVSharedMemory = new SysVSharedMemory();
        connector = new XConnectorEpoll(socketConfig, new SysVSHMConnectionHandler(sysVSharedMemory), new SysVSHMRequestHandler());
        connector.start();

        xServer.setSHMSegmentManager(new SHMSegmentManager(sysVSharedMemory));
    }

    @Override
    public void stop() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }

        sysVSharedMemory.deleteAll();
    }
}

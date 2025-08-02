package com.karmangames.solitaire.xenvironment.components;

import com.karmangames.solitaire.xenvironment.EnvironmentComponent;
import com.karmangames.solitaire.xconnector.XConnectorEpoll;
import com.karmangames.solitaire.xconnector.UnixSocketConfig;
import com.karmangames.solitaire.xserver.XClientConnectionHandler;
import com.karmangames.solitaire.xserver.XClientRequestHandler;
import com.karmangames.solitaire.xserver.XServer;

public class XServerComponent extends EnvironmentComponent {
    private XConnectorEpoll connector;
    private final XServer xServer;
    private final UnixSocketConfig socketConfig;

    public XServerComponent(XServer xServer, UnixSocketConfig socketConfig) {
        this.xServer = xServer;
        this.socketConfig = socketConfig;
    }

    @Override
    public void start() {
        if (connector != null) return;
        connector = new XConnectorEpoll(socketConfig, new XClientConnectionHandler(xServer), new XClientRequestHandler());
        connector.setInitialInputBufferCapacity(262144);
        connector.setCanReceiveAncillaryMessages(true);
        connector.start();
    }

    @Override
    public void stop() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }

    public XServer getXServer() {
        return xServer;
    }
}

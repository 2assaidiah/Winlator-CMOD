package com.karmangames.solitaire.xenvironment.components;

import com.karmangames.solitaire.alsaserver.ALSAClientConnectionHandler;
import com.karmangames.solitaire.alsaserver.ALSARequestHandler;
import com.karmangames.solitaire.xconnector.UnixSocketConfig;
import com.karmangames.solitaire.xconnector.XConnectorEpoll;
import com.karmangames.solitaire.xenvironment.EnvironmentComponent;

public class ALSAServerComponent extends EnvironmentComponent {
    private XConnectorEpoll connector;
    private final UnixSocketConfig socketConfig;

    public ALSAServerComponent(UnixSocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

    @Override
    public void start() {
        if (connector != null) return;
        connector = new XConnectorEpoll(socketConfig, new ALSAClientConnectionHandler(), new ALSARequestHandler());
        connector.setMultithreadedClients(true);
        connector.start();
    }

    @Override
    public void stop() {
        if (connector != null) {
            connector.stop();
            connector = null;
        }
    }
}

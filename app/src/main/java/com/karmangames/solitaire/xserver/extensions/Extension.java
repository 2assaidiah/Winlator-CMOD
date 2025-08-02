package com.karmangames.solitaire.xserver.extensions;

import com.karmangames.solitaire.xconnector.XInputStream;
import com.karmangames.solitaire.xconnector.XOutputStream;
import com.karmangames.solitaire.xserver.XClient;
import com.karmangames.solitaire.xserver.errors.XRequestError;

import java.io.IOException;

public interface Extension {
    String getName();

    byte getMajorOpcode();

    byte getFirstErrorId();

    byte getFirstEventId();

    void handleRequest(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError;
}

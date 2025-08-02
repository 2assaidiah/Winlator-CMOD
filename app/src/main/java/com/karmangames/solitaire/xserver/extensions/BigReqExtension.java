package com.karmangames.solitaire.xserver.extensions;

import static com.karmangames.solitaire.xserver.XClientRequestHandler.RESPONSE_CODE_SUCCESS;

import com.karmangames.solitaire.xconnector.XInputStream;
import com.karmangames.solitaire.xconnector.XOutputStream;
import com.karmangames.solitaire.xconnector.XStreamLock;
import com.karmangames.solitaire.xserver.XClient;

import java.io.IOException;

public class BigReqExtension implements Extension {
    public static final byte MAJOR_OPCODE = -100;
    private static final int MAX_REQUEST_LENGTH = 4194303;

    @Override
    public String getName() {
        return "BIG-REQUESTS";
    }

    @Override
    public byte getMajorOpcode() {
        return MAJOR_OPCODE;
    }

    @Override
    public byte getFirstErrorId() {
        return 0;
    }

    @Override
    public byte getFirstEventId() {
        return 0;
    }

    @Override
    public void handleRequest(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException {
        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte((byte)0);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(0);
            outputStream.writeInt(MAX_REQUEST_LENGTH);
            outputStream.writePad(20);
        }
    }
}

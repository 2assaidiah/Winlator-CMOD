package com.karmangames.solitaire.xserver.requests;

import static com.karmangames.solitaire.xserver.XClientRequestHandler.RESPONSE_CODE_SUCCESS;

import com.karmangames.solitaire.xconnector.XInputStream;
import com.karmangames.solitaire.xconnector.XOutputStream;
import com.karmangames.solitaire.xconnector.XStreamLock;
import com.karmangames.solitaire.xserver.XClient;
import com.karmangames.solitaire.xserver.errors.XRequestError;
import com.karmangames.solitaire.xserver.extensions.Extension;

import java.io.IOException;

public abstract class ExtensionRequests {
    public static void queryExtension(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        short length = inputStream.readShort();
        inputStream.skip(2);
        String name = inputStream.readString8(length);
        Extension extension = client.xServer.getExtensionByName(name);
        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte((byte)0);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(0);

            if (extension != null) {
                outputStream.writeByte((byte)1);
                outputStream.writeByte(extension.getMajorOpcode());
                outputStream.writeByte(extension.getFirstEventId());
                outputStream.writeByte(extension.getFirstErrorId());
                outputStream.writePad(20);
            }
            else {
                outputStream.writeByte((byte)0);
                outputStream.writePad(23);
            }
        }
    }
}

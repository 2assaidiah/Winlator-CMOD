package com.karmangames.solitaire.xserver.requests;

import static com.karmangames.solitaire.xserver.Keyboard.KEYSYMS_PER_KEYCODE;
import static com.karmangames.solitaire.xserver.XClientRequestHandler.RESPONSE_CODE_SUCCESS;

import com.karmangames.solitaire.xconnector.XInputStream;
import com.karmangames.solitaire.xconnector.XOutputStream;
import com.karmangames.solitaire.xconnector.XStreamLock;
import com.karmangames.solitaire.xserver.Keyboard;
import com.karmangames.solitaire.xserver.XClient;
import com.karmangames.solitaire.xserver.errors.XRequestError;

import java.io.IOException;

public abstract class KeyboardRequests {
    public static void getKeyboardMapping(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        byte firstKeycode = inputStream.readByte();
        int count = inputStream.readUnsignedByte();
        inputStream.skip(2);

        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte(KEYSYMS_PER_KEYCODE);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(count);
            outputStream.writePad(24);

            int i = firstKeycode - Keyboard.MIN_KEYCODE;
            while (count != 0) {
                outputStream.writeInt(client.xServer.keyboard.keysyms[i]);
                count--;
                i++;
            }
        }
    }

    public static void getModifierMapping(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte((byte)1);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(2);
            outputStream.writePad(24);
            outputStream.writePad(8);
        }
    }
}
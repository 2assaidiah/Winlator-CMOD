package com.karmangames.solitaire.xserver.requests;

import static com.karmangames.solitaire.xserver.XClientRequestHandler.RESPONSE_CODE_SUCCESS;

import com.karmangames.solitaire.xconnector.XInputStream;
import com.karmangames.solitaire.xconnector.XOutputStream;
import com.karmangames.solitaire.xconnector.XStreamLock;
import com.karmangames.solitaire.xserver.Atom;
import com.karmangames.solitaire.xserver.Window;
import com.karmangames.solitaire.xserver.XClient;
import com.karmangames.solitaire.xserver.errors.BadAtom;
import com.karmangames.solitaire.xserver.errors.BadWindow;
import com.karmangames.solitaire.xserver.errors.XRequestError;

import java.io.IOException;

public abstract class SelectionRequests {
    public static void setSelectionOwner(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        int windowId = inputStream.readInt();
        int atom = inputStream.readInt();
        int timestamp = inputStream.readInt();

        Window owner = client.xServer.windowManager.getWindow(windowId);
        if (owner == null) throw new BadWindow(windowId);
        if (!Atom.isValid(atom)) throw new BadAtom(atom);

        client.xServer.selectionManager.setSelection(atom, owner, client, timestamp);
    }

    public static void getSelectionOwner(XClient client, XInputStream inputStream, XOutputStream outputStream) throws IOException, XRequestError {
        int atom = inputStream.readInt();
        if (!Atom.isValid(atom)) throw new BadAtom(atom);
        Window owner = client.xServer.selectionManager.getSelection(atom).owner;

        try (XStreamLock lock = outputStream.lock()) {
            outputStream.writeByte(RESPONSE_CODE_SUCCESS);
            outputStream.writeByte((byte)0);
            outputStream.writeShort(client.getSequenceNumber());
            outputStream.writeInt(0);
            outputStream.writeInt(owner != null ? owner.id : 0);
            outputStream.writePad(20);
        }
    }
}

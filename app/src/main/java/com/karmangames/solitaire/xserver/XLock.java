package com.karmangames.solitaire.xserver;

public interface XLock extends AutoCloseable {
    @Override
    void close();
}

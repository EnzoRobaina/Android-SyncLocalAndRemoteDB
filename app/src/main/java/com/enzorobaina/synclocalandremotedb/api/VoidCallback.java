package com.enzorobaina.synclocalandremotedb.api;

public interface VoidCallback {
    void onSuccess();
    void onFail();
    void always();
}

package com.enzorobaina.synclocalandremotedb.api;

import java.util.List;

public interface ListCallback {
    void onSuccess(List<Long> value);
    void onFail();
}

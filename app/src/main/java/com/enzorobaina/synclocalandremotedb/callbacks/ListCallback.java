package com.enzorobaina.synclocalandremotedb.callbacks;

import java.util.List;

public interface ListCallback {
    void onSuccess(List<Long> value);
    void onFail();
    void always();
}

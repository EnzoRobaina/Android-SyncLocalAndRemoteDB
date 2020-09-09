package com.enzorobaina.synclocalandremotedb.callbacks;

import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

public interface CharacterListCallback {
    void onSuccess(List<Character> result);
}

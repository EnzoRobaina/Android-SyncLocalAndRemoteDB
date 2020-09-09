package com.enzorobaina.synclocalandremotedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PerformSyncResponse {
    @SerializedName("to_insert")
    public List<Character> toInsert;
    @SerializedName("to_update")
    public List<Character> toUpdate;
}

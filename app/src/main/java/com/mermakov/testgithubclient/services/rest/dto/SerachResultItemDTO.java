package com.mermakov.testgithubclient.services.rest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class SerachResultItemDTO {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }
}

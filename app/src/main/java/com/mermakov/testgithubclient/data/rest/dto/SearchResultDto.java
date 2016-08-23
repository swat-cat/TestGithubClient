package com.mermakov.testgithubclient.data.rest.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class SearchResultDto {
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("items")
    @Expose
    private List<SerachResultItemDTO> items = new ArrayList<SerachResultItemDTO>();


    public List<SerachResultItemDTO> getItems() {
        return items;
    }
}

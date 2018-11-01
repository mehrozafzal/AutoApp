
package com.example.auto1.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class ManufacturerResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("totalPageCount")
    @Expose
    private Integer totalPageCount;
    @SerializedName("wkda")
    @Expose
    private LinkedHashMap<String, String> wkdaManufacturerMap;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public LinkedHashMap<String, String> getWkdaMap() {
        return wkdaManufacturerMap;
    }

    public void setWkdaMap(LinkedHashMap<String, String> wkdaManufacturerMap) {
        this.wkdaManufacturerMap = wkdaManufacturerMap;
    }
}

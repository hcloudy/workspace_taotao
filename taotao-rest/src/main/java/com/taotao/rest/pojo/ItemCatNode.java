package com.taotao.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 商品分类pojo
 */
public class ItemCatNode {

    @JsonProperty("u")
    private String url;
    @JsonProperty("n")
    private String name;
    @JsonProperty("i")
    private List items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}

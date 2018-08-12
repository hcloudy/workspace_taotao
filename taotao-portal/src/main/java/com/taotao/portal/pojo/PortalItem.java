package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class PortalItem extends TbItem {

    public String[] getImages() {
        String images= this.getImage();
        if (!images.equals("") && images != null) {
            String[] imags = images.split(",");
            return imags;
        }
        else {
            return null;
        }
    }
}

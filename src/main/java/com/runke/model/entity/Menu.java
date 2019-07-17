package com.runke.model.entity;

import lombok.Data;
@Data
public class Menu {
    private String menuId;
    private String menuCode;
    private String pageUrl;
    /**是否是一级菜单*/
    private Boolean isRoot;
    private String parentMenuId;
    private Boolean deleted;
}

package com.vertis.core.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NavItem {
    private String title;
    private String link;

    private List<NavItem> subMenu = new ArrayList<>();
}

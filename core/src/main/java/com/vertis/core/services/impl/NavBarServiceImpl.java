package com.vertis.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.vertis.core.models.NavItem;
import com.vertis.core.services.NavBarService;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component(service = NavBarService.class, immediate = true)
public class NavBarServiceImpl implements NavBarService {
    @Override
    public List<NavItem> getRootNavItems(final Resource resource) {
        final List<NavItem> menuModelList = new ArrayList<>();
        Optional.ofNullable(resource).ifPresent(rootResource -> rootResource.listChildren().forEachRemaining(child -> Optional.ofNullable(child.getChild(JcrConstants.JCR_CONTENT))
                .map(Resource::getValueMap)
                .ifPresent(properties -> {
                    if (!properties.get("hideInNavigation", false)) {
                        final NavItem menuModel = new NavItem();
                        menuModel.setTitle(child.getName());
                        menuModel.setLink(child.getPath());
                        if (!properties.get("hideAllPagesInSubNavigation", false)) {
                            menuModel.setSubMenu(this.getSubMenuNavItems(child, 2));
                        }
                        menuModelList.add(menuModel);
                    }
                })));
        return menuModelList;
    }

    private List<NavItem> getSubMenuNavItems(final Resource resource, final int depth) {
        if (depth == 0) {
            return new ArrayList<>();
        }
        final List<NavItem> subMenuModelList = new ArrayList<>();
        Optional.ofNullable(resource).ifPresent(rootResource -> rootResource.listChildren().forEachRemaining(child -> Optional.ofNullable(child.getChild(JcrConstants.JCR_CONTENT))
                .map(Resource::getValueMap)
                .ifPresent(properties -> {
                    if (!properties.get("hideInNavigation", false)) {
                        final NavItem menuModel = new NavItem();
                        menuModel.setTitle(child.getName());
                        menuModel.setLink(child.getPath());
                        if (!properties.get("hideInSubNavigation", false)) {
                            menuModel.setSubMenu(this.getSubMenuNavItems(child, depth-1));
                        }
                        subMenuModelList.add(menuModel);
                    }
                })));
        return subMenuModelList;
    }


}


package com.vertis.core.services;

import com.vertis.core.models.NavItem;
import org.apache.sling.api.resource.Resource;

import java.util.List;

public interface NavBarService {
    List<NavItem> getRootNavItems(final Resource resource) ;
}

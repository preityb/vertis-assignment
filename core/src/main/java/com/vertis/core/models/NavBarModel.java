package com.vertis.core.models;

import com.vertis.core.services.NavBarService;
import lombok.Builder;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
public class NavBarModel {
    @OSGiService
    private NavBarService navBarService;

    @SlingObject
    private ResourceResolver resolver;

    @ValueMapValue
    @Default(values = "/content/examples/en")
    private String rootPath;

    @Getter
    private List<NavItem> navItems = new ArrayList<>();

    @PostConstruct
    public void init() {
        final Resource rootResource = resolver.getResource(rootPath);
        Optional.ofNullable(rootResource).ifPresent(resource -> this.navItems = this.navBarService.getRootNavItems(resource));
    }
}

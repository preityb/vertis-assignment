package com.vertis.core.models;

import com.vertis.core.services.impl.NavBarServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
public class NavBarModelTest {

    private static final AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);

    @BeforeAll
    static void setup() {
        ctx.addModelsForClasses(NavBarModelTest.class);
        ctx.load().json("/jcr/content/content.json", "/content");
        ctx.registerInjectActivateService(new NavBarServiceImpl());
        ctx.currentResource(ctx.load().json("/component/menubar.json", "/menubar"));
    }

    @Test
    void testNavBarModelTest() {
        final NavBarModel navBarModel = ctx.currentResource().adaptTo(NavBarModel.class);
        Assert.assertNotNull(navBarModel);
        Assert.assertNotNull(navBarModel.getNavItems());

        // added 3 pages under /en, but it should have 2 as 3rd is marked for hide in navigation
        Assert.assertEquals(2, navBarModel.getNavItems().size());
        Assert.assertNotNull( navBarModel.getNavItems().get(0));
        Assert.assertNotNull( navBarModel.getNavItems().get(0).getSubMenu());
        Assert.assertEquals(2, navBarModel.getNavItems().get(0).getSubMenu().size());

        Assert.assertNotNull( navBarModel.getNavItems().get(1));
        Assert.assertNotNull( navBarModel.getNavItems().get(1).getSubMenu());
        // there are 2 pages under page2 but page2 is marked for hide all sub pages so getting 0
        Assert.assertEquals(0, navBarModel.getNavItems().get(1).getSubMenu().size());
    }

}

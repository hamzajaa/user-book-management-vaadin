package org.example.userbookmanagement.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.example.userbookmanagement.frontend.client.create_client.CreateClient;
import org.example.userbookmanagement.frontend.client.list_client.ListClient;

@Route("")
public class MainView extends AppLayout {


    public MainView() {
        H1 title = new H1("MyApp");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");

        HorizontalLayout navigation = getNavigation();
        navigation.getElement();

        addToNavbar(title, navigation);

    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL,
                LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL
        );
        navigation.add(
                createLink("Dashboard", CreateClient.class),
                createLink("Orders",CreateClient.class),
                createLink("Client", ListClient.class),
                createLink("Products", CreateClient.class)
        );
        return navigation;
    }


    private RouterLink createLink(String viewName, Class<? extends Component> viewClass) {
        RouterLink link = new RouterLink(viewName,viewClass);
//        link.add(viewName);
        // Demo has no routes
        // link.setRoute(viewClass.java);

        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.TextColor.SECONDARY,
                LumoUtility.FontWeight.MEDIUM
            );

//        link.getStyle().set("text-decoration", "none");

        return link;
    }

}

package org.example.userbookmanagement.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("dashboard")
public class Dashboard extends Div {
    public Dashboard() {
        Button primaryButton = new Button("Primary");
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button secondaryButton = new Button("Secondary");

        Button tertiaryButton = new Button("Tertiary");
        tertiaryButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout horizontalLayout = new HorizontalLayout(primaryButton,
                secondaryButton, tertiaryButton);
        add(horizontalLayout);
    }
}

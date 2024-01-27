package org.example.userbookmanagement.frontend.client.list_client;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.example.userbookmanagement.backend.dto.ClientDto;
import org.example.userbookmanagement.backend.service.facade.ClientService;
import org.example.userbookmanagement.frontend.MainView;
import org.example.userbookmanagement.frontend.client.create_client.CreateClient;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;


@Route("list-client")
public class ListClient extends Div implements BeforeEnterObserver {

    private ClientService clientService;

    public Grid<ClientDto> grid;
    public GridListDataView<ClientDto> dataView;
    public DataProvider<ClientDto, Void> dataProvider;

    public List<ClientDto> clients = new ArrayList<>();

    public ListClient(ClientService clientService) {
        this.clientService = clientService;

        MainView mainView = new MainView();
        add(mainView);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.getStyle().setBorder(LumoUtility.Border.ALL);
        layout.getStyle().set("background-color", "#f8f9fa");

        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.getStyle().set("background-color", "#f8f9fa");
//        layout2.setMargin(true);
        layout2.getStyle().setMarginTop("1em");
        layout2.getStyle().setMarginLeft("1em");
        layout2.getStyle().setMarginRight("1em");
        layout2.getStyle().setBorder(LumoUtility.Border.ALL);
        layout2.getStyle().setDisplay(Style.Display.FLEX);
        layout2.getStyle().setAlignItems(Style.AlignItems.CENTER);
        layout2.getStyle().setJustifyContent(Style.JustifyContent.SPACE_BETWEEN);
        Span span = new Span("Manage Clients");
        span.getStyle().setMargin("0.5em");
        span.getStyle().setFontWeight(Style.FontWeight.BOLD);
        span.getStyle().setFontSize(LumoUtility.FontSize.MEDIUM);
        layout2.add(span);

        Button newButton = new Button("New");
        newButton.getStyle().setMarginBottom("0.5em");
        newButton.getStyle().setMarginTop("0.5em");
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        newButton.setIcon(VaadinIcon.PLUS.create());
        newButton.addClickListener(buttonClickEvent -> {
            CreateClient createClient = new CreateClient(clientService);
            createClient.openDialog();
        });

        Button searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        searchButton.setIcon(VaadinIcon.SEARCH_PLUS.create());
        searchButton.getStyle().setMarginBottom("0.5em");
        searchButton.getStyle().setMarginTop("0.5em");

        Button multiDeleteButton = new Button(new Icon(VaadinIcon.TRASH));
        multiDeleteButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        multiDeleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        multiDeleteButton.getStyle().setMarginBottom("0.5em");
        multiDeleteButton.getStyle().setMarginTop("0.5em");

        layout.add(newButton);
        layout.add(searchButton);
        layout.add(multiDeleteButton);

        grid = new Grid<>(ClientDto.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(ClientDto::getFirstName)
                .setHeader("First Name")
                .setAutoWidth(true)
                .setSortable(true)
                .getStyle().set("background-color", "#f8f9fa");
        grid.addColumn(ClientDto::getLastName)
                .setHeader("Last Name")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(ClientDto::getEmail)
                .setHeader("Email")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(ClientDto::getPhoneNumber)
                .setHeader("Phone Number")
                .setAutoWidth(true)
                .setSortable(true);
        grid.addColumn(
                new ComponentRenderer<>((client) -> {
                    HorizontalLayout layoutOfCudButton = new HorizontalLayout();

                    Button deteleButton = new Button(new Icon(VaadinIcon.TRASH));
                    deteleButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_PRIMARY);
                    deteleButton.addClickListener(c -> this.delete(client));

                    Button editButton = new Button(new Icon(VaadinIcon.PENCIL));
                    editButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_SUCCESS);
                    editButton.addClickListener(c -> this.delete(client));

                    Button viewButton = new Button(new Icon(VaadinIcon.EYE));
                    viewButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_TERTIARY);
                    viewButton.addClickListener(c -> this.delete(client));

                    layoutOfCudButton.add(deteleButton, editButton, viewButton);
                    return layoutOfCudButton;
                })
        ).setHeader("Actions");

        dataView = grid.setItems(findAll());

        clients = findAll();

        dataProvider = new ListDataProvider(clients);
        grid.setDataProvider(dataProvider);

        TextField searchField = new TextField();
        searchField.setWidth("20%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.getStyle().setAlignItems(Style.AlignItems.FLEX_END);
        searchField.getStyle().setColor("white");
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(client -> {
            String searchTerm = searchField.getValue().trim();
            if (searchTerm.isEmpty())
                return true;

            boolean matchesFirstName = matchesTerm(client.getFirstName(), searchTerm);
            boolean matchesLastName = matchesTerm(client.getLastName(), searchTerm);
            boolean matchesEmail = matchesTerm(client.getEmail(), searchTerm);
            boolean matchesPhoneNumber = matchesTerm(client.getPhoneNumber(),
                    searchTerm);

            return matchesFirstName || matchesLastName || matchesEmail || matchesPhoneNumber;
        });

        layout2.add(searchField);

        grid.getStyle().setMarginBottom("1em");
        grid.getStyle().setMarginLeft("1em");
        grid.getStyle().setMarginRight("1em");


        // Add the wrapper layout to the main component
        add(layout);
        add(layout2);

        // Add the components to the wrapper layout
        add(grid);
    }

    private void delete(ClientDto client) {
        this.clientService.delete(client);
    }


    private List<ClientDto> findAll() {
        return clientService.findAll();
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    public void refreshData() {
        clients = findAll();
        dataProvider = new ListDataProvider<>(clients).withConfigurableFilter();
        grid.setDataProvider(dataProvider);
        dataView.refreshAll();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
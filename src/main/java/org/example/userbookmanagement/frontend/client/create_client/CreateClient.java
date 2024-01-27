package org.example.userbookmanagement.frontend.client.create_client;

import com.helger.commons.functional.IBooleanFunction;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.example.userbookmanagement.backend.dto.ClientDto;
import org.example.userbookmanagement.backend.service.facade.ClientService;
import org.example.userbookmanagement.frontend.MainView;
import org.example.userbookmanagement.frontend.client.list_client.ListClient;
import org.springframework.beans.factory.annotation.Autowired;


@Route("create-client")
public class CreateClient extends Div {

    private ClientService clientService;

    Binder<ClientDto> binder;
    FormLayout formLayout;
    Dialog createDialog;

    TextField firstName;
    TextField lastName;
    EmailField email;
    TextField phoneNumber;
    Button cancelButton;
    Button saveButton;


    public CreateClient(ClientService clientService) {
        this.clientService = clientService;

        MainView mainView = new MainView();
        add(mainView);

        createDialog = new Dialog();
        createDialog.setHeaderTitle("New Client");

        VerticalLayout formVerticalLayout = createFormLayout();
        createDialog.add(formVerticalLayout);

        save();

    }

    public void openDialog() {
        createDialog.open();
    }

    private VerticalLayout createFormLayout() {
        formLayout = new FormLayout();
        createHeaderFormLayout(formLayout);
        createFooterFormLayout(formLayout);


        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.setPadding(true);
        verticalLayout.setSpacing(true);
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.getStyle().set("width", "18rem").set("max-width", "100%");


        verticalLayout.add(formLayout);

        bindData(firstName, lastName, email, phoneNumber);

        return verticalLayout;

    }


    private void createFooterFormLayout(FormLayout formLayout) {
        cancelButton = new Button("Cancel", e -> createDialog.close());
        saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        createDialog.getFooter().add(cancelButton);
        createDialog.getFooter().add(saveButton);
    }


    private void createHeaderFormLayout(FormLayout formLayout) {
        firstName = new TextField("First Name");
        lastName = new TextField("Lat Name");
        email = new EmailField("Email");
        email.setClearButtonVisible(true);

        phoneNumber = new TextField("Phone Number");
        phoneNumber.setMaxLength(10);
        phoneNumber.setMinLength(10);
        phoneNumber.setErrorMessage("Enter a valid phone number");


        formLayout.add(firstName, lastName, email, phoneNumber);

        createDialog.getHeader().add(formLayout);
    }

    private void bindData(TextField firstName, TextField lastName, EmailField email, TextField phoneNumber) {
        binder = new Binder<>(ClientDto.class);
        binder.forField(firstName)
                .asRequired("First Name is obligation")
                .bind(ClientDto::getFirstName, ClientDto::setFirstName);
        binder.forField(lastName)
                .asRequired("Last Name is obligation")
                .bind(ClientDto::getLastName, ClientDto::setLastName);
        binder.forField(email)
                .asRequired("Email is obligation")
                .bind(ClientDto::getEmail, ClientDto::setEmail);
        binder.forField(phoneNumber)
                .asRequired("Phone Number is obligation")
                .bind(ClientDto::getPhoneNumber, ClientDto::setPhoneNumber);

    }

    private boolean isValidEmail(EmailField email) {
        if (!email.isInvalid()) {
            email.setErrorMessage("Enter a valid email address");
            email.setClearButtonVisible(true);
            return false;
        }
        return true;
    }


    private void save() {
        saveButton.addClickListener(clickEvent -> {
            ClientDto clientDto = new ClientDto(); // Create a new instance of ClientDto
            if (binder.writeBeanIfValid(clientDto)) {
                 clientService.save(clientDto);
                // Get the reference to the existing ListClient
                ListClient listClient = (ListClient) UI.getCurrent().getChildren()
                        .filter(component -> component.getClass() == ListClient.class)
                        .findFirst().orElse(null);

                if (listClient != null) {
                    listClient.refreshData();
                }
                binder.readBean(null); // Reset the Binder
                clearFormFields(createDialog); // Clear the form fields
                createDialog.close();
                Notification.show("Client Saved Successfully.", 3000, Notification.Position.TOP_END).addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } else {
                // Handle validation errors if needed
                if (email.isInvalid() && !email.isEmpty()) {
                    Notification.show("Enter a valid email address.", 3000, Notification.Position.TOP_END).addThemeVariants(NotificationVariant.LUMO_WARNING);
                } else if (phoneNumber.isInvalid() && !phoneNumber.isEmpty()) {
                    Notification.show("Enter a valid phone number.", 3000, Notification.Position.TOP_END).addThemeVariants(NotificationVariant.LUMO_WARNING);
                } else {
                    Notification.show("Please fill in all the required fields.", 3000, Notification.Position.TOP_END).addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        });
    }

    private void clearFormFields(Dialog formLayout) {
        formLayout.getChildren().forEach(component -> {
            switch (component) {
                case TextField tf -> tf.clear();
                case EmailField ef -> ef.clear();
                case Component c -> System.out.printf("");
            }
        });
    }

}

/**
 * @author Branislav Bajlovski
 * @date 29.12.2016
 */
package com.theidentityhub.demo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.theidentityhub.model.AccountProvider;
import com.theidentityhub.model.Friend;
import com.theidentityhub.model.Profile;
import com.theidentityhub.model.Role;
import com.theidentityhub.service.IdentityService;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Branislav Bajlovski
 *
 */
public class MainApp extends Application {

    IdentityService     is;

    final Button        button = new Button("Sign in");
    final Label         label  = new Label("Undefined");

    private final Scene scene  = new Scene(new Group());

    /**
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(args);

    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(final Stage stage) throws Exception {

        stage.setTitle("Java Demo");

        final VBox vbox = new VBox();
        vbox.setLayoutX(200);
        vbox.setLayoutY(200);

        vbox.getChildren().add(this.label);
        vbox.getChildren().add(this.button);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.BASELINE_LEFT);
        ((Group) this.scene.getRoot()).getChildren().add(vbox);

        stage.setScene(this.scene);
        stage.show();

        final String BASE_URL = "https://www.theidentityhub.com/[Your URL segment]";
        final String CLIENT_ID = "[Your Application Client Id]";

        this.is = new IdentityService(CLIENT_ID, BASE_URL);

        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                System.out.println("test / handle");
                if (MainApp.this.button.getText().toString().compareTo("Sign out") == 0) {

                    MainApp.this.is.signOut();
                    MainApp.this.label.setText("");
                    MainApp.this.button.setText("Sign in");
                } else {
                    System.out.println("test / handle > else");
                    MainApp.this.is.tryAuthenticate();

                    if (MainApp.this.is.tryAuthenticate()) {
                        System.out.println("test / IS AUTH ---> " + MainApp.this.is.isAuthenticated());
                        MainApp.this.button.setText("Sign out");
                        loadData();
                    }

                }
            }
        });

    }

    void loadData() {
        try {
            // Check if user is verified
            final boolean isVerified = this.is.verifyToken();// DONE
            System.out.println("test / IS verified-->" + isVerified);

            // Get profile data
            final Profile object = this.is.getProfile();// DONE
            System.out.println("test / PROFILE FROM GET --->>>: " + object);

            final String textProfile = "--PROFILE--\n" + this.label.getText() + "DisplayName " +
                            object.getDisplayName() + "\n" + "EmailAddress " + object.getEmailAddress() + "\n" +
                            "GivenName " + object.getGivenName() + "\n" + "IdentityId " + object.getIdentityId() +
                            "\n" + "Picture " + object.getPicture() + "\n" + "Surname " + object.getSurname() + "\n" +
                            "UserName " + object.getUserName() + "\n" + "LargePictures " + object.getLargePictures() +
                            "\n" + "MediumPictures " + object.getMediumPictures() + "\n" + "Is editable " +
                            object.isEditable() + "\n" + "SmallPictures " + object.getSmallPictures() + "\n\n";
            this.label.setText(textProfile);

            // Get accounts data
            final ArrayList<AccountProvider> accountProviders = this.is.getAccounts();// DONE

            String textAccounts = this.label.getText().toString() + "\n--ACCOUNTS--";
            for (final AccountProvider provider : accountProviders) {

                System.out.println("test / provider: " + object);

                textAccounts = textAccounts + "\n\n Display Name " + provider.getDisplayName() + "\n" +
                                "ProviderImageUrl " + provider.getProviderImageUrl() + "\n" + "account length " +
                                provider.getAccounts().length + "\n";
                if (provider.getAccounts().length > 0) {
                    textAccounts = textAccounts + "AccountId " + provider.getAccounts()[0].getAccountId() + "\n" +
                                    "DisplayName " + provider.getAccounts()[0].getDisplayName() + "\n" +
                                    "EmailAddress " + provider.getAccounts()[0].getEmailAddress() + "\n" +
                                    "PictureUrl " + provider.getAccounts()[0].getPictureUrl() + "\n";
                }

            }

            this.label.setText(textAccounts);
            // Get friends data
            final ArrayList<Friend> friends = this.is.getFriends();// DONE
            String textFriends = this.label.getText().toString() + "\n--FRIENDS--";
            for (final Friend friend : friends) {

                textFriends = textFriends + "\n\n Display Name " + friend.getDisplayName() + "\n" + "\n" + "\n" +
                                "IdentityId " + friend.getIdentityId() + "\n" + "\n" + "SmallPicture " +
                                friend.getSmallPicture() + "\n" + "\n" + "LargePictures " + friend.getLargePictures() +
                                "\n" + "\n" + "MediumPictures " + friend.getMediumPictures() + "\n" + "\n" +
                                "SmallPictures " + friend.getSmallPictures() + "\n";

            }
            this.label.setText(textFriends);
            // Get roles data
            final ArrayList<Role> roles = this.is.getRoles();// DONE
            String textRoles = this.label.getText().toString() + "\n--ROLES--";
            if (roles != null) {
                for (final Role role : roles) {

                    textRoles = textRoles + "\n\n Name " + role.getName() + "\n";

                }
            }
            this.label.setText(textRoles);

        } catch (final InterruptedException e) {
            e.printStackTrace();
            System.out.println("test / InterruptedException " + e.getLocalizedMessage());
        } catch (final ExecutionException e) {
            e.printStackTrace();
            System.out.println("test / ExecutionException " + e.getLocalizedMessage());
        }
    }

}

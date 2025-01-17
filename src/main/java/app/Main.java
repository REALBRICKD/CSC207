package app;

import data_access.AudioDataAccessObject;
import interface_adapter.Audio.AudioController;
import use_case.Audio.AudioInputData;
import view.AudioManager; // Import the AudioManager class
import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Audio.AudioInteractor;

import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // Load the custom Goblin font as a resource stream
            InputStream fontStream = Main.class.getResourceAsStream("/cc.ttf"); // Adjust the path as needed
            Font goblinFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(30f); // Adjust the font size as needed
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(goblinFont);

            // Main application window setup
            JFrame application = new JFrame("Translayte");
            application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // Card layout for switching between views
            CardLayout cardLayout = new CardLayout();
            JPanel views = new JPanel(cardLayout);
            application.add(views);

            // View manager model
            ViewManagerModel viewManagerModel = new ViewManagerModel();
            new ViewManager(views, cardLayout, viewManagerModel);

            // ViewModels setup
            LoginViewModel loginViewModel = new LoginViewModel();
            LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
            SignupViewModel signupViewModel = new SignupViewModel();

            // Data access object
            FileUserDataAccessObject userDataAccessObject;
            try {
                userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Create new AudioController using factory method
            AudioController audioController = AudioControllerFactory.createAudioController("src/power.wav");


//            // Create an instance of AudioManager
//            AudioManager audioManager = new AudioManager("/power.wav");

            // Creating views
            SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject, audioController); // Pass audioController
            applyFontToComponent(signupView, goblinFont);
            views.add(signupView, signupView.viewName);

            LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, signupViewModel, userDataAccessObject, audioController); // Pass audioController
            applyFontToComponent(loginView, goblinFont);
            views.add(loginView, loginView.viewName);

            LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel);
            applyFontToComponent(loggedInView, goblinFont);
            views.add(loggedInView, loggedInView.viewName);

            // Activate the initial view
            viewManagerModel.setActiveView(signupView.viewName);
            viewManagerModel.firePropertyChanged();

            application.pack();
            application.setVisible(true);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Handle font loading error
        }
    }

    private static void applyFontToComponent(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyFontToComponent(child, font);
            }
        }
    }
}

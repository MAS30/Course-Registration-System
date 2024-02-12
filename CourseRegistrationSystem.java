package application;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseRegistrationSystem extends Application {

    private Stage primaryStage;
    private VBox root;
    private VBox coursesBox;
    private TextField nameTextField;
    private TextField phoneTextField;
    private TextField emailTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeStage(primaryStage);

        root = new VBox(10);
        root.setStyle("-fx-padding: 20px");

        Label nameLabel = createBoldLabel("Name:");
        nameTextField = createTextField("Enter your name");
        Label phoneLabel = createBoldLabel("Phone:");
        phoneTextField = createTextField("Enter your phone");
        Label emailLabel = createBoldLabel("Email:");
        emailTextField = createTextField("Enter your email");

        root.getChildren().addAll(nameLabel, nameTextField, phoneLabel, phoneTextField, emailLabel, emailTextField);

        Label coursesLabel = createBoldLabel("Courses:");

        Course graphicDesign = new Course("Graphic Designing");
        Course webDevelopment = new Course("Web Development");
        Course mobAppDevelopment = new Course("Mobile Application Development");
        Course cyberSecurity = new Course("Cyber Security");
        Course artificialIntelligence = new Course("Artificial Intelligence");
        Course videoEditing = new Course("Video Editing");
        Course gameDevelopment = new Course("Game Development");
        Course msOffice = new Course("Ms-Office");


        coursesBox = new VBox(5);
        coursesBox.getChildren().addAll(coursesLabel, graphicDesign, webDevelopment, mobAppDevelopment, cyberSecurity, artificialIntelligence, videoEditing, gameDevelopment, msOffice);

        Label noteLabel = createBoldLabel("\nNote: You can submit only 3 preferences.");
        
        Button submitButton = createSubmitButton();

        root.getChildren().addAll(coursesBox,noteLabel, submitButton);
        
        Scene scene = new Scene(root, 350, 520);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void initializeStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Course Registration System");
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private Button createSubmitButton() {
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                displayResultScene();
            } catch (RegistrationException ex) {
                showRegistrationErrorAlert(ex.getMessage());
            }
        });
        return submitButton;
    }

    private void displayResultScene() throws RegistrationException {
        String studentName = nameTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        String email = emailTextField.getText().trim();

        if (studentName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Please enter name, phone, and email.");
        }

        int selectedCoursesCount = countSelectedCourses();
        if (selectedCoursesCount == 0) {
            throw new RegistrationException("Please register at least one course.");
        }

        if (selectedCoursesCount > 3) {
            throw new RegistrationException("Selection Limit Exceed: You can select only 3 preferences.");
        }


        showSuccessMessage("Registration successful!\nWe'll contact you Shortly.");
    }


    private void showRegistrationErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class RegistrationException extends Exception {
        public RegistrationException(String message) {
            super(message);
        }
    }

    private static class Course extends CheckBox {
        public Course(String courseName) {
            super(courseName);
        }
    }

    
    private int countSelectedCourses() {
        int count = 0;
        for (Node node : coursesBox.getChildren()) {
            if (node instanceof Course && ((Course) node).isSelected()) {
                count++;
            }
        }
        return count;
    }
}

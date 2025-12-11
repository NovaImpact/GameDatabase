package com.example.demo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;

/**
 * Controller for the Game Data Editor
 * Handles all UI interactions and data management
 */
public class HelloController {


    @FXML private TableView<com.example.demo.Game> gameTable;
    @FXML private TableColumn<com.example.demo.Game, Integer> rankColumn;
    @FXML private TableColumn<com.example.demo.Game, String> titleColumn;
    @FXML private TableColumn<com.example.demo.Game, Double> salesColumn;
    @FXML private TableColumn<com.example.demo.Game, LocalDate> releaseDateColumn;
    @FXML private TableColumn<com.example.demo.Game, String> typeColumn;

    @FXML private TextField rankField;
    @FXML private TextField titleField;
    @FXML private TextField salesField;
    @FXML private DatePicker releaseDatePicker;
    @FXML private Label typeLabel;

    @FXML private TextField seriesField;
    @FXML private TextField platformsField;
    @FXML private TextField releaseYearField;
    @FXML private TextField developerField;
    @FXML private TextField publisherField;

    @FXML private TextField ratingField;
    @FXML private TextField metascoreField;
    @FXML private TextField devPublisherField;


    @FXML private ImageView gameImageView;
    @FXML private Button addImageButton;
    @FXML private Button removeImageButton;

    @FXML private TextArea detailsArea;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private ComboBox<String> filterComboBox;

    private com.example.demo.Game selectedGame;


    @FXML
    public void initialize() {
        try {
            System.out.println("=".repeat(50));
            System.out.println("INITIALIZING GAME DATA EDITOR");
            System.out.println("=".repeat(50));

            GameCopies.readGameCopiesData();
            GameRating.readGameRatingData();

            System.out.println("Total Games: " + com.example.demo.Game.getAllGames().size());
            System.out.println("=".repeat(50));

            setupFilterComboBox();
            setupTableColumns();
            setupTableSelectionListener();
            setupImageView();

            applyFilter();

            if (!gameTable.getItems().isEmpty()) {
                gameTable.getSelectionModel().select(0);
            }

        } catch (Exception e) {
            showError("Initialization Error", "Failed to load game data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupFilterComboBox() {
        filterComboBox.getItems().addAll("All Games", "Sales Data Only", "Rating Data Only");
        filterComboBox.setValue("All Games");
        filterComboBox.setOnAction(e -> applyFilter());
    }

    private void setupTableColumns() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        salesColumn.setCellValueFactory(new PropertyValueFactory<>("sales"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

        typeColumn.setCellValueFactory(cellData -> {
            com.example.demo.Game game = cellData.getValue();
            String type = game instanceof GameCopies ? "Sales Data" :
                    game instanceof GameRating ? "Rating Data" : "Game";
            return new javafx.beans.property.SimpleStringProperty(type);
        });

        salesColumn.setCellFactory(column -> new TableCell<com.example.demo.Game, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2fM", item));
                }
            }
        });
    }

    private void setupTableSelectionListener() {
        gameTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedGame = newValue;
                        displayGameDetails(newValue);
                    }
                }
        );
    }

    private void setupImageView() {
        gameImageView.setPreserveRatio(true);
        gameImageView.setFitWidth(250);
        gameImageView.setFitHeight(350);
    }

    private void applyFilter() {
        gameTable.getItems().clear();
        String filter = filterComboBox.getValue();

        switch (filter) {
            case "Sales Data Only":
                gameTable.getItems().addAll(GameCopies.getAllGameCopies());
                break;
            case "Rating Data Only":
                gameTable.getItems().addAll(GameRating.getAllRatings());
                break;
            default:
                gameTable.getItems().addAll(com.example.demo.Game.getAllGames());
                break;
        }
    }

    private void displayGameDetails(com.example.demo.Game game) {
        clearAllFields();

        rankField.setText(String.valueOf(game.getRank()));
        titleField.setText(game.getTitle());
        salesField.setText(String.valueOf(game.getSales()));
        releaseDatePicker.setValue(game.getReleaseDate());
        detailsArea.setText(game.toString());

        if (game instanceof GameCopies) {
            displayGameCopiesDetails((GameCopies) game);
        } else if (game instanceof GameRating) {
            displayGameRatingDetails((GameRating) game);
        }

        displayGameImage(game);
    }

    private void displayGameCopiesDetails(GameCopies game) {
        typeLabel.setText("Type: Sales Data (GameCopies)");
        typeLabel.setStyle("-fx-text-fill: #3498db; -fx-font-weight: bold;");

        seriesField.setText(game.getSeries());
        platformsField.setText(game.getPlatforms());
        releaseYearField.setText(String.valueOf(game.getReleaseYear()));
        developerField.setText(game.getDeveloper());
        publisherField.setText(game.getPublisher());

        seriesField.setDisable(false);
        platformsField.setDisable(false);
        releaseYearField.setDisable(false);
        developerField.setDisable(false);
        publisherField.setDisable(false);

        ratingField.setDisable(true);
        metascoreField.setDisable(true);
        devPublisherField.setDisable(true);
    }

    private void displayGameRatingDetails(GameRating game) {
        typeLabel.setText("Type: Rating Data (GameRating)");
        typeLabel.setStyle("-fx-text-fill: #9b59b6; -fx-font-weight: bold;");

        ratingField.setText(game.getRating());
        metascoreField.setText(String.valueOf(game.getMetascore()));
        devPublisherField.setText(game.getDeveloperPublisher());

        ratingField.setDisable(false);
        metascoreField.setDisable(false);
        devPublisherField.setDisable(false);

        seriesField.setDisable(true);
        platformsField.setDisable(true);
        releaseYearField.setDisable(true);
        developerField.setDisable(true);
        publisherField.setDisable(true);
    }

    private void displayGameImage(com.example.demo.Game game) {
        if (game.getImagePath() != null && !game.getImagePath().isEmpty()) {
            try {
                File imageFile = new File(game.getImagePath());
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    gameImageView.setImage(image);
                    removeImageButton.setDisable(false);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
        gameImageView.setImage(null);
        removeImageButton.setDisable(true);
    }

    private void clearAllFields() {
        seriesField.clear();
        platformsField.clear();
        releaseYearField.clear();
        developerField.clear();
        publisherField.clear();
        ratingField.clear();
        metascoreField.clear();
        devPublisherField.clear();
    }

    @FXML
    private void handlePrevious() {
        int currentIndex = gameTable.getSelectionModel().getSelectedIndex();
        if (currentIndex > 0) {
            gameTable.getSelectionModel().select(currentIndex - 1);
            gameTable.scrollTo(currentIndex - 1);
        }
    }

    @FXML
    private void handleNext() {
        int currentIndex = gameTable.getSelectionModel().getSelectedIndex();
        if (currentIndex < gameTable.getItems().size() - 1) {
            gameTable.getSelectionModel().select(currentIndex + 1);
            gameTable.scrollTo(currentIndex + 1);
        }
    }

    @FXML
    private void handleSave() {
        if (selectedGame == null) {
            showWarning("No Selection", "Please select a game to save changes.");
            return;
        }

        try {
            selectedGame.setRank(Integer.parseInt(rankField.getText().trim()));
            selectedGame.setTitle(titleField.getText().trim());
            selectedGame.setSales(Double.parseDouble(salesField.getText().trim()));
            selectedGame.setReleaseDate(releaseDatePicker.getValue());

            if (selectedGame instanceof GameCopies) {
                GameCopies gameCopies = (GameCopies) selectedGame;
                gameCopies.setSeries(seriesField.getText().trim());
                gameCopies.setPlatforms(platformsField.getText().trim());
                gameCopies.setReleaseYear(Integer.parseInt(releaseYearField.getText().trim()));
                gameCopies.setDeveloper(developerField.getText().trim());
                gameCopies.setPublisher(publisherField.getText().trim());
            } else if (selectedGame instanceof GameRating) {
                GameRating gameRating = (GameRating) selectedGame;
                gameRating.setRating(ratingField.getText().trim());
                gameRating.setMetascore(Integer.parseInt(metascoreField.getText().trim()));
                gameRating.setDeveloperPublisher(devPublisherField.getText().trim());
            }

            gameTable.refresh();
            detailsArea.setText(selectedGame.toString());
            showSuccess("Success", "Game data saved successfully!");

        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter valid numbers for numeric fields.");
        } catch (Exception e) {
            showError("Save Error", "Failed to save game data: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddImage() {
        if (selectedGame == null) {
            showWarning("No Selection", "Please select a game to add cover art.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Game Cover Art");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog(addImageButton.getScene().getWindow());

        if (selectedFile != null) {
            selectedGame.setImagePath(selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            gameImageView.setImage(image);
            removeImageButton.setDisable(false);
            showSuccess("Success", "Cover art added successfully!");
        }
    }

    @FXML
    private void handleRemoveImage() {
        if (selectedGame != null) {
            selectedGame.setImagePath(null);
            gameImageView.setImage(null);
            removeImageButton.setDisable(true);
            showSuccess("Success", "Cover art removed successfully!");
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedGame == null) {
            showWarning("No Selection", "Please select a game to delete.");
            return;
        }


        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Game");
        confirm.setContentText("Are you sure you want to delete:\n\"" +
                selectedGame.getTitle() + "\"?\n\nThis action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                com.example.demo.Game.getAllGames().remove(selectedGame);
                if (selectedGame instanceof GameCopies) {
                    GameCopies.getAllGameCopies().remove((GameCopies) selectedGame);
                } else if (selectedGame instanceof GameRating) {
                    GameRating.getAllRatings().remove((GameRating) selectedGame);
                }
                applyFilter();
                showSuccess("Deleted", "Game deleted successfully!");
            }
        });
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
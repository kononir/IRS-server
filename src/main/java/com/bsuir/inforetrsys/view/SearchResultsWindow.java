package com.bsuir.inforetrsys.view;

import com.bsuir.inforetrsys.controller.ControllerException;
import com.bsuir.inforetrsys.controller.MetricsController;
import com.bsuir.inforetrsys.controller.SearchResultsController;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.entity.table.ClassificationMatrixData;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchResultsWindow {
    private static final String METRICS_FXML_FILE_PATH = "view/metrics.fxml";
    private static final String STYLE_FILE_PATH = "style/main.css";

    SearchResultsController controller = new SearchResultsController();

    private List<SearchResult> searchResults;
    private int searchingTime;

    public SearchResultsWindow(List<SearchResult> searchResults, int searchingTime) {
        this.searchResults = searchResults;
        this.searchingTime = searchingTime;
    }

    public void show() {
        Button showMetricsTableButton = new Button("Show metrics table");
        showMetricsTableButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource(METRICS_FXML_FILE_PATH));
                Parent root = loader.load();

                MetricsController metricsController = loader.getController();
                List<ClassificationMatrixData> dataList = controller.controlFindingMatrixData(searchResults);
                metricsController.setClassificationMatrixData(dataList);
                metricsController.setMetricsData(controller.controlComputingMetrics(dataList));

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getClassLoader().getResource(STYLE_FILE_PATH).toExternalForm());

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException | ControllerException e) {
                e.printStackTrace();
            }
        });

        Button showHelpButton = new Button("Show help");

        HBox buttonsHBox = new HBox(showMetricsTableButton, showHelpButton);
        buttonsHBox.setId("buttons-hbox");

        Label searchingTimeLabel = new Label("Searching time: " + searchingTime + " ms");

        GridPane resultsGridPane = new GridPane();
        resultsGridPane.setAlignment(Pos.TOP_CENTER);
        resultsGridPane.setId("results-grid-pane");

        resultsGridPane.getColumnConstraints().add(new ColumnConstraints(350, 1000, 1000));
        resultsGridPane.getColumnConstraints().add(new ColumnConstraints(100));

        int rowIndex = 0;
        for (SearchResult searchResult : searchResults) {
            Label titleLabel = new Label(searchResult.getTitle());
            titleLabel.setId("title-label");
            Label snippetLabel = new Label(searchResult.getAddingTime().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + " : " + searchResult.getSnippet());
            snippetLabel.setId("snippet-label");
            Label filePathLabel = new Label(searchResult.getFilePath());
            filePathLabel.setId("file-path-label");

            VBox resultVBox = new VBox(titleLabel, filePathLabel, snippetLabel);

            Label rankLabel = new Label("Rank - " + searchResult.getRank());
            rankLabel.setId("rank-label");

            resultsGridPane.add(resultVBox, 0, rowIndex);
            resultsGridPane.add(rankLabel, 1, rowIndex);

            rowIndex++;
        }

        ScrollPane resultsScrollPane = new ScrollPane(resultsGridPane);
        resultsScrollPane.setFitToHeight(true);
        resultsScrollPane.setFitToWidth(true);
        StackPane.setAlignment(resultsScrollPane, Pos.CENTER);

        VBox root = new VBox();
        root.setId("root-pane");
        root.getChildren().addAll(buttonsHBox, searchingTimeLabel, resultsScrollPane);
        VBox.setVgrow(resultsScrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource(STYLE_FILE_PATH).toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Search results");
        stage.setScene(scene);
        stage.show();
    }
}

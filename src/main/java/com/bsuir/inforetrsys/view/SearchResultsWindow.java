package com.bsuir.inforetrsys.view;

import com.bsuir.inforetrsys.entity.SearchResult;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchResultsWindow {
    private static final String STYLE_FILE_PATH = "style/main.css";
    private List<SearchResult> searchResults;

    public SearchResultsWindow(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public void show() {
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

        ScrollPane scrollPane = new ScrollPane(resultsGridPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        StackPane.setAlignment(scrollPane, Pos.CENTER);

        Pane root = new StackPane();
        root.setId("root-pane");
        root.getChildren().addAll(scrollPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource(STYLE_FILE_PATH).toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Search results");
        stage.setScene(scene);
        stage.show();
    }
}

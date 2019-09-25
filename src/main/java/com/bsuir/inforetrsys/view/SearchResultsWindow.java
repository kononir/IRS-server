package com.bsuir.inforetrsys.view;

import com.bsuir.inforetrsys.entity.SearchResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchResultsWindow {
    private List<SearchResult> searchResults;

    public SearchResultsWindow(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public void show() {
        Button backButton = new Button("Back");
        backButton.setCancelButton(true);
        backButton.setId("back-to-search-button");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(5));

        VBox resultsVBox = new VBox();
        //resultsVBox.autosize();
        //resultsVBox.setFillWidth(true);
        resultsVBox.setSpacing(10);
        resultsVBox.setId("results-pane");

        for (SearchResult searchResult : searchResults) {
            Label titleLabel = new Label(searchResult.getTitle());
            Label snippetLabel = new Label(searchResult.getSnippet());
            snippetLabel.setId("snippet-label");
            Label filePathLabel = new Label(searchResult.getFilePath());
            Label addingTimeLabel = new Label(searchResult.getAddingTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
            addingTimeLabel.setId("adding-time-label");

            VBox resultVBox = new VBox(titleLabel, snippetLabel, filePathLabel, addingTimeLabel);

            Label rankLabel = new Label(String.valueOf(searchResult.getRank()));
            rankLabel.setId("rank-label");

            HBox resultHBox = new HBox(resultVBox, rankLabel);
            resultVBox.setSpacing(2);

            resultsVBox.getChildren().addAll(resultHBox);
        }

        ScrollPane scrollPane = new ScrollPane(resultsVBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        StackPane.setAlignment(scrollPane, Pos.CENTER);
        StackPane.setMargin(scrollPane, new Insets(10));

        Pane root = new StackPane();
        root.getChildren().addAll(backButton, scrollPane);

        Scene scene = new Scene(root, 400, 600);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}

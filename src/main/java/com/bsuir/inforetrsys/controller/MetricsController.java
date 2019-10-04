package com.bsuir.inforetrsys.controller;

import com.bsuir.inforetrsys.entity.table.ClassificationMatrixData;
import com.bsuir.inforetrsys.entity.table.MetricsData;
import com.bsuir.inforetrsys.entity.table.TextCellEnum;
import com.bsuir.inforetrsys.util.TextCellEnumConverter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.List;

public class MetricsController {
    @FXML
    public TableView<ClassificationMatrixData> classificationMatrix;
    @FXML
    public TableColumn<ClassificationMatrixData, TextCellEnum> textMatrixColumn;
    @FXML
    public TableColumn<ClassificationMatrixData, Integer> relevanceMatrixColumn;
    @FXML
    public TableColumn<ClassificationMatrixData, Integer> notRelevanceMatrixColumn;

    @FXML
    public TableView<MetricsData> metricsTable;
    @FXML
    public TableColumn<MetricsData, Double> recallTableColumn;
    @FXML
    public TableColumn<MetricsData, Double> precisionTableColumn;
    @FXML
    public TableColumn<MetricsData, Double> accuracyTableColumn;
    @FXML
    public TableColumn<MetricsData, Double> errorTableColumn;
    @FXML
    public TableColumn<MetricsData, Double> fMeasureTableColumn;

    public void setClassificationMatrixData(List<ClassificationMatrixData> classificationMatrixData) {
        classificationMatrix.setItems(FXCollections.observableList(classificationMatrixData));
    }

    public void setMetricsData(MetricsData metricsData) {
        metricsTable.setItems(FXCollections.singletonObservableList(metricsData));
    }

    @FXML
    private void initialize() {
        textMatrixColumn.setCellValueFactory(new PropertyValueFactory<>("firstColumnText"));
        textMatrixColumn.setCellFactory(TextFieldTableCell.forTableColumn(new TextCellEnumConverter()));
        relevanceMatrixColumn.setCellValueFactory(new PropertyValueFactory<>("relevanceNumber"));
        notRelevanceMatrixColumn.setCellValueFactory(new PropertyValueFactory<>("noRelevanceNumber"));

        recallTableColumn.setCellValueFactory(new PropertyValueFactory<>("recall"));
        precisionTableColumn.setCellValueFactory(new PropertyValueFactory<>("precision"));
        accuracyTableColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        errorTableColumn.setCellValueFactory(new PropertyValueFactory<>("error"));
        fMeasureTableColumn.setCellValueFactory(new PropertyValueFactory<>("fMeasure"));
    }
}

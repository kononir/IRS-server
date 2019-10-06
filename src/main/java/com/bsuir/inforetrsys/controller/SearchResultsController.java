package com.bsuir.inforetrsys.controller;

import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.util.MetricCalculator;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.entity.table.ClassificationMatrixData;
import com.bsuir.inforetrsys.entity.table.MetricsData;
import com.bsuir.inforetrsys.entity.table.TextCellEnum;
import com.bsuir.inforetrsys.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.util.MetricCalculatorImpl;
import com.epam.cafe.service.ServiceException;

import java.util.Arrays;
import java.util.List;

public class SearchResultsController {
    private static final int FOUND_NOT_RELEVANT_NUM = 0;
    private static final int NOT_FOUND_RELEVANT_NUM = 0;

    private static final int FIRST = 0;
    private static final int SECOND = 1;

    private MetricCalculator calculator = new MetricCalculatorImpl();
    private DocumentService documentService = new DocumentServiceImpl();

    public List<ClassificationMatrixData> controlFindingMatrixData(List<SearchResult> searchResults)
            throws ControllerException {
        try {
            int foundDocumentsNum = searchResults.size();
            int allDocumentsNum = documentService.getDocumentsNumber();
            int notFoundDocumentsNum = allDocumentsNum - foundDocumentsNum;

            return Arrays.asList(
                    new ClassificationMatrixData(
                            TextCellEnum.FOUND, foundDocumentsNum, FOUND_NOT_RELEVANT_NUM
                    ),
                    new ClassificationMatrixData(
                            TextCellEnum.NOT_FOUND, NOT_FOUND_RELEVANT_NUM, notFoundDocumentsNum
                    )
            );
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    public MetricsData controlComputingMetrics(List<ClassificationMatrixData> classificationMatrixDataList) {
        ClassificationMatrixData firstClassificationMatrixData = classificationMatrixDataList.get(FIRST);
        int a = firstClassificationMatrixData.getRelevanceNumber();
        int b = firstClassificationMatrixData.getNoRelevanceNumber();
        ClassificationMatrixData secondClassificationMatrixData = classificationMatrixDataList.get(SECOND);
        int c = secondClassificationMatrixData.getRelevanceNumber();
        int d = secondClassificationMatrixData.getNoRelevanceNumber();
        return new MetricsData(
                calculator.calculateRecall(a, c),
                calculator.calculatePrecision(a, b),
                calculator.calculateAccuracy(a, b, c, d),
                calculator.calculateError(a, b, c, d),
                calculator.calculateFMeasure(a, b, c)
        );
    }
}

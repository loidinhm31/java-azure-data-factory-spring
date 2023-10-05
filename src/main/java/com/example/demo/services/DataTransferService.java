package com.example.demo.services;

import com.example.demo.repository.impl.QdQExecPatternRepositoryImpl;
import entities.QdQExecHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataTransferService {
    private final QdQExecPatternRepositoryImpl qdQExecPatternRepository;
    private final DataProcessingService dataProcessingService;


    public String createAndSaveDataInNewTable() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        int weekNumber = sevenDaysAgo.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int year = sevenDaysAgo.getYear();
        String newTableName = "qd_q_exec_history_W" + weekNumber + "_" + year;

        qdQExecPatternRepository.createCustomTable(newTableName);

        List<QdQExecHistory> qdQExecHistoryList = dataProcessingService.getDataFromLast7Days();

        boolean correctInserted = qdQExecPatternRepository.insertBatch(newTableName, qdQExecHistoryList);

        return correctInserted ? "done" : "error";
    }


}

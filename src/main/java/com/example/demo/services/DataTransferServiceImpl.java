package com.example.demo.services;

import com.example.demo.repository.QdQExecHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataTransferServiceImpl {
    private final QdQExecHistoryRepository qdQExecHistoryRepository;


    public String createNewTableAndTransferData() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        int weekNumber = sevenDaysAgo.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        String formattedDate = sevenDaysAgo.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String newTableName = "qd_q_exec_history_W" + weekNumber + "_" + formattedDate;

        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusDays(6);

        try {
            qdQExecHistoryRepository.transferDataFromLast7Days(newTableName,
                    Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            log.info("Transferred data into table {} from {} to {}", newTableName, startDate, endDate);
        } catch (UncategorizedSQLException e) {
            log.error("Cannot transfer data to table {}", newTableName, e);
        }
        return "done";
    }

}

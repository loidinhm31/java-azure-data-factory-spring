package com.example.demo.services;

import com.example.demo.repository.mapper.QdQExecHistoryMapper;
import entities.QdQExecHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DataProcessingService {
    private final QdQExecHistoryMapper qdQExecHistoryMapper;

    public List<QdQExecHistory> getDataFromLast7Days() {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusDays(6);
        return qdQExecHistoryMapper.getDataFromLast7Days(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
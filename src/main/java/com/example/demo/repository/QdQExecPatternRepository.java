package com.example.demo.repository;


import entities.QdQExecHistory;

import java.util.List;

public interface QdQExecPatternRepository {
    void createCustomTable(String newTableName);

    boolean insertBatch(String tableName, List<QdQExecHistory> qdQExecHistories);
}

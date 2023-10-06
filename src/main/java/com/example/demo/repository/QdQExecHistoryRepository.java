package com.example.demo.repository;

import com.example.demo.annotation.SqlServerDataSource;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@SqlServerDataSource
public interface QdQExecHistoryRepository {
    void transferDataFromLast7Days(@Param("tableName") String tableName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
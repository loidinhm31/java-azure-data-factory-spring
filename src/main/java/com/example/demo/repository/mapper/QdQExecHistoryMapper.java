package com.example.demo.repository.mapper;

import entities.QdQExecHistory;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.Date;
import java.util.List;

@Mapper
public interface QdQExecHistoryMapper {
    @Select("SELECT * " +
            "FROM qd_q_exec_history " +
            "WHERE started_at BETWEEN #{startDate} AND #{endDate} " +
            "ORDER BY started_at")
    List<QdQExecHistory> getDataFromLast7Days(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @InsertProvider(type= SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<QdQExecHistory> insertStatement);
}
package com.example.demo.repository.impl;

import com.example.demo.repository.QdQExecPatternRepository;
import com.example.demo.repository.mapper.QdQExecHistoryMapper;
import com.example.demo.utils.BuildTableUtils;
import entities.QdQExecHistory;
import entities.QdQExecHistoryDynamicSqlSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.insertMultiple;

@Slf4j
@RequiredArgsConstructor
@Service
public class QdQExecPatternRepositoryImpl implements QdQExecPatternRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QdQExecHistoryMapper qdQExecHistoryMapper;


    @Override
    public void createCustomTable(String newTableName) {

        // Create a new table
        try {
            String createTableSQL = buildCreateTable(newTableName);
            jdbcTemplate.execute(createTableSQL);
            log.info("Table {} was created", newTableName);
        } catch (UncategorizedSQLException | IllegalAccessException e) {
            log.error("Cannot create table {}, it could be created: \n" +
                    "{}", newTableName, e.getMessage());
        }
    }

    @Override
    public boolean insertBatch(String tableName, List<QdQExecHistory> qdQExecHistories) {
        try {
            MultiRowInsertStatementProvider<QdQExecHistory> multiRowInsert = insertMultiple(qdQExecHistories)
                    .into(QdQExecHistoryDynamicSqlSupport.qdQExecHistory.withName(tableName))
                    .map(QdQExecHistoryDynamicSqlSupport.execHistoryId).toProperty("execHistoryId")
                    .map(QdQExecHistoryDynamicSqlSupport.startedAt).toProperty("startedAt")
                    .map(QdQExecHistoryDynamicSqlSupport.finishedAt).toProperty("finishedAt")
                    .map(QdQExecHistoryDynamicSqlSupport.duration).toProperty("duration")
                    .map(QdQExecHistoryDynamicSqlSupport.execStatusId).toProperty("execStatusId")
                    .map(QdQExecHistoryDynamicSqlSupport.execTypeId).toProperty("execTypeId")
                    .map(QdQExecHistoryDynamicSqlSupport.schedName).toProperty("schedName")
                    .map(QdQExecHistoryDynamicSqlSupport.schedInstanceId).toProperty("schedInstanceId")
                    .map(QdQExecHistoryDynamicSqlSupport.schedObjectName).toProperty("schedObjectName")
                    .map(QdQExecHistoryDynamicSqlSupport.schedVersion).toProperty("schedVersion")
                    .map(QdQExecHistoryDynamicSqlSupport.jobGroupName).toProperty("jobGroupName")
                    .map(QdQExecHistoryDynamicSqlSupport.jobName).toProperty("jobName")
                    .map(QdQExecHistoryDynamicSqlSupport.jobClassName).toProperty("jobClassName")
                    .map(QdQExecHistoryDynamicSqlSupport.jobClassLocation).toProperty("jobClassLocation")
                    .map(QdQExecHistoryDynamicSqlSupport.jobDataMap).toProperty("jobDataMap")
                    .map(QdQExecHistoryDynamicSqlSupport.jobExecutionId).toProperty("jobExecutionId")
                    .map(QdQExecHistoryDynamicSqlSupport.triggerGroupName).toProperty("triggerGroupName")
                    .map(QdQExecHistoryDynamicSqlSupport.triggerName).toProperty("triggerName")
                    .map(QdQExecHistoryDynamicSqlSupport.calendarName).toProperty("calendarName")
                    .map(QdQExecHistoryDynamicSqlSupport.result).toProperty("result")
                    .map(QdQExecHistoryDynamicSqlSupport.error).toProperty("error")
                    .map(QdQExecHistoryDynamicSqlSupport.userData).toProperty("userData")
                    .map(QdQExecHistoryDynamicSqlSupport.log).toProperty("log")
                    .map(QdQExecHistoryDynamicSqlSupport.logStoredCount).toProperty("logStoredCount")
                    .map(QdQExecHistoryDynamicSqlSupport.logTotalCount).toProperty("logTotalCount")
                    .map(QdQExecHistoryDynamicSqlSupport.threadGroupName).toProperty("threadGroupName")
                    .map(QdQExecHistoryDynamicSqlSupport.threadName).toProperty("threadName")
                    .map(QdQExecHistoryDynamicSqlSupport.threadPriority).toProperty("threadPriority")
                    .map(QdQExecHistoryDynamicSqlSupport.hostName).toProperty("hostName")
                    .map(QdQExecHistoryDynamicSqlSupport.jvmPid).toProperty("jvmPid")
                    .map(QdQExecHistoryDynamicSqlSupport.jvmName).toProperty("jvmName")
                    .map(QdQExecHistoryDynamicSqlSupport.jvmVendor).toProperty("jvmVendor")
                    .map(QdQExecHistoryDynamicSqlSupport.jvmVersion).toProperty("jvmVersion")
                    .map(QdQExecHistoryDynamicSqlSupport.jvmRuntimeVersion).toProperty("jvmRuntimeVersion")
                    .map(QdQExecHistoryDynamicSqlSupport.osName).toProperty("osName")
                    .map(QdQExecHistoryDynamicSqlSupport.osVersion).toProperty("osVersion")
                    .map(QdQExecHistoryDynamicSqlSupport.osArch).toProperty("osArch")
                    .map(QdQExecHistoryDynamicSqlSupport.jcId).toProperty("jcId")
                    .map(QdQExecHistoryDynamicSqlSupport.jcFlowId).toProperty("jcFlowId")
                    .map(QdQExecHistoryDynamicSqlSupport.jcSrcEhId).toProperty("jcSrcEhId")
                    .map(QdQExecHistoryDynamicSqlSupport.isIndexed).toProperty("isIndexed")
                    .build()
                    .render(RenderingStrategies.MYBATIS3);

            int rows = qdQExecHistoryMapper.insertMultiple(multiRowInsert);
            if (rows == qdQExecHistories.size()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("Cannot insert data for: {}", tableName, e);
        }
        return Boolean.FALSE;
    }

    private String buildCreateTable(String tableName) throws IllegalAccessException {
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE ")
                .append(tableName).append(" (");

        List<String> types = BuildTableUtils.getActualTypeForSqlColumn(QdQExecHistoryDynamicSqlSupport.QdQExecHistoryDynamic.class);

        Map<String, String> fieldTypeMap = BuildTableUtils.getFieldType(new QdQExecHistoryDynamicSqlSupport.QdQExecHistoryDynamic(), types);

        int order = 0;
        for (Map.Entry<String, String> fieldType : fieldTypeMap.entrySet()) {
            order++;

            createTableSQL.append(fieldType.getKey()).append(" ")
                    .append(BuildTableUtils.convertToSqlType(fieldType.getValue()));

            // Define primary key
            if (order == 1) {
                createTableSQL.append(" NOT NULL PRIMARY KEY, ");
            } else if (order < types.size()) {
                createTableSQL.append(", ");
            } else if (order == types.size()) {
                createTableSQL.append(")");
            }
        }

        return createTableSQL.toString();
    }
}

package entities;

import lombok.Getter;
import lombok.Setter;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.util.Date;

public class QdQExecHistoryDynamicSqlSupport {
    public static final QdQExecHistoryDynamic qdQExecHistory = new QdQExecHistoryDynamic();
    public static final SqlColumn<Long> execHistoryId = qdQExecHistory.execHistoryId;
    public static final SqlColumn<Date> startedAt = qdQExecHistory.startedAt;
    public static final SqlColumn<Date> finishedAt = qdQExecHistory.finishedAt;

    public static final SqlColumn<Integer> duration = qdQExecHistory.duration;

    public static final SqlColumn<Integer> execStatusId = qdQExecHistory.execStatusId;

    public static final SqlColumn<Integer> execTypeId = qdQExecHistory.execTypeId;

    public static final SqlColumn<String> schedName = qdQExecHistory.schedName;

    public static final SqlColumn<String> schedInstanceId = qdQExecHistory.schedInstanceId;

    public static final SqlColumn<String> schedObjectName = qdQExecHistory.schedObjectName;

    public static final SqlColumn<String> schedVersion = qdQExecHistory.schedVersion;

    public static final SqlColumn<String> jobGroupName = qdQExecHistory.jobGroupName;

    public static final SqlColumn<String> jobName = qdQExecHistory.jobName;

    public static final SqlColumn<String> jobClassName = qdQExecHistory.jobClassName;

    public static final SqlColumn<String> jobClassLocation = qdQExecHistory.jobClassLocation;

    public static final SqlColumn<String> jobDataMap = qdQExecHistory.jobDataMap;

    public static final SqlColumn<String> jobExecutionId = qdQExecHistory.jobExecutionId;

    public static final SqlColumn<String> triggerGroupName = qdQExecHistory.triggerGroupName;

    public static final SqlColumn<String> triggerName = qdQExecHistory.triggerName;

    public static final SqlColumn<String> calendarName = qdQExecHistory.calendarName;

    public static final SqlColumn<String> result = qdQExecHistory.result;

    public static final SqlColumn<String> error = qdQExecHistory.error;

    public static final SqlColumn<String> userData = qdQExecHistory.userData;

    public static final SqlColumn<String> log = qdQExecHistory.log;

    public static final SqlColumn<Integer> logStoredCount = qdQExecHistory.logStoredCount;

    public static final SqlColumn<Integer> logTotalCount = qdQExecHistory.logTotalCount;

    public static final SqlColumn<String> threadGroupName = qdQExecHistory.threadGroupName;

    public static final SqlColumn<String> threadName = qdQExecHistory.threadName;

    public static final SqlColumn<Integer> threadPriority = qdQExecHistory.threadPriority;

    public static final SqlColumn<String> hostName = qdQExecHistory.hostName;

    public static final SqlColumn<Integer> jvmPid = qdQExecHistory.jvmPid;

    public static final SqlColumn<String> jvmName = qdQExecHistory.jvmName;

    public static final SqlColumn<String> jvmVendor = qdQExecHistory.jvmVendor;

    public static final SqlColumn<String> jvmVersion = qdQExecHistory.jvmVersion;

    public static final SqlColumn<String> jvmRuntimeVersion = qdQExecHistory.jvmRuntimeVersion;

    public static final SqlColumn<String> osName = qdQExecHistory.osName;

    public static final SqlColumn<String> osVersion = qdQExecHistory.osVersion;

    public static final SqlColumn<String> osArch = qdQExecHistory.osArch;

    public static final SqlColumn<Long> jcId = qdQExecHistory.jcId;

    public static final SqlColumn<String> jcFlowId = qdQExecHistory.jcFlowId;

    public static final SqlColumn<Long> jcSrcEhId = qdQExecHistory.jcSrcEhId;

    public static final SqlColumn<Integer> isIndexed = qdQExecHistory.isIndexed;

    @Getter
    @Setter
    public static final class QdQExecHistoryDynamic extends AliasableSqlTable<QdQExecHistoryDynamic> {
        public final SqlColumn<Long> execHistoryId = column("exec_history_id", JDBCType.BIGINT);
        public final SqlColumn<Date> startedAt = column("started_at", JDBCType.DATE);
        public final SqlColumn<Date> finishedAt = column("finished_at", JDBCType.DATE);
        public final SqlColumn<Integer> duration = column("duration", JDBCType.INTEGER);
        public final SqlColumn<Integer> execStatusId = column("exec_status_id", JDBCType.INTEGER);
        public final SqlColumn<Integer> execTypeId = column("exec_type_id", JDBCType.INTEGER);
        public final SqlColumn<String> schedName = column("sched_name", JDBCType.VARCHAR);
        public final SqlColumn<String> schedInstanceId = column("sched_instance_id", JDBCType.VARCHAR);
        public final SqlColumn<String> schedObjectName = column("sched_object_name", JDBCType.VARCHAR);
        public final SqlColumn<String> schedVersion = column("sched_version", JDBCType.VARCHAR);
        public final SqlColumn<String> jobGroupName = column("job_group_name", JDBCType.VARCHAR);
        public final SqlColumn<String> jobName = column("job_name", JDBCType.VARCHAR);
        public final SqlColumn<String> jobClassName = column("job_class_name", JDBCType.VARCHAR);
        public final SqlColumn<String> jobClassLocation = column("job_class_location", JDBCType.VARCHAR);
        public final SqlColumn<String> jobDataMap = column("job_data_map", JDBCType.VARCHAR);
        public final SqlColumn<String> jobExecutionId = column("job_execution_id", JDBCType.VARCHAR);
        public final SqlColumn<String> triggerGroupName = column("trigger_group_name", JDBCType.VARCHAR);
        public final SqlColumn<String> triggerName = column("trigger_name", JDBCType.VARCHAR);
        public final SqlColumn<String> calendarName = column("calendar_name", JDBCType.VARCHAR);
        public final SqlColumn<String> result = column("result", JDBCType.VARCHAR);
        public final SqlColumn<String> error = column("error", JDBCType.VARCHAR);
        public final SqlColumn<String> userData = column("user_data", JDBCType.VARCHAR);
        public final SqlColumn<String> log = column("log", JDBCType.VARCHAR);
        public final SqlColumn<Integer> logStoredCount = column("log_stored_count", JDBCType.INTEGER);
        public final SqlColumn<Integer> logTotalCount = column("log_total_count", JDBCType.INTEGER);
        public final SqlColumn<String> threadGroupName = column("thread_group_name", JDBCType.VARCHAR);
        public final SqlColumn<String> threadName = column("thread_name", JDBCType.VARCHAR);
        public final SqlColumn<Integer> threadPriority = column("thread_priority", JDBCType.INTEGER);
        public final SqlColumn<String> hostName = column("host_name", JDBCType.VARCHAR);
        public final SqlColumn<Integer> jvmPid = column("jvm_pid", JDBCType.INTEGER);
        public final SqlColumn<String> jvmName = column("jvm_name", JDBCType.VARCHAR);
        public final SqlColumn<String> jvmVendor = column("jvm_vendor", JDBCType.VARCHAR);
        public final SqlColumn<String> jvmVersion = column("jvm_version", JDBCType.VARCHAR);
        public final SqlColumn<String> jvmRuntimeVersion = column("jvm_runtime_version", JDBCType.VARCHAR);
        public final SqlColumn<String> osName = column("os_name", JDBCType.VARCHAR);
        public final SqlColumn<String> osVersion = column("os_version", JDBCType.VARCHAR);
        public final SqlColumn<String> osArch = column("os_arch", JDBCType.VARCHAR);
        public final SqlColumn<Long> jcId = column("jc_id", JDBCType.BIGINT);
        public final SqlColumn<String> jcFlowId = column("jc_flow_id", JDBCType.VARCHAR);
        public final SqlColumn<Long> jcSrcEhId = column("jc_src_eh_id", JDBCType.BIGINT);
        public final SqlColumn<Integer> isIndexed = column("is_indexed", JDBCType.INTEGER);

        public QdQExecHistoryDynamic() {
            super("qd_q_exec_history", QdQExecHistoryDynamic::new);
        }
    }
}

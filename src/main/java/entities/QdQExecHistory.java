package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class QdQExecHistory {

    private Long execHistoryId;

    private Date startedAt;

    private Date finishedAt;

    private Integer duration;

    private Integer execStatusId;

    private Integer execTypeId;

    private String schedName;

    private String schedInstanceId;

    private String schedObjectName;

    private String schedVersion;

    private String jobGroupName;

    private String jobName;

    private String jobClassName;

    private String jobClassLocation;

    private String jobDataMap;

    private String jobExecutionId;

    private String triggerGroupName;

    private String triggerName;

    private String calendarName;

    private String result;

    private String error;

    private String userData;

    private String log;

    private Integer logStoredCount;

    private Integer logTotalCount;

    private String threadGroupName;

    private String threadName;

    private Integer threadPriority;

    private String hostName;

    private Integer jvmPid;

    private String jvmName;

    private String jvmVendor;

    private String jvmVersion;

    private String jvmRuntimeVersion;

    private String osName;

    private String osVersion;

    private String osArch;

    private Long jcId;

    private String jcFlowId;

    private Long jcSrcEhId;

    private Integer isIndexed;
}

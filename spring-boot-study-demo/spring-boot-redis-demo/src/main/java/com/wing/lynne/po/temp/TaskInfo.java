package com.wing.lynne.po.temp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@REntity
public class TaskInfo {

    @RId
    private String redisKey;
    private String basicInfo;
    private String uniqueCode;
    private int userId;
    private int taskType;
    private int stage;
    private int status;
    private int priority;
    private List<String> invokeTaskList;
    private Set<String> dependenceTaskCodeSet;
    private int taskTraceLogId;
    private String errorLog;
    private long startMillis;
    private List<String> completeSubTask;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BasicInfo {
        private long startTime;
        private long endTime;
        private int saveDatabaseId;
        private String saveTableName;
        private int timeGrain;
        private ConfigInfo configInfo;
        private int formId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConfigInfo {
        private int type;
        private CalculateTaskConfigInfo calculateTaskInfo;
        private QueryTaskConfigInfo queryTaskInfo;
        private CompositeFieldTaskConfigInfo compositeFieldTaskConfigInfo;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QueryTaskConfigInfo {

        private int sourceDatabaseId;
        private List<String> sourceTables;
        private List<TaskColumnInfo> columns;
        private List<TaskColumnInfo> groupColumns;
        private String querySql;
        private List<TaskTimeFilterInfo> timeFilters;
    }

    @Data
    @Builder
    public static class CompositeFieldTaskConfigInfo {

        private Integer configSetId;

        private Integer formId;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalculateTaskConfigInfo {

        private TaskColumnInfo column;
        private List<TaskColumnInfo> groupColumns;
        private int type;
        private int sameRatioGrain;
        private String leftColumn;
        private String leftTaskCode;
        private byte operator;
        private String rightColumn;
        private String rightTaskCode;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskColumnInfo {
        private String columnTitle;
        private String queryColumnName;
        private String saveColumnName;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskTimeFilterInfo {
        private int filterType;
        private int patternType;
        private String startReplaceStr;
        private String endReplaceStr;
        private String timePattern;
        private int customValue;
    }

}

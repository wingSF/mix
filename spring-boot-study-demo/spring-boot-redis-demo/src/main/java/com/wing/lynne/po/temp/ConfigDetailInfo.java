package com.wing.lynne.po.temp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigDetailInfo {


    private int id;
    private String configName;
    private byte timeGrain;
    private String timeGrainName;
    private List<Integer> groups;
    private List<String> groupNames;
    private List<ConfigSetInfo> configSets;
    private boolean confirmed;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConfigSetInfo {
        private int setId;
        private byte type;
        private String configName;
        private CalculateConfigInfo calculateConfig;
        private SqlConfigInfo sqlConfig;
        private CompositeFieldInfo compositeFieldInfo;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CalculateConfigInfo {
        private int columnId;
        private String columnAsName;
        private String columnTitle;
        private String leftColumnAsName;
        private byte operator;
        private String rightColumnAsName;
        private int ratioType;
        private int ratioPeriod;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SqlConfigInfo {
        private List<ConfigSourceInfo> sourceList;
        private String sql;
        private List<ConfigColumnInfo> configColumns;
        private List<ConfigTimeFilterInfo> configTimeFilters;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConfigSourceInfo {
        private int databaseId;
        private String databaseName;
        private List<OneLayerCheckItem> tables;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConfigColumnInfo {
        private int columnId;
        private String columnAsName;
        private String columnTitle;
        private boolean group;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConfigTimeFilterInfo {
        private byte timeFilterType;
        private byte timePatternType;
        private String timeReplaceStr;
        private String timePatternStr;
        private int customValue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompositeFieldInfo {
        private int columnId;
        private String columnAsName;
        private String columnTitle;
        private List<CompositeFieldDetail> compositeFieldDetailList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompositeFieldDetail {

        private List<CompositeFieldKeyValue> compositeFieldKeyValueList;
        private Integer formulaId;
        private String type;
        private String state;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompositeFieldKeyValue {

        private String type;
        private TermInfo termInfo;

    }

    @Data
    public static class TermInfo{

        private String value;

        private String name;

        private String title;
    }
}

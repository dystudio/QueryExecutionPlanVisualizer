package com.nydia.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Plan {

    @SerializedName("Node Type")
    @Expose
    private String nodeType;
    @SerializedName("Strategy")
    @Expose
    private String strategy;
    @SerializedName("Partial Mode")
    @Expose
    private String partialMode;
    @SerializedName("Parallel Aware")
    @Expose
    private Boolean parallelAware;
    @SerializedName("Relation Name")
    @Expose
    private String relationName;
    @SerializedName("Alias")
    @Expose
    private String alias;
    @SerializedName("Startup Cost")
    @Expose
    private Double startupCost;
    @SerializedName("Total Cost")
    @Expose
    private Double totalCost;
    @SerializedName("Plan Rows")
    @Expose
    private Integer planRows;
    @SerializedName("Workers Planned")
    @Expose
    private Integer workersPlanned;
    @SerializedName("Single Copy")
    @Expose
    private Boolean singleCopy;
    @SerializedName("Plan Width")
    @Expose
    private Integer planWidth;
    @SerializedName("Filter")
    @Expose
    private String filter;
    @SerializedName("Group Key")
    @Expose
    private List<String> groupKey = null;
    @SerializedName("Sort Key")
    @Expose
    private List<String> sortKey = null;
    @SerializedName("Plans")
    @Expose
    private List<Plan> plans = null;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getPartialMode() {
        return partialMode;
    }

    public void setPartialMode(String partialMode) {
        this.partialMode = partialMode;
    }

    public Boolean getParallelAware() {
        return parallelAware;
    }

    public void setParallelAware(Boolean parallelAware) {
        this.parallelAware = parallelAware;
    }

    public Double getStartupCost() {
        return startupCost;
    }

    public void setStartupCost(Double startupCost) {
        this.startupCost = startupCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getPlanRows() {
        return planRows;
    }

    public void setPlanRows(Integer planRows) {
        this.planRows = planRows;
    }

    public Integer getPlanWidth() {
        return planWidth;
    }

    public void setPlanWidth(Integer planWidth) {
        this.planWidth = planWidth;
    }

    public List<String> getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(List<String> groupKey) {
        this.groupKey = groupKey;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public Boolean getSingleCopy() {
        return singleCopy;
    }

    public void setSingleCopy(Boolean singleCopy) {
        this.singleCopy = singleCopy;
    }

    public Integer getWorkersPlanned() {
        return workersPlanned;
    }

    public void setWorkersPlanned(Integer workersPlanned) {
        this.workersPlanned = workersPlanned;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<String> getSortKey() {
        return sortKey;
    }

    public void setSortKey(List<String> sortKey) {
        this.sortKey = sortKey;
    }
}

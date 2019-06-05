package com.czarea.rest.vo;

import java.io.Serializable;

/**
 * @author zhouzx
 */
public class PageRequest<F> implements Serializable {

    private static final long serialVersionUID = -4976202359298582250L;
    private Integer offset = 0;
    private Integer limit = 20;
    private Long total = -1L;

    private F filter;
    private String sort;
    private boolean isAsc = true;

    public PageRequest() {
    }

    public PageRequest(Integer offset, Integer limit, F filter) {
        this.offset = offset;
        this.limit = limit;
        this.filter = filter;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public F getFilter() {
        return filter;
    }

    public void setFilter(F filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }
}

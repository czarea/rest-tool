package com.czarea.rest.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表实体类
 *
 * @author zhouzx
 * @since 1.0
 */
public class Grid<T> {

    /**
     * 总数
     **/
    private long total = 0;
    /**
     * 总页数
     **/
    private long pages = 0;

    /**
     * 列表内容
     **/
    private List<T> list = new ArrayList<>();

    public Grid() {
    }

    public Grid(long total, long pages, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Grid [total=" + total + ", pages=" + pages + ", list=" + list + "]";
    }

}

package com.chelsea.wiki.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq { // 做分页的
    @NotNull(message = "The page number cannot be empty.")
    private int page; // PageNum

    @NotNull(message = "The number of items per page cannot be empty.")
    @Max(value = 1000, message = "The number of items per page cannot exceed 1000.")
    private int size; // PageSize

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() { // 打印日志
        final StringBuffer sb = new StringBuffer("PageReq{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append("}");
        return sb.toString();
    }
}
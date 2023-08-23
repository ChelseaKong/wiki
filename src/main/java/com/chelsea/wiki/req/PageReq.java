package com.chelsea.wiki.req;

public class PageReq { // 做分页的
    private int page; // PageNum

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
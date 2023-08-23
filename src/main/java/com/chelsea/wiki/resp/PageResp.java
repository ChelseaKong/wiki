package com.chelsea.wiki.resp;

import java.util.List;

public class PageResp<T> { // 做分页的
    private long total; // 总行数

    private List<T> list; // 当前页的列表内容

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() { // 打印日志
        final StringBuffer sb = new StringBuffer("PageResp{");
        sb.append("total=").append(total);
        sb.append(", list=").append(list);
        sb.append("}");
        return sb.toString();
    }
}
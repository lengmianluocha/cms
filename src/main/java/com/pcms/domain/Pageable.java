package com.pcms.domain;


import java.util.List;

/**
 * 分页信息
 */
public class Pageable<T> {

    private List<T> data;

    private Long total;

    private Integer draw;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }
}

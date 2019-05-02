package com.app.finlit.data.shared;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PageResponse<TModel> extends CommonResponse{

    public List<TModel> items;
    public List<TModel> list;
    public int total;
    public int pageNo;
    public int pageSize;
    public int totalRecords;

}

package com.app.finlit.data.shared;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataResponse<Model> extends CommonResponse{

    public Model data;
}

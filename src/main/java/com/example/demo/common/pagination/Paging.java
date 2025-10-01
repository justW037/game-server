package com.example.demo.common.pagination;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Paging implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 30;
    public static final int MAX_PAGE_SIZE = 5000;

    private long total = 0;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int pageIndex = 0;

    private List<?> list = Collections.EMPTY_LIST;

}

package com.example.demo.common.pagination;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BasePageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer pageIndex = 0;

	private Integer pageSize = Paging.DEFAULT_PAGE_SIZE;

	private String keyword;
}

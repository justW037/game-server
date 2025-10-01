package com.example.demo.common.pagination;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.common.utils.CommonUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public abstract class BasePageOrderParam extends BasePageParam {
	private static final long serialVersionUID = 1L;

	private List<OrderItem> pageSorts;

	public String buildOrderByClause(String prefix, List<String> allowedColumns) {
		if (CommonUtil.empty(pageSorts)) {
			return "";
		}

		return pageSorts.stream()
				.filter(item -> item.getCol() != null
						&& (CommonUtil.empty(allowedColumns) || allowedColumns.contains(item.getCol().toUpperCase())))
				.map(item -> item.getOrderBy(prefix)).collect(Collectors.joining(", "));
	}
}

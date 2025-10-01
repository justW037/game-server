package com.example.demo.common.pagination;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationHelper {
    /**
     * Phân trang với count + list + optional fallback.
     *
     * @param filter          param phân trang (phải extends BasePageParam)
     * @param countFn         hàm đếm tổng record
     * @param listFn          hàm lấy danh sách dữ liệu
     * @param fallbackIfEmpty logic fallback nếu total = 0 (ví dụ: insert default data)
     * @param <F>             kiểu filter
     * @param <T>             kiểu dữ liệu trong danh sách
     * @return Paging chứa total + list
     */
    public static <F extends BasePageParam, T> Paging paginate(
            F filter,
            Function<F, Long> countFn,
            Function<F, List<T>> listFn,
            Consumer<F> fallbackIfEmpty
    ) {
        // validate pageIndex và pageSize
        if (filter.getPageIndex() == null || filter.getPageIndex() < 0) {
            filter.setPageIndex(0);
        }
        if (filter.getPageSize() == null || filter.getPageSize() <= 0) {
            filter.setPageSize(Paging.DEFAULT_PAGE_SIZE);
        }
        if (filter.getPageSize() > Paging.MAX_PAGE_SIZE) {
            filter.setPageSize(Paging.MAX_PAGE_SIZE);
        }

        // count
        long total = countFn.apply(filter);

        Paging page = new Paging()
                .setPageIndex(filter.getPageIndex())
                .setPageSize(filter.getPageSize())
                .setTotal(total);

        // fallback khi empty
        if (total <= 0 && fallbackIfEmpty != null) {
            fallbackIfEmpty.accept(filter);
            total = countFn.apply(filter);
            page.setTotal(total);
        }

        // list
        if (total > 0) {
            List<T> data = listFn.apply(filter);
            page.setList(data);
        }

        return page;
    }

    /**
     * Overload không có fallback.
     */
    public static <F extends BasePageParam, T> Paging paginate(
            F filter,
            Function<F, Long> countFn,
            Function<F, List<T>> listFn
    ) {
        return paginate(filter, countFn, listFn, null);
    }

    /**
     * paginate với filter + repo function (repo chỉ cần PageRequest)
     */
    public static <T, F extends BasePageOrderParam> Paging paginate(
            F filter,
            Function<Pageable, Page<T>> repoFn
    ) {
        Pageable pageable = toPageable(filter);
        Page<T> springPage = repoFn.apply(pageable);
        return fromPage(springPage);
    }

    /**
     * paginate với filter + repo function nhận filter và pageable
     */
    public static <T, F extends BasePageOrderParam> Paging paginate(
            F filter,
            BiFunction<F, Pageable, Page<T>> repoFn
    ) {
        Pageable pageable = toPageable(filter);
        Page<T> springPage = repoFn.apply(filter, pageable);
        return fromPage(springPage);
    }

     /**
     * Convert BasePageOrderParam -> Pageable
     */
    public static Pageable toPageable(BasePageOrderParam filter) {
        int pageIndex = (filter.getPageIndex() == null || filter.getPageIndex() < 0) ? 0 : filter.getPageIndex() - 1;
        int pageSize = (filter.getPageSize() == null || filter.getPageSize() <= 0)
                ? Paging.DEFAULT_PAGE_SIZE
                : Math.min(filter.getPageSize(), Paging.MAX_PAGE_SIZE);

        // build sort từ pageSorts
        Sort sort = Sort.unsorted();
        if (filter.getPageSorts() != null && !filter.getPageSorts().isEmpty()) {
            List<Sort.Order> orders = filter.getPageSorts().stream()
                    .filter(i -> i.getCol() != null)
                    .map(i -> {
                        if (OrderItem.DESC.equalsIgnoreCase(i.getDir())) {
                            return Sort.Order.desc(i.getCol());
                        } else {
                            return Sort.Order.asc(i.getCol());
                        }
                    }).collect(Collectors.toList());
            if (!orders.isEmpty()) {
                sort = Sort.by(orders);
            }
        }

        return PageRequest.of(pageIndex, pageSize, sort);
    }

    public static <T> Paging fromPage(Page<T> springPage) {
    return new Paging()
            .setPageIndex(springPage.getNumber() + 1) 
            .setPageSize(springPage.getSize())
            .setTotal(springPage.getTotalElements())
            .setList(springPage.getContent());
    }

}

package com.example.demo.common.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.common.contexts.DatabaseContext;
import com.example.demo.common.pagination.BasePageOrderParam;
import com.example.demo.common.pagination.PaginationHelper;
import com.example.demo.common.pagination.Paging;
import com.example.demo.common.sql.JoinSpec;
import com.example.demo.common.utils.CommonUtil;
import com.example.demo.common.utils.EntityMetadataResolver;
import com.example.demo.common.utils.ReflectionUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractNativeDao<T, F extends BasePageOrderParam>  {

    protected final EntityManager em;
    protected final String tableName;
    protected final String primaryKey;

    public AbstractNativeDao(EntityManager em, Class<?> entityClass) {
        this.em = em;
        this.tableName = EntityMetadataResolver.resolveTableName(entityClass);
        this.primaryKey = EntityMetadataResolver.resolvePrimaryKey(entityClass);
    }

    /** Cột cần SELECT */
    protected abstract String getSelectColumns();

    /** Tên result mapping (Entity/DTO) */
    protected abstract String getResultMappingName();

    /** Cột mặc định để search keyword */
    protected List<String> getSearchableColumns() {
        return Collections.emptyList();
    }

    /** Map filter field -> column name */
    protected Map<String, String> getFilterableColumns() {
        return Collections.emptyMap();
    }

    /** Danh sách JOIN cần thêm */
    protected List<JoinSpec> getJoins() {
        return Collections.emptyList(); // mặc định không join
    }

    /** Ghép join clause */
    protected String buildJoinClause() {
        return getJoins().stream()
                .map(JoinSpec::toSql)
                .collect(Collectors.joining(" "));
    }

    /** Pagination SQL tuỳ DB */
    protected String getPaginationClause(int offset, int pageSize) {
        return DatabaseContext.getDbType().formatPagination(offset, pageSize);
    }

    public long count(F filter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(" + primaryKey + ") FROM " + tableName + " t ");
        sql.append(buildJoinClause());
        sql.append(" WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();
        buildWhereClause(filter, sql, params);

        Query q = em.createNativeQuery(sql.toString());
        params.forEach(q::setParameter);

        return ((Number) q.getSingleResult()).longValue();
    }

    public List<T> findAll(F filter) {
        StringBuilder sql = new StringBuilder("SELECT " + getSelectColumns() + " FROM " + tableName + " t ");
        sql.append(buildJoinClause());
        sql.append(" WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();
        buildWhereClause(filter, sql, params);

        // order by
        String orderBy = filter.buildOrderByClause("", Arrays.asList(getSelectColumns().split(",")));
        if (CommonUtil.empty(orderBy)) {
            orderBy = "t." + primaryKey + " DESC";
        }
        sql.append(" ORDER BY ").append(orderBy);

        // pagination
        int pageIndex = (filter.getPageIndex() == null || filter.getPageIndex() < 1) ? 1 : filter.getPageIndex();
        int offset = (pageIndex - 1) * filter.getPageSize();
        sql.append(getPaginationClause(offset, filter.getPageSize()));

        Query q = em.createNativeQuery(sql.toString(), getResultMappingName());
        params.forEach(q::setParameter);

        return q.getResultList();
    }

    /** Default where clause với keyword + filterableColumns */
    protected void buildWhereClause(F filter, StringBuilder sql, Map<String, Object> params) {
        // keyword search
        if (CommonUtil.notEmpty(filter.getKeyword()) && !getSearchableColumns().isEmpty()) {
            sql.append(" AND (");
            List<String> conditions = new ArrayList<>();
            for (int i = 0; i < getSearchableColumns().size(); i++) {
                String col = getSearchableColumns().get(i);
                String param = "kw" + i;
                conditions.add("LOWER(" + col + ") LIKE :" + param);
                params.put(param, "%" + filter.getKeyword().toLowerCase() + "%");
            }
            sql.append(String.join(" OR ", conditions)).append(")");
        }

        // filter fields
        getFilterableColumns().forEach((field, column) -> {
            Object value = ReflectionUtil.getFieldValue(filter, field);
            if (value != null) {
                String param = "p_" + field;
                sql.append(" AND ").append(column).append(" = :").append(param);
                params.put(param, value);
            }
        });
    }

    /** Paging wrapper */
    public Paging paginate(F filter) {
        return PaginationHelper.paginate(
            filter,
            this::count,
            this::findAll
        );
    }
}



// @Repository
// public class MetadataDao extends AbstractNativeDao<MetadataListItemDTO, MetadataParam> {
//     @Autowired
//     public MetadataDao(@Qualifier("writeEntityManagerFactory") EntityManager em) {
//         super(em, "KPI_METADATA", "ID");
//     }

//     @Override
//     protected String getSelectColumns() {
//         return "ID,CODE,TYPE,NAME,AVATAR,ICON,PARENT_ID,PRIORITY,STATUS,CREATED_AT,EXTRA,DESCRIPTION";
//     }

//     @Override
//     protected String getResultMappingName() {
//         return "metadataListItemDTOMapping";
//     }

//     @Override
//     protected List<String> getSearchableColumns() {
//         return Arrays.asList("NAME", "SLUG", "CODE");
//     }

//     @Override
//     protected Map<String, String> getFilterableColumns() {
//         Map<String, String> map = new HashMap<>();
//         map.put("metaType", "TYPE");
//         map.put("parentId", "PARENT_ID");
//         map.put("status", "STATUS");
//         return map;
//     }
// }
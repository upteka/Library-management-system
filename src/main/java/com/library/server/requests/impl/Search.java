package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.EntityList;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

public class Search<T extends Entity> implements Request<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Search.class);
    private final String action = "search";
    private final BaseService<T> baseService;

    public Search(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public ResponsePack<T> handle(RequestPack<? extends Entity> requestPack) {
        List<String> params = requestPack.getParams();
        if (params == null || params.isEmpty()) {
            return new ResponsePack<>(action, "缺少请求参数", null, false);
        }

        try {
            if (params.getFirst().equalsIgnoreCase("search")) {
                SearchParameters searchParams = parseSearchParameters(params);
                return searchEntities(searchParams);
            } else {
                return new ResponsePack<>(action, "无效的请求参数", null, false);
            }
        } catch (Exception e) {
            LOGGER.error("处理请求时出错: {}", e.getMessage(), e);
            return new ResponsePack<>(action, "处理请求时出错: " + e.getMessage(), null, false);
        }
    }


    private SearchParameters parseSearchParameters(List<String> params) throws ClassNotFoundException {
        String fieldName = params.get(1);
        Object value = "0".equals(params.get(2)) ? null : params.get(2);
        String condition = params.get(3);
        int limit = Integer.parseInt(params.get(4));
        String sortField = params.size() > 5 ? params.get(5) : null;
        String sortOrder = params.size() > 6 ? params.get(6) : null;
        int page = params.size() > 7 ? Integer.parseInt(params.get(7)) : 1;
        int pageSize = params.size() > 8 ? Integer.parseInt(params.get(8)) : 10;
        boolean caseInsensitive = params.size() > 9 && Boolean.parseBoolean(params.get(9));
        String logicalOperator = params.size() > 10 ? params.get(10) : "AND";
        String[] selectedFields = params.size() > 11 ? params.subList(11, params.size()).toArray(new String[0]) : null;
        boolean distinct = params.size() > 12 && Boolean.parseBoolean(params.get(12));
        Timestamp startDate = params.size() > 13 ? Timestamp.valueOf(params.get(13)) : null;
        Timestamp endDate = params.size() > 14 ? Timestamp.valueOf(params.get(14)) : null;
        String dateField = params.size() > 15 ? params.get(15) : null;
        Class<?> customEntityType = params.size() > 16 ? Class.forName(params.get(16)) : null;

        return new SearchParameters(fieldName, value, condition, limit, sortField, sortOrder, page, pageSize, caseInsensitive, logicalOperator, selectedFields, distinct, startDate, endDate, dateField, customEntityType);
    }

    private ResponsePack<T> searchEntities(SearchParameters params) {
        List<T> entities = baseService.search(
                params.fieldName, params.value, params.condition, params.limit, params.sortField, params.sortOrder,
                params.page, params.pageSize, params.caseInsensitive, params.logicalOperator, params.selectedFields,
                params.distinct, params.startDate, params.endDate, params.dateField, params.customEntityType
        );

        // 数据脱敏：清除 User 对象的敏感信息
        entities.forEach(entity -> {
            if (entity instanceof User) {
                ((User) entity).setPassword(null);
                ((User) entity).setPhone(null);
                ((User) entity).setEmail(null);
                ((User) entity).setRole(null);
            }
        });

        if (!entities.isEmpty()) {
            return new ResponsePack<>(action, "获取实体成功", (T) new EntityList<>(entities), true);
        } else {
            return new ResponsePack<>(action, "未找到符合条件的实体", null, false);
        }
    }

    private static class SearchParameters {
        String fieldName;
        Object value;
        String condition;
        int limit;
        String sortField;
        String sortOrder;
        int page;
        int pageSize;
        boolean caseInsensitive;
        String logicalOperator;
        String[] selectedFields;
        boolean distinct;
        Timestamp startDate;
        Timestamp endDate;
        String dateField;
        Class<?> customEntityType;

        /**
         * 搜索方法，根据指定字段、值、条件等进行查询，并返回结果列表。
         *
         * @param fieldName        要查询的字段名。
         * @param value            查询的值，如果为"0"则使用默认值。
         * @param condition        查询条件（如"=", "LIKE"等）。
         * @param limit            返回结果的限制条数，为 0 时不限制。
         * @param sortField        排序字段名。
         * @param sortOrder        排序顺序（"ASC"或"DESC"）。
         * @param page             页码，从 1 开始，默认值为 1。
         * @param pageSize         每页的记录数，默认值为 10。
         * @param caseInsensitive  是否忽略大小写，默认值为 false。
         * @param logicalOperator  自定义条件连接符（如"AND"、"OR"），默认值为"AND"。
         * @param selectedFields   返回的字段数组，默认值为 null（选择所有字段）。
         * @param distinct         是否去重，默认值为 false。
         * @param startDate        时间范围查询的开始时间，默认值为 null。
         * @param endDate          时间范围查询的结束时间，默认值为 null。
         * @param dateField        用于时间范围查询的字段，默认值为 null。
         * @param customEntityType 自定义搜索的实体类型，默认值为 null（使用默认实体类型）。
         */
        SearchParameters(String fieldName, Object value, String condition, int limit, String sortField, String sortOrder,
                         int page, int pageSize, boolean caseInsensitive, String logicalOperator, String[] selectedFields, boolean distinct,
                         Timestamp startDate, Timestamp endDate, String dateField, Class<?> customEntityType) {
            this.fieldName = fieldName;
            this.value = value;
            this.condition = condition;
            this.limit = limit;
            this.sortField = sortField;
            this.sortOrder = sortOrder;
            this.page = page;
            this.pageSize = pageSize;
            this.caseInsensitive = caseInsensitive;
            this.logicalOperator = logicalOperator;
            this.selectedFields = selectedFields;
            this.distinct = distinct;
            this.startDate = startDate;
            this.endDate = endDate;
            this.dateField = dateField;
            this.customEntityType = customEntityType;
        }
    }
}
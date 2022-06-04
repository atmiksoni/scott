package com.flyme.common.json.pqgrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.springmvc.designpattern.proxy.Proxy;

/**
 * 表格数据JSON模型
 */
@JsonInclude(Include.NON_NULL)
public class PqGrid implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer total;// 总记录
	private Integer page;// 当前请求页
	private Integer pageCount;// 当前页数据条数
	private Integer pages;// 总页数
	private List<?> rows = new ArrayList<Object>();// 数据集
	private List<CriterionMap> footer;
	private CriterionMap params = new CriterionMap(false);

	public Integer getTotal() {
		return total;
	}

	public PqGrid() {

	}

	public PqGrid(PagerInfo pagerInfo) {
		this.total = pagerInfo.getTotalResult();
		this.pages = pagerInfo.getTotalPage();
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public List<CriterionMap> getFooter() {
		return footer;
	}

	public PqGrid setFooter(List<CriterionMap> footer) {
		this.footer = footer;
		return this;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	/**
	 * 转换list为PqGrid对象
	 */
	public static PqGrid toPqGrid(List<?> rows, CriteriaQuery cq) {
		PagerInfo pagerInfo = cq.getPagerInfo();
		PqGrid pqGrid = new PqGrid();
		String resultType = cq.getResultType();
		Proxy proxy = cq.getProxy();
		if (resultType.equals(Global.ResultType_LIST)) {
			CriterionMap jsonFieldMap = pagerInfo.getJsonFieldMap();
			if (ObjectUtils.isNotEmpty(jsonFieldMap)) {
				rows = DataGridUtil.listtojson(jsonFieldMap, rows, proxy);
			}
		} else {
			if (ObjectUtils.isNotEmpty(proxy)) {
				for (Object map : rows) {
					proxy.operate((CriterionMap) map, null, null, proxy.getParams());
				}
			}
		}
		pqGrid.setRows(rows);
		pqGrid.setPageCount(rows.size());
		pqGrid.setTotal(pagerInfo.getTotalResult());
		pqGrid.setPages(pagerInfo.getTotalPage());
		pqGrid.setPage(pagerInfo.getPage());
		return pqGrid;
	}

	/**
	 * 转换list为List<CriterionMap>对象
	 */
	public List<CriterionMap> getDataMap(List<?> data, CriteriaQuery cq) {
		PagerInfo pagerInfo = cq.getPagerInfo();
		List<CriterionMap> maps = ObjectUtils.getArrayList();
		String sqlField = pagerInfo.getSqlfields();
		if (ObjectUtils.isNotEmpty(sqlField)) {
			Proxy proxy = cq.getProxy();
			CriterionMap jsonFieldMap = pagerInfo.getJsonFieldMap();
			if (ObjectUtils.isNotEmpty(jsonFieldMap)) {
				maps = DataGridUtil.listToMap(jsonFieldMap, data, proxy);
			}
		}
		return maps;
	}

	/**
	 * 转换list为ApiJson对象
	 */
	public ApiJson getApiJson(CriterionMap data, CriteriaQuery cq) {
		PagerInfo pagerInfo = cq.getPagerInfo();
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		String sqlField = pagerInfo.getSqlfields();
		if (ObjectUtils.isNotEmpty(sqlField)) {
			String[] field = sqlField.split(",");
			for (String string : field) {
				map.put(string, "");
			}
			if (ObjectUtils.isNotEmpty(data)) {
				map.putAll(data);
				j.setObject(map);
			}

		}
		return j;
	}

	public CriterionMap getParams() {
		return params;
	}

	public void setParams(CriterionMap params) {
		this.params = params;
	}

	public void addParams(String key, Object value) {
		this.params.put(key, value);
	}

}

package cn.xiaowenjie.elastic.services;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Service
public class ElasticService {

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	Client client;

	public <T> T getById(String index, String id, Class<?> T)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		return getById(index, index, id, T);
	}

	public <T> T getById(String index, String type, String id, Class<?> T)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.idsQuery().addIds(id)).execute().actionGet();

		System.out.println(response);

		long totalHits = response.getHits().getTotalHits();

		// 没有找到
		if (totalHits == 0) {
			return null;
		} else if (totalHits != 1) {
			throw new RuntimeException("出错了，找到了不止一条：" + totalHits);
		}

		SearchHit searchHit = response.getHits().getHits()[0];

		Map<String, Object> source = searchHit.getSource();

		Object obj = T.newInstance();
		BeanUtils.copyProperties(source, obj);

		return (T) obj;
	}

	public void addIndex(String index, String id, Object data) {
		addIndex(index, index, id, data);
	}

	@SneakyThrows
	public void addIndex(String index, String type, String id, Object data) {
		IndexResponse response = client.prepareIndex(index, type, id).setSource(mapper.writeValueAsBytes(data)).get();
		System.out.println(response);
	}
}

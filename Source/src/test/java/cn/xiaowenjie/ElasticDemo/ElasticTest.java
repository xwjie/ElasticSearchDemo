package cn.xiaowenjie.ElasticDemo;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.xiaowenjie.elastic.beans.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticTest {

	@Autowired
	Client client;

	// smartcn_tokenizer
	//@Autowired
	//ElasticService ela;

	@After
	public void tearDown() throws Exception {
		// this.client.close();
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

		System.out.println(source);

		Object obj = T.newInstance();

		BeanUtils.copyProperties(obj, source);

		return (T) obj;
	}

	@Test
	public void getDocById() throws IllegalAccessException, InvocationTargetException, InstantiationException {
		Article obj = getById("article", "article", "1", Article.class);
		System.out.println(obj);
	}

	@Test
	public void test() {
		assertNotNull(client);
		System.out.println(client);
	}

	@Test
	public void indexTest() {
		String json = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";

		IndexResponse response = client.prepareIndex("twitter", "tweet").setId("kimchy").setSource(json).get();

		System.out.println(response);

		// Index name
		String _index = response.getIndex();
		System.out.println(_index);

		// Type name
		String _type = response.getType();
		System.out.println(_type);

		// Document ID (generated or not)
		String _id = response.getId();
		System.out.println(_id);

		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		System.out.println(_version);

		// status has stored current instance statement.
		System.out.println(response.status());
	}

	@Test
	public void articleIndexTest() throws JsonProcessingException {
		Article art = Article.builder().title("测试数据").filePath("c:\\1.txt").fileExt("txt")
				.body("elasticsearch服务下载包括其中插件和分词" + "http://download.csdn.net/detail/u014201191/8809619").build();

		// instance a json mapper
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse

		IndexResponse response = client.prepareIndex("article", "article").setId("1")
				.setSource(mapper.writeValueAsBytes(art)).get();

		System.out.println(response);

		// Index name
		String _index = response.getIndex();
		System.out.println(_index);

		// Type name
		String _type = response.getType();
		System.out.println(_type);

		// Document ID (generated or not)
		String _id = response.getId();
		System.out.println(_id);

		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		System.out.println(_version);

		// status has stored current instance statement.
		System.out.println(response.status());
	}
}

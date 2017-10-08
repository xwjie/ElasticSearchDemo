package cn.xiaowenjie;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElasticDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticDemoApplication.class, args);
	}

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.cluster}")
	private String clusterName;
	
	@Value("${elasticsearch.nodename}")
	private String nodeName;

	@Value("${elasticsearch.port}")
	private int port;

	@Bean
	public Client client() throws UnknownHostException {
		System.out.println("\n\n\t\t\t ---------- init elastic client ----------\n\n\n");
		
		Settings settings = Settings.builder().put("cluster.name", clusterName).put("node.name", nodeName)
				.put("client.transport.sniff", true).build();

		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

		return client;
	}

}

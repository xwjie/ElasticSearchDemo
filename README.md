# ElasticSearchDemo
ElasticSearch+Springboot的例子，对本机的文本等文件进行全文检索


http://localhost:9200/article/_search?q=%E7%A8%8B%E5%BA%8F%E5%91%98

# 踩坑记

* 注入的类型不对导致无法启动
```
@Autowired
TransportClient client;
```
无法启动，修改为以下代码启动正常。
```
@Autowired
Client client
```
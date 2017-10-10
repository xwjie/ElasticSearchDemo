# ElasticSearchDemo
ElasticSearch+Springboot的例子，对本机的文本等文件进行全文检索


http://localhost:9200/article/_search?q=%E7%A8%8B%E5%BA%8F%E5%91%98

# 更新日志
2017.10.10 完成基本功能，能全文搜索，能显示能分页。

# nodejs 调用elasticsearch

使用 [elasticsearch-js](https://github.com/elastic/elasticsearch-js)

# elastic search 支持跨域

在 `elasticsearch-5.6.2\config\elasticsearch.yml` 最后加入配置

```
http.cors.enabled : true
http.cors.allow-origin: "*"
http.cors.allow-methods: OPTIONS, HEAD, GET, POST, PUT, DELETE
http.cors.allow-headers: X-Requested-With,X-Auth-Token,Content-Type,Content-Length
http.cors.allow-credentials: true
```

# 完全匹配关键字
```
query: {
        match: {
            body: key
        }
    },
```
修改为
```
query: {
        match_phrase: {
            body: key
        }
    },
```

# 不返回某些字段

```
_source: {
	//includes: [
	//    '*.count',
	//    'meta.*'
	//],
	excludes: ['body']
},
```

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
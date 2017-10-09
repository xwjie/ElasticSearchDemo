package cn.xiaowenjie.elastic.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xiaowenjie.elastic.beans.Article;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ArticleService {

	@Autowired
	ElasticService ela;

	public void addFile(File f) throws IOException {
		if (!canAdd(f)) {
			return;
		}

		Article art = fromFile(f);

		log.info("add index:" + f);

		ela.addIndex("article", art.getFilePath(), art);
	}

	private boolean canAdd(File f) {
		// TODO 先只加文本文件
		return f.getName().toLowerCase().endsWith(".txt");
	}

	@SneakyThrows
	public Article getArticleById(String id) {
		return ela.getById("article", id, Article.class);
	}

	private Article fromFile(File f) throws IOException {
		// FIXME 要判断编码格式
		return Article.builder().title(f.getName()).body(FileUtils.readFileToString(f, "gbk")).size(f.length())
				.lastModified(f.lastModified()).filePath(f.getAbsolutePath())
				.fileExt(FilenameUtils.getExtension(f.getName())).build();
	}

}

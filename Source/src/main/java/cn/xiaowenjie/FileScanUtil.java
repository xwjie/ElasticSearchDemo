package cn.xiaowenjie;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.xiaowenjie.elastic.services.ArticleService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileScanUtil {

	@Autowired
	ArticleService articleService;

	@PostConstruct
	public void scan() {
		Set<String> paths = new HashSet<>();

		paths.add("C:\\Users\\Administrator\\Desktop");

		int level = 0;
		for (String path : paths) {
			doScan(new File(path), level);
		}

	}

	@SneakyThrows
	private void doScan(File path, int level) {
		log.info("scan directory:" + path);

		File[] files = path.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				doScan(file, level + 1);
			} else {
				articleService.addFile(file);
			}
		}
	}
}

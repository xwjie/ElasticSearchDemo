package cn.xiaowenjie.elastic.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	private String title;

	private String body;

	private String filePath;

	private String fileExt;

	private long lastModified;

	private long size;
}

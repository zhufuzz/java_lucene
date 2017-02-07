 /**  
 *@Description:     
 */ 
package com.jikexuyuan.crawl.zongheng;  

import java.util.HashMap;

import com.jikexuyuan.crawl.CrawlBase;
import com.jikexuyuan.crawl.zongheng.db.ZonghengDB;
import com.jikexuyuan.crawl.zongheng.model.NovelReadModel;
import com.jikexuyuan.util.JsonUtil;
import com.jikexuyuan.util.ParseMD5;
import com.jikexuyuan.util.RegexUtil;
  
public class ReadPage extends CrawlBase{
	//章节标题正则
	private static final String TITLE = "chapterName=\"(.*?)\"";
	//章节字数正则
	private static final String WORDCOUNT = "itemprop=\"wordCount\">(\\d*?)</span>";
	//章节正文正则
	private static final String CONTENT = "<div id=\"chapterContent\" class=\"content\" itemprop=\"acticleBody\">(.*?)</div>";
	private String url;
	//请求头信息
	private static HashMap<String, String> params;
	
	static {
		params = new HashMap<String, String>();
		params.put("Referer", "http://book.zongheng.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
		params.put("Host", "book.zongheng.com");
	}
	
	public ReadPage(String url) {
		readPageByGet(url, params, "utf-8");
		this.url = url;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 返回小说正阅读页信息
	 */
	public NovelReadModel analyzer() {
		NovelReadModel bean = new NovelReadModel();
		bean.setUrl(this.url);
		bean.setId(ParseMD5.parseStrToMD5(bean.getUrl()));
		bean.setTitle(getTitle());
		bean.setWordCount(getWordCount());
		bean.setContent(getContent());
		return bean;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节标题
	 */
	private String getTitle() {
		return RegexUtil.getFirstString(getPageSourceCode(), TITLE, 1);
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节字数
	 */
	private int getWordCount() {
		String wordCountStr = RegexUtil.getFirstString(getPageSourceCode(), WORDCOUNT, 1);
		return Integer.parseInt(wordCountStr);
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节正文
	 */
	private String getContent() {
		return RegexUtil.getFirstString(getPageSourceCode(), CONTENT, 1);
	}

	/**  
	 * @param args
	 * @Author:lulei  
	 * @Description:  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		ReadPage readPage = new ReadPage("http://book.zongheng.com/chapter/496438/8139039.html");
		System.out.println(readPage.getTitle());
		System.out.println(readPage.getWordCount());
		System.out.println(readPage.getContent());
		NovelReadModel bean = readPage.analyzer();
		System.out.println(JsonUtil.parseJson(bean));
		bean.setChapterId(2);
		bean.setChapterTime(1440401189000l);
		ZonghengDB db = new ZonghengDB();
		db.saveNovelRead(bean);
	}

}

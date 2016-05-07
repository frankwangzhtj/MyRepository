package com.sse.newslt.indexing;

import org.junit.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserTest {
    @Test
    public void rssGetter(){
        String x = new NewsParser().readNewsFeed("http://www.voanews.com/api/epiqq");
        assert (x.startsWith("<?xml"));
    }

//    @Test
//    public void path(){
//        Path pathAbsolute = Paths.get("C:\\src\\newslt\\indexing\\src\\main\\java\\com\\sse\\newslt\\indexing\\Article.java");
//        Path pathBase = Paths.get("C:\\src\\newslt\\indexing\\src\\test\\java\\com\\sse\\newslt\\indexing\\ArticleTest.java");
//        Path pathRelative = pathBase.relativize(pathAbsolute);
//        System.out.println(pathRelative);
//    }
}

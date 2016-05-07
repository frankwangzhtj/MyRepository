package com.sse.newslt.indexing;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Zhihao.Wang on 3/7/2016.
 */
public class ArticleTest
{

    @Test
    public void articleParserTest()
    {
        String resouceXML="C:\\src\\newslt\\indexing\\src\\test\\resources\\cnn_topstories.xml";
        File inputFile = new File(resouceXML);
        String articleXml=null;
        try {
            articleXml = Files.toString(inputFile, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(String.valueOf(articleXml),"", Parser.xmlParser());
        Elements items=doc.getElementsByTag("item");
        int size=items.size();
        System.out.println("items size : "+size);
        ArrayList<Article> list=new ArrayList<Article>();
        for(int i=0;i<size;i++ ){
            Article article=new Article();
            article.articleSetter(items.get(i));
            list.add(article);
        }
        System.out.println(list.size());


/*
        Document doc = Jsoup.parse(articleXml, "", Parser.xmlParser());
        Element e = doc.select("Article").get(0);
        Article article = new Article(e);
        assertEquals("Id", article.getId(), "25420637818");
        assertEquals("source", article.getSource(), "CNBC");
        assertEquals("title", article.getTitle(),
                "Cisco shares jump 9% on earnings beat, buyback");
                */

    }

}

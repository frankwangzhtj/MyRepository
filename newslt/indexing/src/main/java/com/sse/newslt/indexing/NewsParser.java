package com.sse.newslt.indexing;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.io.Files;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zhihao.wang on 3/3/2016.
 */
public class NewsParser
{
    private final Logger logger = LoggerFactory.getLogger(NewsParser.class);
    public static void main(String[] args)
    {
        CommandLine jcl = new CommandLine();
        JCommander argParser = new JCommander(jcl);

        argParser.parse(args);
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
        Logger log = LoggerFactory.getLogger(NewsParser.class);
        NewsParser newsParser = new NewsParser();

        NewsIndexer newsIndexer = new NewsIndexer(jcl.solrUrl);
        String newsFeed = newsParser.readNewsFeed(jcl.input);

       // NewsIndexer newsIndexer = new NewsIndexer("http://localhost:8983/solr/newssearch");

        //String newsFeed = newsParser.readNewsFeed("http://www.voanews.com/api/epiqq");

        List<Article> articles = newsParser.parseNewsFeed(newsFeed);
        articles.forEach((article->{
            log.debug(article.getId() + ":" + "contentLen=" + article.description.length() +
                    "~~" + article.getTitle());
            newsIndexer.index(article);
        }));
        log.info("Articles-count=" + articles.size());
    }

    public String readNewsFeed(String Path)
    {
        URL url;
        String newsfeed = "";
        try
        {
            url = new URL(Path);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                builder.append(inputLine);
            }
            newsfeed = builder.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return newsfeed;
    }

    public List<Article>  parseNewsFeed(String newsfeed)
    {
        List<Article> articles = new ArrayList<>();
        if (newsfeed != null)
        {
            Document doc = Jsoup.parse(newsfeed, "", Parser.xmlParser());
            Elements items=doc.getElementsByTag("item");
            for (Element e : items)
            {
                Article article = new Article();
                article.articleSetter(e);
                articles.add(article);
            }
        }
        else
        {
            logger.warn("newsfeed is NULL");
        }
        return articles;
    }
    /**
     * command parameters parser.
     */
    public static class CommandLine
    {
        /**
         */
        @Parameter(names = {"-i",
                "--input-file"},
                required = true,
                description = "Path to input file")
        private String input;

        @Parameter(names = {"-u",
                "--solr-url"},
                required = true,
                description = "Solr URL with collection")
        private String solrUrl;

    }
}

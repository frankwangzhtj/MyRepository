package com.sse.newslt.indexing;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Zhihao.wang on 3/3/2016.
 */
public class Article
{
    String id;
    public String getId()
    {
        return id;
    }
    String title;
    public String getTitle()
    {
        return title;
    }
    String description;
    public String getDescription()
    {
        return description;
    }
    String url;
    public String getUrl()
    {
        return url;
    }
    String source;
    public String getSource()
    {
        return source;
    }
    String publishedDate;
    public String getPublishedDate()
    {
        return publishedDate;
    }

    public void articleSetter(Element article)
    {
        id = article.getElementsByTag("guid").get(0).text();
        title = article.getElementsByTag("title").get(0).text();
        String temp = article.getElementsByTag("description").get(0).text();
        description = descriptionParser(temp);
        url = article.getElementsByTag("guid").get(0).text();
        publishedDate = article.getElementsByTag("pubDate").get(0).text();
        Elements images=article.select("media|thumbnail");
        if(images.size()>0)
            source = images.get(0).attr("url");
    }

    private String descriptionParser(String des)
    {
        int index=des.indexOf("<br");
        if(index==-1)
            return des;
        String result=des.substring(0,index);
        return result;
    }
}

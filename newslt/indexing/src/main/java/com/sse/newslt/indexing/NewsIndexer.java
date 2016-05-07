package com.sse.newslt.indexing;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Zhihao.wang on 3/4/2016.
 */
public class NewsIndexer
{
    private final Logger logger = LoggerFactory.getLogger(NewsParser.class);
    private final SolrClient solr;

    public NewsIndexer(String urlString)
    {
        HttpClient myclient = HttpClientUtil.createClient(null);
        this.solr = new HttpSolrClient(urlString, myclient);
    }

    void index(Article article)
    {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", article.getId());
        document.addField("title", article.getTitle());
        String description;
        if (article.getDescription().length() > 20000)
        {
            logger.debug("!!!" + article.getId() + " contentLen="
                    + article.getDescription().length() + " truncated at 20000");
            description =  article.getDescription().substring(0, 20000) + "...";
        }
        else
        {
            description =  article.getDescription();
        }

        document.addField("description", description);
        document.addField("url", article.getUrl());
        document.addField("publisheddate", article.getPublishedDate());
        document.addField("source", article.getSource());

        try
        {
            UpdateResponse response = solr.add(document);
            // Remember to commit your changes!
            solr.commit();
        }
        catch (SolrServerException ex)
        {
            logger.error(ex.getMessage());
        }
        catch (IOException ex)
        {
            logger.error(ex.getMessage());
        }
    }
}

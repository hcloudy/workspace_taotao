import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class TestSolrj {

    @Test
    public void testSolrj() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.1.117:8080/solr");

        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.addField("id","testsolrj01");
        solrDocument.addField("item_title","手机");
        solrDocument.addField("item_sell_point","你妹啊");

        solrServer.add(solrDocument);
        solrServer.commit();
    }

    @Test
    public void querySolrj() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.1.117:8080/solr");

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList results = queryResponse.getResults();

        for(SolrDocument q : results) {
            System.out.println(q.get("id"));
            System.out.println(q.get("item_title"));
            System.out.println(q.get("item_sell_point"));
        }
    }

    //测试solr集群版
    @Test
    public void querySolrCloud() throws IOException, SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer("192.168.1.117:2181,192.168.1.117:2182,192.168.1.117:2183");
        SolrInputDocument document = new SolrInputDocument();
        solrServer.setDefaultCollection("collection2");

        document.addField("id", "solrcloud001");
        document.addField("item_title", "华为荣耀7");
        document.addField("item_sell_point", "性价比高，外观好看");

        solrServer.add(document);
        solrServer.commit();
    }
}

package Interfaces;

import Exceptions.IncorrectParameterException;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.Map;

public interface Query {

    public void setParams(String[] params) throws IncorrectParameterException;
    public void execute(SolrQuery query) ;

    /* deci ideea e ca trebuie sa intaorca ceva asta aici */
}

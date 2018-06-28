package Interfaces;

import Exceptions.IncorrectParameterException;
import org.apache.solr.client.solrj.SolrServerException;

public interface Command {

    public void setParams(String[] params) throws IncorrectParameterException;
    public void execute() throws SolrServerException;
}

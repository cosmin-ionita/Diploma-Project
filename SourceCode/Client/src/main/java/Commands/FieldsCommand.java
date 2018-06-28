package Commands;

import APIs.SolrAPI;
import Exceptions.IncorrectParameterException;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.List;

public class FieldsCommand implements Interfaces.Command {

    public void setParams(String[] params) throws IncorrectParameterException {}

    public void execute() throws SolrServerException{
        List<String> fields = SolrAPI.getAllFields();

        System.out.println("The list of fields that can be used as KEYS in any query: \n");

        if(fields != null)
            fields.forEach(t -> System.out.println(t));
    }
}

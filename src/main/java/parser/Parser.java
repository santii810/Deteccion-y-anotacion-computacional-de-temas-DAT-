package parser;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class Parser {
    final static Logger logger = Logger.getLogger(Parser.class);

    private static final String REST_ENDPOINT = "http://lindat.mff.cuni.cz/services/udpipe/api/process";
    private static final String MODEL_EN = "english-partut-ud-2.4-190531";

    public Parser() {

    }

    private String getQueryParams() {
        return "tokenizer&parser&tagger&model=" + MODEL_EN;
    }


    public ParserResponse send(String text) throws IOException {
        String completeEndpoint = REST_ENDPOINT + "?" + getQueryParams() + "&data=" + URLEncoder.encode(text, "UTF-8");
        logger.info("Sending request to UDPipe");
        logger.debug("Complete endpoint: " + completeEndpoint);


        HttpGet request = new HttpGet(completeEndpoint);


        ParserResponse responseModel;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            responseModel = new ParserResponse(response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                responseModel.setBody(result);
            }

        }
        return responseModel;
    }
}

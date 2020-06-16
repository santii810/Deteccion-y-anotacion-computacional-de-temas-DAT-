package parser;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

@NoArgsConstructor
@Slf4j
public class Parser {

    private static final String URL = "http://lindat.mff.cuni.cz/services/udpipe/api/process?tokenizer&parser&tagger&model=english-ewt-ud-2.4-190531&data=";

    public ParserResponse send(String text) throws IOException {
        String textProcessed = preprocessText(text);

        String completeEndpoint = URL + URLEncoder.encode(textProcessed, "UTF-8");
        return httpRequest(completeEndpoint);
    }

    private String preprocessText(String text) {
        String toret = "";
        toret = text.replace("\"", "");
        toret = toret.replace("”", "");
        toret = toret.replace("“", "");
        toret = toret.replace("''", "");
        return toret;
    }

    private ParserResponse httpRequest(String completeEndpoint) throws IOException {
        log.trace("Sending request to UDPipe");
        log.trace("Complete endpoint: " + completeEndpoint);


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
        log.trace(Integer.toString(responseModel.getHttpStatus()));
        log.trace(responseModel.toString());
        return responseModel;
    }
}

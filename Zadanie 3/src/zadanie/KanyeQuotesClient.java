package zadanie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;

public class KanyeQuotesClient {
    private LinkedList<String> quotes;
    private HttpRequest request;
    private HttpClient client;
    private HttpResponse<String> response;

    protected boolean build(){
        try{
            createRequest();
        }
        catch(URISyntaxException e){
            System.out.println("Unable to create HTTP Request.");
            return false;
        }
        createClient();
        quotes = new LinkedList<>();
        return true;
    }

    protected boolean askForQuote(){
        String quote;

        while(true){
            try{
                createResponse();
                quote = trimResponse();
                if(!doesQuoteAlreadyRead(quote)) break;
            }catch (IOException | InterruptedException e){
                System.out.println("Unable to create HTTP Response. Try again or exit.");
                return true;
            }
        }

        System.out.println(quote);
        return quotes.size() != 122;
    }
    private void createRequest() throws URISyntaxException {
         request = HttpRequest.newBuilder().uri(new URI("https://api.kanye.rest/")).build();
    }

    private void createClient(){
        client = HttpClient.newHttpClient();
    }
    private void createResponse() throws IOException, InterruptedException {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //Aby nie instalować biblioteki do obsługi JSON, odpowiedz z serwera jest zamieniana na String, a następnie uzyskany ciąg jest odpowiedno przycinany
    private String trimResponse(){
        return response.body().substring(9, response.body().length() - 1);
    }

    private boolean doesQuoteAlreadyRead(String quote){
        if(quotes.contains(quote)) return true;
        quotes.add(quote);
        return false;
    }
}

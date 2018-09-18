import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiEventHandler extends EventHandler  {

    private static final String baseURL = "https://en.wikipedia.org/w/api.php?action=opensearch&search=";
    private static final String endURL = "&limit=100&namespace=0&format=json";

    public WikiEventHandler(MessageEvent messageEvent){
        this.messageEvent = messageEvent;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        String link = doSearch();
        if(!(link.equals(""))){
            sendMessage(link);
        }
    }

    public String doSearch(){

        String searchString = createSearchString();
        String urlString = baseURL + searchString + endURL;

        try {
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONArray myResponse = new JSONArray(content.toString().trim());

            JSONArray suggestions = myResponse.getJSONArray(1);
            JSONArray preview = myResponse.getJSONArray(2);
            JSONArray links = myResponse.getJSONArray(3);

            if(suggestions.length() > 0){
                System.out.println("" + suggestions.get(0));
                System.out.println("" + preview.get(0));
                System.out.println("" + links.get(0));

                return links.get(0).toString();
            } else {
                return urlString;
            }


        } catch(Exception e) {
            super.sendMessage(urlString);
            System.out.println(e);
            return "";
        }
    }

    public String createSearchString(){
        int numArgs = messageEvent.message.length;

        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < numArgs; i++) {
            if(i == numArgs -1){
                sb.append(messageEvent.message[i]);
            } else {
                sb.append(messageEvent.message[i] + "%20");
            }
        }

        return sb.toString();
    }
}

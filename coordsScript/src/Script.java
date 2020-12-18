import hierarchy.Root;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;

import com.google.gson.Gson;

public class Script {
    public static void main(String[] args) throws FileNotFoundException{

        // set authentication for database
        String url = "jdbc:mysql://localhost:3306/eduinfo?useSSL=false";
        String user = Secrets.user;
        String password = Secrets.password;

        // set query string for getting addresses from database
        String query = "SELECT id, postalCode, town, street, houseNumber from Schools;";

        // set apikey for geocaching api
        var apikey = Secrets.apikey;


        HttpClient client = HttpClient.newHttpClient();

        try {
            // initioal database connection setup
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            st.executeQuery("use eduinfo;");

            // get all the addresses for school rows
            ResultSet rs = st.executeQuery(query);

            // set up a file for writing api responses
            FileWriter fw = new FileWriter("test.csv");
            fw.append("id,lat,lon\n");
            fw.flush();

            
            while (rs.next()){
                
                // generate formatted address for api call
                String request = null;
                // for small towns which don't have streets
                if (rs.getString(4) == null ||rs.getString(4).equals("null") ||  rs.getString(4).equals("-") || rs.getString(4).equals("brak")){
                    request = (rs.getString(2) +" " + rs.getString(3) +" " +rs.getString(5)+ ", Polska");
                }
                else{
                    request = (rs.getString(2) +" " + rs.getString(3) + ", " + rs.getString(4) + " " +rs.getString(5)+ ", Polska");
                }

                System.out.println(request);

                // build query uri
                StringBuilder sb = new StringBuilder("https://api.geoapify.com/v1/geocode/search");
                sb.append('?');
                sb.append(URLEncoder.encode("text", "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(request, "UTF-8"));
                sb.append('&');
                sb.append(URLEncoder.encode("apiKey", "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(apikey, "UTF-8"));
                sb.append('&');
                sb.append(URLEncoder.encode("limit", "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode("1", "UTF-8"));
                sb.append('&');
                sb.append(URLEncoder.encode("filter", "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode("countrycode", "UTF-8"));
                sb.append(':');
                sb.append(URLEncoder.encode("pl", "UTF-8"));
                String uri = sb.toString();

                System.out.println(uri);

                // send api request
                HttpRequest req = HttpRequest.newBuilder()
                        .GET()
                        .header("accept", "application/json")
                        .uri(URI.create(uri))
                        .build();

                // get and parse resonse to JSON 
                var response = client.send(req, HttpResponse.BodyHandlers.ofString());
                var rep = response.body();
                System.out.println(rep);

                Gson g = new Gson();

                var obj = g.fromJson(rep, Root.class);

                // extract the interesting data and save line to file
                for( var feat : obj.features){
                    var lat  = feat.properties.lat;
                    var lon = feat.properties.lon;

                    fw.append(rs.getString(1));
                    fw.append(",");
                    fw.append(Double.toString(lat));
                    fw.append(",");
                    fw.append(Double.toString(lon));
                    fw.append("\n");
                    fw.flush();
                }
            }
            fw.flush();

            fw.close();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        } ;

    }
}

import com.google.gson.Gson;
import hierarchy.Root;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;

public class Script {
    public static void main(String[] args) throws FileNotFoundException{

        String url = "jdbc:mysql://localhost:3306/eduinfo?useSSL=false";
        String user = Secrets.user;
        String password = Secrets.password;

        String query = "SELECT id, postalCode, town, street, houseNumber from Schools where id > 49406;";

        var apikey = Secrets.apikey;
        HttpClient client = HttpClient.newHttpClient();

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            st.executeQuery("use eduinfo;");
            ResultSet rs = st.executeQuery(query);
            FileWriter fw = new FileWriter("data5.csv");
            fw.append("id,lat,lon\n");
            fw.flush();

            while (rs.next()){

                String request = null;
                if (rs.getString(4) == null ||rs.getString(4).equals("null") ||  rs.getString(4).equals("-") || rs.getString(4).equals("brak")){
                    request = (rs.getString(2) +" " + rs.getString(3) +" " +rs.getString(5)+ ", Polska");
                }
                else{
                    request = (rs.getString(2) +" " + rs.getString(3) + ", " + rs.getString(4) + " " +rs.getString(5)+ ", Polska");
                }

                System.out.println(request);

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


                var u = sb.toString();

                System.out.println(sb.toString());

                HttpRequest req = HttpRequest.newBuilder()
                        .GET()
                        .header("accept", "application/json")
                        .uri(URI.create(u))
                        .build();


                var response = client.send(req, HttpResponse.BodyHandlers.ofString());

                var rep = response.body();

                System.out.println(rep);

                Gson g = new Gson();

                var r = new Root();

                var obj = g.fromJson(rep, r.getClass());

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

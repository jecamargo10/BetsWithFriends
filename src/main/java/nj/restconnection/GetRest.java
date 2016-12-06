/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nj.restconnection;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import nj.bestDTO.LeaguesDTO;

/**
 *
 * @author javiercamargourrego
 */
public class GetRest 
{
 public ArrayList<LeaguesDTO> leagues = new ArrayList();
 private String urlString;
 
 
 public GetRest(String pUrlString)
    {
        urlString = pUrlString;
    }
    
    
    public ArrayList<LeaguesDTO> getLeagues() throws Exception {

        URL url = new URL(urlString);
        System.out.println("Conecto");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // By default it is GET request  
        con.setRequestMethod("GET");

        //add request header  
        con.setRequestProperty("X-Auth-Token", "5f5dff74ec8049ffbf1797c6eecfccf6");

        int responseCode = con.getResponseCode();
        System.out.println("Sending get request : " + url);
        System.out.println("Response code : " + responseCode);

        // Reading response from input Stream  
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        String[] full = response.toString().split("}},");
        String[] suprimeFull = new String[full.length];

        for (int i = 1; i < full.length; i++) {
            String league = "{" + (full[i].split("\\},\\{")[0]);

            if (i == full.length - 1) {

                league = league.substring(0, league.length() - 1);
                System.out.println(league);

            } else {
                league += "}";

            }

            suprimeFull[i - 1] = league;

        }

        for (int i = 0; i < suprimeFull.length; i++) {

            Gson tak = new Gson();
            LeaguesDTO tokns = tak.fromJson(suprimeFull[i], LeaguesDTO.class);
            leagues.add(tokns);

        }

        return leagues;
}
    
    
  /** 
     public static void main(String[] args) {
         try{
       GetRest algo = new  GetRest("http://api.football-data.org/v1/competitions/?season=2016") ;
         }
         catch (Exception e)
         {
         e.printStackTrace();
         }
}
* */
    
    
    }
            
    

    
  
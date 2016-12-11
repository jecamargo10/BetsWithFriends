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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import nj.bestDTO.FixturesDTO;
import nj.bestDTO.LeaguesDTO;

/**
 *
 * @author javiercamargourrego
 */
public class GetRest {

    //Cambiar estructura de datos
    public ArrayList<LeaguesDTO> leagues = new ArrayList();
    public ArrayList<FixturesDTO> fixtures = new ArrayList();
    private Hashtable<Date, ArrayList<FixturesDTO>> contenedor = new Hashtable<Date, ArrayList<FixturesDTO>>();
    private String urlString;
    private StringBuffer response;

    public GetRest(String pUrlString) {
        urlString = pUrlString;
        response = null;

    }

    public void connect() throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Auth-Token", "5f5dff74ec8049ffbf1797c6eecfccf6");
        int responseCode = con.getResponseCode();
        System.out.println("Sending get request : " + url);
        System.out.println("Response code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
    }

    public ArrayList<LeaguesDTO> getLeagues() throws Exception {
        String[] full = response.toString().split("}},");
        String[] suprimeFull = new String[full.length];
        for (int i = 1; i < full.length; i++) {
            String league = "{" + (full[i].split("\\},\\{")[0]);
            if (i == full.length - 1) {
                league = league.substring(0, league.length() - 1);
            } else {
                league += "}";
                System.out.println(league);
            }
            suprimeFull[i - 1] = league;
        }
        for (int i = 0; i < suprimeFull.length - 1; i++) {
            Gson tak = new Gson();
            LeaguesDTO tokns = tak.fromJson(suprimeFull[i], LeaguesDTO.class);
            leagues.add(tokns);
        }
        return leagues;
    }

    public ArrayList<FixturesDTO> getFixtures() throws Exception {
        String[] full = response.toString().split("\"}},\"");
        String[] suprimeFull = new String[full.length - 1];
        for (int i = 2; i < full.length; i++) {
            String league = "{\"" + (full[i].split(",\"odds\"")[0]) + "}";
            suprimeFull[i - 2] = league;
        }
        for (int i = 0; i < suprimeFull.length; i++) {
            Gson tak = new Gson();
            FixturesDTO tokns = tak.fromJson(suprimeFull[i], FixturesDTO.class);
            fixtures.add(tokns);
        }
        return fixtures;
    }

    public Hashtable<Date, ArrayList<FixturesDTO>> getFixturs() {
        try {

            String[] full = response.toString().split("\"}},\"");
            String[] suprimeFull = new String[full.length - 1];
            for (int i = 2; i < full.length; i++) {
                String league = "{\"" + (full[i].split(",\"odds\"")[0]) + "}";
                suprimeFull[i - 2] = league;
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date result = null;
            ArrayList<FixturesDTO> pruebas = new ArrayList();
            int agregados = 0;
            for (int i = 0; i < suprimeFull.length - 1; i++) {
                Gson tak = new Gson();
                FixturesDTO tokns = tak.fromJson(suprimeFull[i], FixturesDTO.class);
                String target = tokns.getDate();
                Date res = df.parse(target);
                if (result == null) {
                    result = res;
                    pruebas.add(tokns);
                    agregados++;
                } else if (!result.equals(res)) {
                    contenedor.put(result, pruebas);
                    pruebas = new ArrayList();
                    pruebas.add(tokns);
                    result = res;
                    agregados++;
                } else {
                    pruebas.add(tokns);
                    agregados++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenedor;
    }

/**
    public static void main(String[] args) {
        try {
           GetRest algo = new GetRest("http://api.football-data.org/v1/competitions/440/fixtures?timeFrameStart=2016-11-23&timeFrameEnd=2016-11-26");
            algo.connect();
            ArrayList<FixturesDTO> prueba = algo.getFixtures();
              System.out.println( prueba.get(0).getDate());
              System.out.println( prueba.get(0).getResult().getGoalsAwayTeam());
        
            GetRest alguno  = new GetRest("http://api.football-data.org/v1/competitions/426/fixtures?timeFrameStart=2016-12-10&timeFrameEnd=2016-12-21");
            alguno.connect();
          Hashtable<Date,ArrayList<FixturesDTO> > contenedor =  alguno.getFixturs();
            Date date = new Date();
   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   String fechas = df.format(date);
   



ArrayList<FixturesDTO> pruebas = contenedor.get(df.parse(fechas));
System.out.println(contenedor.get(df.parse(fechas)) == null);
System.out.println(pruebas.get(1).getAwayTeamName());
System.out.println(pruebas.get(1).getHomeTeamName());


              
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    * */

    

}

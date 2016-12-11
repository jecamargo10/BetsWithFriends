/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nj.security.njfablousgetgames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import nj.bestDTO.FixturesDTO;
import nj.bestDTO.LeaguesDTO;
import static nj.betswithfriends.GenericResource.rest;
import nj.restconnection.GetRest;


/**
 *
 * @author javiercamargourrego
 */
public class DailyUpdateThread implements Runnable
{
      private static GetRest rest;

    private      Hashtable<Date, ArrayList<FixturesDTO>> premierFixtures=new Hashtable<Date,ArrayList<FixturesDTO>>();
    private      Hashtable<Date, ArrayList<FixturesDTO>> bbvaFixtures=new Hashtable<Date,ArrayList<FixturesDTO>>();
    private      Hashtable<Date, ArrayList<FixturesDTO>> frenchFixtures=new Hashtable<Date,ArrayList<FixturesDTO>>();
    private      Hashtable<Date, ArrayList<FixturesDTO>> seriesaFixtures=new Hashtable<Date,ArrayList<FixturesDTO>>();
    private      Hashtable<Date,ArrayList<FixturesDTO>> germanFixtures=new Hashtable<Date,ArrayList<FixturesDTO>>();

                        
                  //      private  ArrayList<FixturesDTO> championsLeagues;
                        
                        
private int idPremier;
private int idBbva;
private int idFrench;
private int idSeriesA;
private int idGerman;
//private int idChampions;







    @Override
    public void run() {
        
        
                   System.out.println("ENTRO");

				while(true){
					try {
                                                        System.out.println("ENTRANDO");

                                            //2016-12-04
                                            
                                            DateFormat  year = new SimpleDateFormat("yyyy");
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            Date date = new Date();
                                            String dateToday = dateFormat.format(date);
                                            String fecha = year.format(date);

                                            
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime( dateFormat.parse(dateToday));
                                            cal.add( Calendar.DATE, 20 );
                                            String dateNext=dateFormat.format(cal.getTime());

                                            
                                            rest = new GetRest("http://api.football-data.org/v1/competitions/?season=" + fecha);
                                            rest.connect();
                                            ArrayList<LeaguesDTO> leagues = rest.getLeagues();
                                            for (int i = 0; i < leagues.size(); i++) 
                                            {
                                                LeaguesDTO get = leagues.get(i);
                                                if(get.getCaption().contains("Premier League"))
                                                {
                                                idPremier= get.getId();
                                                }
                                                else  if(get.getCaption().contains("Ligue 1"))
                                                {
                                                idFrench= get.getId();
                                                }
                                                else  if(get.getCaption().contains("Serie A"))
                                                {
                                                idSeriesA= get.getId();
                                                }
                                                 else  if(get.getCaption().contains("1. Bundesliga"))
                                                {
                                                idGerman= get.getId();
                                                }
                                                 else  if(get.getCaption().contains("Primera Division"))
                                                {
                                                idBbva= get.getId();
                                                }
                                                 //Champions
/**
                                                  else  if(get.getCaption().contains("Champions League"))
                                                {
                                                    
                                                idChampions= get.getId();
                                                }
                                                * **/
                                               
                                                
                                            }
                                            
                                            

//Hacerlo para todas la ligas !!!!!!
//Premier
 rest = new GetRest("http://api.football-data.org/v1/competitions/"+idPremier+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        premierFixtures = rest.getFixturs();
System.out.println(premierFixtures.get(dateFormat.parse(dateToday)).size());

//Ligue 1

       rest = new GetRest("http://api.football-data.org/v1/competitions/"+idFrench+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        frenchFixtures = rest.getFixturs();



//Serie A

   rest = new GetRest("http://api.football-data.org/v1/competitions/"+idSeriesA+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        seriesaFixtures = rest.getFixturs();



//Bundesliga

   rest = new GetRest("http://api.football-data.org/v1/competitions/"+idGerman+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        germanFixtures = rest.getFixturs();

//Bbva

   rest = new GetRest("http://api.football-data.org/v1/competitions/"+idBbva+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        bbvaFixtures = rest.getFixturs();

//Champions
/**
  rest = new GetRest("http://api.football-data.org/v1/competitions/"+idChampions+"/fixtures?timeFrameStart=" + dateToday + "&timeFrameEnd=" + dateNext);
                                                                                        rest.connect();

                                                        championsLeagues = rest.getFixtures();
System.out.println(championsLeagues.get(0).getDate());
System.out.println(championsLeagues.get(0).getHomeTeamName());
System.out.println(championsLeagues.size());


**/




                                            
                                        
                                                Thread.sleep(60000);
                                           } catch (Exception ex) {
                     System.out.println(ex.getMessage());

ex.printStackTrace();}    }
        
        
        }
                                    
	
    
    
 
    public static void main(String[] args) {
        try {

           DailyUpdateThread update = new DailyUpdateThread();
           update.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    
    
    
    }
    
    
 







/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nj.betswithfriends;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nj.bestDTO.LeaguesDTO;
import nj.restconnection.GetRest;

/**
 * REST Web Service
 *
 * @author javiercamargourrego
 */
@Path("bWF")
public class GenericResource {

    // Solo se tiene que setear una vez por dia ? 
    @Context
    private UriInfo context;

    public static GetRest rest;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * nj.betswithfriends.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getSoccerMatchesOnDates")

    public String getSoccerMatchesOnDates() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Path("getLeagues")

    public Response getLeagues(@QueryParam("step") String fecha) {

        //TODO return proper representation object
        rest = new GetRest("http://api.football-data.org/v1/competitions/?season=" + fecha);
        String anwser = "[";
        try {
            ArrayList<LeaguesDTO> leagues = rest.getLeagues();
            for (int i = 0; i < leagues.size() - 1; i++) {
                Gson gson = new Gson();

                if (i != leagues.size() - 2) {
                    anwser += (gson.toJson(leagues.get(i)) + ",");
                } else {
                    anwser += gson.toJson(leagues.get(i));

                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        anwser += "]";
        System.out.println(anwser);

        return Response.status(201).entity(anwser).build();

    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}

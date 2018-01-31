package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.Train;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the train
 *
 * @author SARR
 * @author BOUE
 *
 */
public class TrainsResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single train request.
     */
    public TrainsResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    /**
     *
     * Retourne la liste des Trains
     *
     * @return  JSON representation du train
     * @throws JSONException : exception
     */
    @Get("json")
    public Representation getTrains() throws JSONException
    {
    	Collection<Train> trains = backend_.getDatabase().getTrains();
        Collection<JSONObject> jsonTrains = new ArrayList<JSONObject>();

        for (Train train : trains)
        {
            JSONObject current = new JSONObject();
            current.put("numero", train.getNumero());
            current.put("name", train.getNom());
            current.put("etat", train.getEtat());
            jsonTrains.add(current);

        }

        JSONArray jsonArray = new JSONArray(jsonTrains);

        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createTrain(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        int nombrePlaceVerrou = object.getInt("place_verrou");
        String nom="";
        if(object.has("nom"))
        {
        	nom = object.getString("nom");
        }

        // Enregistre le train
        Train train = backend_.getDatabase().createTrain(nom,nombrePlaceVerrou);


        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("Numero", train.getNumero());
        resultObject.put("nom", train.getNom());
        resultObject.put("etat", train.getEtat());
        JsonRepresentation result = new JsonRepresentation(resultObject);


        return result;
    }



}

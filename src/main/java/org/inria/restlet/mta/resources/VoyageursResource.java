package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.Voyageur;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the traveller
 *
 * @author SARR
 * @author BOUE
 *
 */
public class VoyageursResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single Voyageur request.
     */
    public VoyageursResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    /**
     *
     * Retourne la liste des Voyageurs
     *
     * @return  JSON representation du Voyageur
     * @throws JSONException : exception
     */
    @Get("json")
    public Representation getVoyageurs() throws JSONException
    {
        Collection<Voyageur> voyageurs = backend_.getDatabase().getVoyageurs();
        Collection<JSONObject> jsonVoyageurs = new ArrayList<JSONObject>();

        for (Voyageur voyageur : voyageurs)
        {
            JSONObject current = new JSONObject();
            current.put("numero", voyageur.getNumero());
            current.put("name", voyageur.getNom());
            current.put("etat", voyageur.getEtat());
            jsonVoyageurs.add(current);

        }

        JSONArray jsonArray = new JSONArray(jsonVoyageurs);
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createVoyageur(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        String nom="";
        if(object.has("nom"))
        {
        	nom = object.getString("nom");
        }


        // Enregistre le Voyageur
        Voyageur voyageur = backend_.getDatabase().createVoyageur(nom);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("numero", voyageur.getNumero());
        resultObject.put("nom", voyageur.getNom());
        resultObject.put("etat", voyageur.getEtat());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }



}

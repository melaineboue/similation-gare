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
public class TrainResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     *
     */
    public TrainResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    /**
     *
     * Retourne les details d'un Train
     *
     * @return  JSON representation du train
     * @throws JSONException : execption
     */
    @Get("json")
    public Representation getTrain() throws JSONException
    {

    	String numeroString = (String) getRequest().getAttributes().get("numero");
        int numero = Integer.valueOf(numeroString);

        Train train = backend_.getDatabase().getTrain(numero);

        JSONObject jsonTrain = new JSONObject();


	       JSONObject current = new JSONObject();
	       current.put("numero", train.getNumero());
	       current.put("name", train.getNom());
	       current.put("etat", train.getEtat());
	       current.put("capacité", train.getCapacite_train());
	       current.put("nombre place_restant", train.getReste_places());
	       current.put("vitesse", train.getVitesse_train());
	       current.put("nombre place verrouillé", train.getPlacesLibres());
	       current.put("Nom_du_processus", train.getProcessus());


        return new JsonRepresentation(current);
    }





}

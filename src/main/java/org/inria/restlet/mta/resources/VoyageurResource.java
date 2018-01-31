package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.Voyageur;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the voyageur
 *
 * @author SARR
 * @author BOUE
 *
 */
public class VoyageurResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     *
     */
    public VoyageurResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    /**
     *
     * Retourne les details d'un Voyageur
     *
     * @return  JSON representation du voyageur
     * @throws JSONException : exception
     */
    @Get("json")
    public Representation getVoyageur() throws JSONException
    {

    	String numeroString = (String) getRequest().getAttributes().get("numero");
        int numero = Integer.valueOf(numeroString);

        Voyageur voyageur = backend_.getDatabase().getVoyageur(numero);

        JSONObject jsonVoyageur = new JSONObject();


	       JSONObject current = new JSONObject();
	       current.put("numero", voyageur.getNumero());
	       current.put("name", voyageur.getNom());
	       current.put("etat", voyageur.getEtat());


        return new JsonRepresentation(current);
    }





}

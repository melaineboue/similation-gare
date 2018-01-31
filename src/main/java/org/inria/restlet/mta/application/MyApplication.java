package org.inria.restlet.mta.application;


import org.inria.restlet.mta.resources.VoyageursResource;
import org.inria.restlet.mta.resources.TrainResource;
import org.inria.restlet.mta.resources.TrainsResource;
import org.inria.restlet.mta.resources.VoyageurResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * Application.
 *
 * @author SARR
 * @author BOUE
 *
 */
public class MyApplication extends Application
{

    public MyApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/voyageurs", VoyageursResource.class); //liste des voyageurs
        router.attach("/trains", TrainsResource.class); //liste des trains
        router.attach("/trains/{numero}", TrainResource.class);//details d'un train
        router.attach("/voyageurs/{numero}", VoyageurResource.class); //details d'un voyageur
        return router;
    }
}

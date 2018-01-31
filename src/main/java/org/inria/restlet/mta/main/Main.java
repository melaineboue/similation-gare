package org.inria.restlet.mta.main;

import org.inria.restlet.mta.application.MyApplication;
import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.ihm.IHM;
import org.inria.restlet.mta.ihm.Panneau;
import org.inria.restlet.mta.internals.EspaceQuai;
import org.inria.restlet.mta.internals.EspaceVente;
import org.inria.restlet.mta.internals.Fonction;
import org.inria.restlet.mta.internals.Parameter;
import org.inria.restlet.mta.internals.Train;
import org.inria.restlet.mta.internals.Voyageur;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

/**
 * Main RESTlet minimal example
 * @author SARR
 * @author BOUE
 *
 */
public final class Main
{

    /** Hide constructor. */
    private Main()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method.
     *
     * @param args  The arguments of the commande line
     * @throws Exception : exception
     */
    public static void main(String[] args) throws Exception
    {
        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);

        // Create an application
        Application application = new MyApplication(context);

        // Add the backend into component's context
        Backend backend = new Backend();
        context.getAttributes().put("backend", backend);
        component.getDefaultHost().attach(application);




        // Start the component
        component.start();

        //Espace quai et Espace vente
        EspaceQuai quai = new EspaceQuai();
		EspaceVente vente = new EspaceVente();

		//l'interface graphique
		Panneau panneau = new Panneau(quai,vente);
		IHM ihm =new IHM(panneau);


		quai.setBackend(backend);
		backend.initDatabaseParameter(quai, vente,ihm);




		quai.setIhm(ihm);
		vente.setIhm(ihm);






		//Le système qui envoi des trains de manière aleatoire et de manière continue
		//on tire un nombre entre 0 et 10 tous les 4 secondes, si le nombre est pair alors on crée un train
		Thread trainManagement = new Thread(){
			public void run()
			{
				while(true)
				{
					int nombre = Fonction.random(0, 10);
					if(nombre % 2 == 0)
					{
						Train train = backend.getDatabase().createTrain("",Parameter.PLACE_VERROUILLE_PAR_DEFAUT);
					}

					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		};






		//Le système qui envoi des voyageurs de manière aleatoire et de manière continue
		//on tire un nombre entre 0 et 10 tous les 500 millisecondes, si le nombre est pair alors on crée un voyageur

		Thread voyageurManagement = new Thread(){
			public void run()
			{
				while(true)
				{
					int nombre = Fonction.random(0, 10);
					if(nombre % 2 == 0)
					{
						Voyageur voyageur = backend.getDatabase().createVoyageur("");
					}

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}


			}
		};


		ihm.setGenerateTrain(trainManagement);
		ihm.setGenerateVoyageur(voyageurManagement);

		trainManagement.start();
		voyageurManagement.start();


		


    }

}

package org.inria.restlet.mta.database.api.impl;

import java.util.Collection;
import java.util.HashMap;

import org.inria.restlet.mta.database.api.Database;
import org.inria.restlet.mta.ihm.IHM;
import org.inria.restlet.mta.internals.EspaceQuai;
import org.inria.restlet.mta.internals.EspaceVente;
import org.inria.restlet.mta.internals.Train;
import org.inria.restlet.mta.internals.Voyageur;

/**
 *
 * In-memory database
 *
 * @author SARR
 * @author BOUE
 *
 */
public class InMemoryDatabase implements Database
{



    /**La liste des voyageurs dans le système*/
    HashMap<Integer,Voyageur> voyageurs ;

    /**La liste des trains dans le système*/
    HashMap<Integer,Train> trains ;

    EspaceQuai quai;
    EspaceVente vente;
    IHM ihm;



    public InMemoryDatabase()
    {
        voyageurs = new HashMap();
        trains = new HashMap();

    }




	@Override
	public Collection<Voyageur> getVoyageurs() {
		return voyageurs.values();
	}


	@Override
	public Collection<Train> getTrains() {
		return trains.values();
	}




	/**
	 * Crée un voyageur, l'ajoute dans la base de données et le lance
	 */
	@Override
	public Voyageur createVoyageur(String nom) {

		Voyageur voyageur = new Voyageur(vente, quai,ihm,nom);
		voyageurs.put(voyageur.getNumero(),voyageur);
		ihm.getPanneau().ajouterVoyageur(voyageur);
		ihm.repaint();
		voyageur.start();
		return voyageur;
	}


	/**
	 * Crée un train, l'ajoute dans la base de données et le lance
	 * @param nombreVerrou : nombre de place  qui doit  au moins être vide quand le train va repartir de l'espace quai
	 */
	@Override
	public Train createTrain(String nom, int nombreVerrou ) {

		Train train = new Train(quai,nombreVerrou,ihm,nom);
		trains.put(train.getNumero(),train);
		ihm.getPanneau().ajouterTrain(train);
		ihm.repaint();
		train.start();
		return train;
	}



	/**
	 * Reccupère les infos concernant un train
	 * @param numero : le numero du train
	 */
	@Override
	public Train getTrain(int numero) {

		return trains.get(numero);
	}




	/**
	 * Reccupère les infos concernant un voyageur
	 * @param numero : le numero du train
	 */
	@Override
	public Voyageur getVoyageur(int numero) {
		return voyageurs.get(numero);
	}


	/**
	 * Initialise les paramètres l'espace quai et l'espace vente
	 * @param quai : l'espace quai du programme
	 * @param vente : l'espace vente du programme
	 * @param ihm : l'interface graphique ou va s'afficher les trains et voyageurs
	 */
	public void setParameter(EspaceQuai quai, EspaceVente vente,IHM ihm)
    {
        this.quai = quai;
        this.vente = vente;
        this.ihm = ihm;

    }




	@Override
	public void remove(int numero) {
		voyageurs.remove(numero);

	}




public synchronized void afficherEtat()
   {
	   System.out.println("---------------------------------------------------------------");
	   for(Train train : trains.values())
	   {
		   System.out.println("train "+train.getNumero()+" : "+train.getEtat()+"/ place restant : "+train.getReste_places()+" "+ train.getName());
	   }

	   for(Voyageur voyageur : voyageurs.values())
	   {
		   System.out.println("voyageur "+voyageur.getNumero()+" : "+voyageur.getEtat()+" "+voyageur.getName());
	   }
	   System.out.println("---------------------------------------------------------------");
   }









}

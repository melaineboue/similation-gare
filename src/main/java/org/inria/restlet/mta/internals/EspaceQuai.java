package org.inria.restlet.mta.internals;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.ihm.IHM;

/**
 * La classe Espace Quai
 * @author SARR
 * @author BOUE
 *
 */
public class EspaceQuai {
	private final int nbreVoies = Parameter.NOMBRE_VOIE_QUAI;
	private int nbreVoies_libre; //le nombre de voie libre au quai
	private Train[] voies; //l'ensemble des voies qui peuvent accueillir des trains
	private Boolean[] voiesEstAffectee; //l'ensemble des variables qui nous dit si une voie est deja occupé ou non
	Backend backend;



	public static int POINT_ARRET;
	final int ESPACE_ENTRE_VOIE = 50;
	int x_debut, y_debut, width, height ;
	IHM ihm;

	Graphics g;



   /**
    * On crée l'espace quai
    */
	public EspaceQuai() {
		this.nbreVoies_libre=nbreVoies;
		this.voies=new Train[nbreVoies];
		this.voiesEstAffectee=new Boolean[nbreVoies];

		for(int i=0;i<voies.length;i++)
		{
			voies[i]=null; //les voies sont vides initialement
			voiesEstAffectee[i] = false;
		}



	}






	/**
	 * diminue le nombre de voie libre, cette méthode est appélée quand un train est garé sur une voie au quai
	 * @param train : le train qui est garé au quai
	 */
   public synchronized void occuperVoieLibre(Train train){

	   afficherEtat();
	   nbreVoies_libre--;
	   voies[train.getNumeroVoieAuQuai()]=train;
	   notifyAll(); //un train est garé, on informe tous les voyageurs
   }


   /**
	 * augmente le nombre de voie libre, cette méthode est appélée quand un train part du quai
	 * @param train : le train qui part du quai
	 */
   public synchronized void libererVoieLibre(Train train){
	   afficherEtat();
	   voies[train.getNumeroVoieAuQuai()]=null;
	   voiesEstAffectee[train.getNumeroVoieAuQuai()] = false;
	   train.setNumeroVoieAuQuai(-1);
	   nbreVoies_libre++;
	   notifyAll(); //informe les autres trains qui sont en attente
   }





   /**
	 * Le train cherche une voie pour garer un train
	 * et gare le train passé en paramètre sur une voie libre
	 * @param train : Le train à garer
	 */
	public synchronized void chercherVoie(Train train){

		//afficherEtat();
		System.out.println("Le train "+train.getNumero()+" veut se gare");

		while(true)
		{
			   for(int i=0; i<voies.length;i++)
			   {
				   if(!voiesEstAffectee[i])
				   {
					   train.setNumeroVoieAuQuai(i);
					   voiesEstAffectee[i] = true ;
					   //occuperVoieLibre(train); //un train vient d'arriver
					   //notifyAll();  //on informe tous les voyageurs
					   return;
				   }
			   }

			   try {
				   train.setEtat("B - En attente d'une voie libre");
				   System.out.println(train.getNom()+":  train en attente ");
				   wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		 }


	}









   /**
    * Cherche un train garé sur le quai, et qui dispose d'une place libre au moins
    * @param voyageur : le voyageur attendant un train
    */
   public synchronized  void chercherTrainPourVoyageur(Voyageur voyageur){

      while(true){  //tant que voyageur ne trouve pas de train
         for(int i=0; i<voies.length;i++){

            Train train = voies[i];

           if(train != null){
        	   if(train.hasPlaceLibre()){   //si le train a au moins une place libre

        		   voyageur.monter(train);  //le voyageur monte dans le train qu'il a trouvé
        		   System.out.println("choix train "+train.getNom());

        		   return ;
        	   }
           }
         }
         try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
      }

   }







   /**
    * On efface les voyageurs qui n'auront pas de billet
    * @param voyageur : le voyageur n'ayant pas eu de billet
    */
   public void removeVoyageur(Voyageur voyageur)
   {
	   backend.getDatabase().remove(voyageur.getNumero());
   }




   /**
    * Le backend qui contient la base de données
    * @param backend : l'objet
    */
   public void setBackend(Backend backend) {
	this.backend = backend;
}





/**
 * Affiche l'etat de tous les objets du système
 */
public synchronized void afficherEtat()
   {
	   System.out.println("---------------------------------------------------------------");
	   for(Train train : backend.getDatabase().getTrains())
	   {
		   System.out.println("train "+train.getNumero()+" : "+train.getEtat()+"/ place restant : "+train.getReste_places()+" "+ train.getName());
	   }

	   for(Voyageur voyageur : backend.getDatabase().getVoyageurs())
	   {
		   System.out.println("voyageur "+voyageur.getNumero()+" : "+voyageur.getEtat()+" "+voyageur.getName());
	   }
	   System.out.println("---------------------------------------------------------------");
   }







	/**
	 * On dessine l'espace quai sur l'interface graphique
	 * @param g : le graphique
	 */
	public void paintComponent(Graphics g)
	{
		g.drawRect(x_debut, y_debut, width, height);

		for (int i=1 ; i <= nbreVoies ; i++ )
		{
			g.drawLine(x_debut+10 , y_debut + i*ESPACE_ENTRE_VOIE , x_debut + width - 20, y_debut + i*ESPACE_ENTRE_VOIE);
		}

	}






	public IHM getIhm() {
		return ihm;
	}






	public void setIhm(IHM ihm) {
		this.ihm = ihm;

		//initialisation des variables de l'espace quai
		x_debut =(int) ihm.getWidth()/2;
		y_debut = 10;
		width = (int)ihm.getWidth()/2-100;
		height = 400;
		POINT_ARRET = x_debut + (int)(width / 2) - Train.LONGUEUR_TRAIN / 2;

	}



}

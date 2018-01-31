package org.inria.restlet.mta.internals;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.inria.restlet.mta.ihm.IHM;

/**
 * Le Train
 * @author SARR
 * @author BOUE
 *
 */
public class Train extends Thread{

	private final int arret_train = Parameter.TEMPS_ARRET_AU_QUAI ;
	private final int capacite_train = Parameter.CAPACITE_TRAIN ;
	private int vitesse_train;         //entre 50 et 300
	private int nombre_place_ne_pas_touche ;  //le nombre de place qui doit rester quand le train partira de l'espace quai
	private int reste_places_libre;  //nombre de place libre restant
	private EspaceQuai quai;
	private static int nombreTrain=0;  //compteur des trains crée
	int numeroVoieAuQuai;  //chaque train garde en mémoire le numero du quai ou il est garé , pour qu'il puisse le librer en partant

	private String etat;
	private int numero;
	private String nom;
	private String processus;




	/*
	 * *********************************************************
	 * ********************GRAPHIQUE***************************
	 ***********************************************************
	 */
	public static final int LONGUEUR_TRAIN = 300;
	int x;  //abcisse train
	int y;  //ordonnée train
	Graphics g;
	Image imageTrain;
	IHM ihm;







	/**
     *@param quai : l'espace quai où le train part
     * @param placesVerrou : nombre de place qui doit être libre au moins quand le train va partir
     * @param nom : le nom du train
     * @param ihm : l'interface graphique
     */
	public Train(EspaceQuai quai, int placesVerrou, IHM ihm, String nom) {
		numero=nombreTrain++;
		this.quai=quai;
		this.ihm = ihm;
		this.vitesse_train = Fonction.random(50, 300); //nombre aléatoire entre 50 et 300
		this.nombre_place_ne_pas_touche = placesVerrou;
		this.reste_places_libre = capacite_train - nombre_place_ne_pas_touche;
		this.setEtat("");

		if(nom.equals(""))
		{
			this.setNom("Train "+numero);
		}
		else
		{
			this.setNom(nom);
		}

		this.setProcessus("");



		int x_initial = Fonction.random(5,500);
		this.x = -LONGUEUR_TRAIN - x_initial;
		this.y = Fonction.random(10, 360);





		try {


			ImageIcon icone = new ImageIcon(getClass().getResource("/images2/train.png"));
			this.imageTrain = icone.getImage();


		} catch (Exception e) {
			e.printStackTrace();
		}



	}

    public void run(){
        this.setProcessus(getName());
		arriver(reste_places_libre);

    }



    /**
     * Le train arrive avec un certain nombre de place déja verrouillé
     * @param restePlaces : le nombre de place verrouillé
     */
	public  void arriver(int restePlaces){


		System.out.println("Le train "+this.getNom()+" arrive ");
		this.setEtat("En route vers la gare");


		int x_destination = (int)ihm.getWidth()/3 - LONGUEUR_TRAIN;
		this.seDeplacer(x_destination, y , (800000/vitesse_train)); //800000 pour ne pas que les trains soit trop rapide sur l'interface

		/*try {
			Thread.sleep(10000/vitesse_train);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		quai.afficherEtat();

		quai.chercherVoie(this);
		this.garer();

	}



	/**
	 * Le train cherche une voie pour garer à la bonne position
	 */
	public void garer(){
		this.seDeplacer(x + LONGUEUR_TRAIN, y);
		this.seDeplacer(x, this.getNumeroVoieAuQuai() * 50 + 10);
		this.seDeplacer(EspaceQuai.POINT_ARRET, y);
		arreter();
	}


	/**
	 * Attend à ce que les voyageurs montent
	 */
	public void arreter(){

		quai.afficherEtat();
		quai.occuperVoieLibre(this);

		this.setEtat("C - Garé");

		try {
			Thread.sleep(arret_train);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		partir();

	}


	public void partir(){
		System.out.println("le train "+this.getNom()+" part avec " + reste_places_libre + "place libre");

		quai.afficherEtat();
		quai.libererVoieLibre(this);
		seDeplacer(ihm.getWidth() + 50, y);

		this.setEtat("D - Reparti");

		quai.afficherEtat();

	}



	/**
	 * informe s'il contient une place libre au moins
	 * @return true s'il reste au moins une place libre
	 */
	public boolean hasPlaceLibre(){
		if(reste_places_libre>0){
			return true;
    	}
		return false;
	}



	 public void diminuerNbrePlaces(){
		 reste_places_libre--;

	 }








	public int getReste_places() {
		return reste_places_libre;
	}

	public void setReste_places(int reste_places) {
		this.reste_places_libre = reste_places;
	}



    public int getNumeroVoieAuQuai() {
		return numeroVoieAuQuai;
	}


	public void setNumeroVoieAuQuai(int numeroVoieAuQuai) {
		this.numeroVoieAuQuai = numeroVoieAuQuai;
	}


	public int getArret_train() {
		return arret_train;
	}



	public int getVitesse_train() {
		return vitesse_train;
	}

	public void setVitesse_train(int vitesse_train) {
		this.vitesse_train = vitesse_train;
	}

	public int getCapacite_train() {
		return capacite_train;
	}



	public int getNombre_place_ne_pas_touche() {
		return nombre_place_ne_pas_touche;
	}

	public void setNombre_place_ne_pas_touche(int nombre_place_ne_pas_touche) {
		this.nombre_place_ne_pas_touche = nombre_place_ne_pas_touche;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getProcessus() {
		return processus;
	}

	public void setProcessus(String processus) {
		this.processus = processus;
	}





	public int getPlacesLibres() {
		return nombre_place_ne_pas_touche;
	}

	public void setPlacesLibres(int placesLibres) {
		this.nombre_place_ne_pas_touche = placesLibres;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String state) {
		this.etat = state;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}






	/*
	 * *****************************************************************
	 * ********************GRAPHIQUE************************************
	 ******************************************************************
	 */


	/**
	 * Dessine le train
	 * @param g : graphique
	 */
	public void paintComponent(Graphics g)
	{
		g.drawImage(imageTrain, x, y, null);
	}



	/**
	 * deplace le train sur l'interface graphique
	 * @param x_final : abcisse final après deplacement
	 * @param y_final : ordonnées final
	 */
	public void seDeplacer(int x_final, int y_final)
	{

		while(this.x!=x_final || this.y != y_final )
		{


			try {
				Thread.sleep(10);

				if(this.x < x_final)
				{
					this.x ++;
				}
				else if(this.x > x_final)
				{
					this.x --;
				}


				if(this.y < y_final)
				{
					this.y ++;
				}
				else if(this.y > y_final)
				{
					this.y --;
				}

				//quai.repaint(g);
				ihm.repaint();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}



	/**
	 * deplace le train sur l'interface graphique, prend un certain temps pour faire ce deplacement
	 * @param x_final : abcisse final après deplacement
	 * @param y_final : ordonnées final
	 * @param temp_pour_arriver : le temps mis pour faire le deplacement
	 */
	public void seDeplacer(int x_final, int y_final, int temp_pour_arriver)
	{

		int distance_x_a_parcourir = Math.abs(x_final - x);

		int temp_un_pas = temp_pour_arriver / distance_x_a_parcourir;

		while(this.x!=x_final || this.y != y_final )
		{


			try {
				Thread.sleep(temp_un_pas);

				if(this.x < x_final)
				{
					this.x ++;
				}
				else if(this.x > x_final)
				{
					this.x --;
				}


				if(this.y < y_final)
				{
					this.y ++;
				}
				else if(this.y > y_final)
				{
					this.y --;
				}

				//quai.repaint(g);
				ihm.repaint();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}



	}






}

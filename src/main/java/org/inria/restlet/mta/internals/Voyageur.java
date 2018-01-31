package org.inria.restlet.mta.internals;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.inria.restlet.mta.ihm.IHM;

/**
 * La classe Voyageur
 * @author SARR
 * @author BOUE
 *
 */
public class Voyageur extends Thread{

	private static int nombreVoyageur=0;

	private EspaceVente vente;
	private EspaceQuai quai;

	private String etat;
	private int numero;
	private String nom;
	private String processus;
	private boolean disposeBillet;



	/*
	 ********************************** ***********************
	 *********************GRAPHIQUE****************************
	 **********************************************************
	 */
	int x;
	int y;
	//BufferedImage imageVoyageur;
	Image imageVoyageur;
	IHM ihm;


	/**
	 * Constructeur 1
	 * @param vente : l'espace vente
	 * @param quai:l'espace quai
	 * @param name : le nom du voyageur
     * @param ihm : l'interface graphique
	 */
	public Voyageur(EspaceVente vente, EspaceQuai quai, IHM ihm, String name) {
		this.quai = quai;
		this.vente = vente;
		this.ihm = ihm;

		if(name.equals(""))
		{
			this.setNom("Voyageur"+numero);

		}
		else
		{
			this.setNom(name);
		}

		this.setNumero(nombreVoyageur);
		this.setEtat("A - En route vers la gare");
		this.setProcessus("");
		this.disposeBillet = false;
		nombreVoyageur++;


		x = -1 * Fonction.random(20,100);
		y =  Fonction.random(vente.getEntree_y1()+5,vente.getEntree_y2()-30);

		this.quai=quai;
		try {
			ImageIcon icone = new ImageIcon(getClass().getResource("/images2/voyageur.png"));
			this.imageVoyageur = icone.getImage();


		} catch (Exception e) {
			e.printStackTrace();
		}




	}



	/**
	 * lance le voyageur dans le système
	 */
	public void run(){
		this.setProcessus(getName());
		System.out.println(getNom()+ " "+"avant acheté");
		acheter();

		if(disposeBillet)
		{
			//si le voyageur a un billet, il part à la gare
			this.passer();
		}
		else{
			//s'il n'y a plus de billet disponible, il rentre chez lui
			this.partir();
		}



	}




	/**
	 * prendre un billet
	 */
	public void acheter() {

		int x_destination = Fonction.random(50, ihm.getWidth()/4 - 50) ;
		int y_destination = Fonction.random(650, 750) ;


		//il se deplace pour venir à l'espace vente
		seDeplacer(10, y); //pour entrer par la porte d'entrée
		seDeplacer(x_destination, y_destination); //pour se positionner quelque part devant l'espace vente
		vente.imprimer(this);  //on imprime son billet


	}



	/**
	 * aller au quai
	 */
	public void passer() {

		int x_destination = Fonction.random(ihm.getWidth()/2 +10, ihm.getWidth() -100) ;
		int y_destination = Fonction.random(425,550) ;

		//le voyageur se deplace pour aller attendre au quai
		seDeplacer(ihm.getWidth() / 4 + 20 , y);
		seDeplacer(x_destination, y_destination);

		quai.afficherEtat();
		System.out.println(" le voyageur "+numero+" va au quai ");
		this.attendre();

	}


	/**
	 * attendre train
	 */

	public void attendre() {
		quai.afficherEtat();
		System.out.println(" le voyageur "+numero+" attends un train ");
		quai.chercherTrainPourVoyageur(this);


	}

	/**
	 * monter dans le train
	 * @param train : le train dans lequel il monte
	 */

	public void monter(Train train) {

		x += ihm.getWidth();
		y += ihm.getHeight();


		quai.afficherEtat();
		System.out.println(" le voyageur "+numero+"monte dans le train "+train.getNumero()+" et il reste "+train.getReste_places()+" places");
	    train.diminuerNbrePlaces();
	    this.setEtat("C - Monter dans le train "+train.getNumero());
	    System.out.println(getNom()+ " finir");
	}





	/**
	 * rentrer chez lui, puisqu'il n'y a plus debillet disponible
	 */

	public void partir() {

		int x_destination = Fonction.random(vente.getSortie_x1()+10, vente.getSortie_x2()-30) ; //vers la sortie
		int y_destination = ihm.getHeight() + 25 ;

		seDeplacer(x_destination, ihm.getHeight() - 100);
		seDeplacer(x, y_destination);

		quai.afficherEtat();
		System.out.println("un client repart chez lui");

		quai.removeVoyageur(this);
		this.setEtat("Reparti chez lui");
	}































	public int getNumero() {
		return numero;
	}



	public String getEtat() {
		return etat;
	}

	public void setEtat(String state) {
		this.etat = state;
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

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setDisposeBillet(boolean disposeBillet) {
		this.disposeBillet = disposeBillet;
	}









	/*
	 * *********************************************************************
	 * *********************GRAPHIQUE***************************************
	 * *********************************************************************
	 */
	public void paintComponent(Graphics g)
	{
		g.drawImage(imageVoyageur, x, y, null);
	}



	/**
	 * Le voyageur se deplace de sa position actuelle à une autre position
	 * @param x_destination : l'abcisse de la position finale
	 * @param y_destination :  l'abcisse de la position finale
	 */
	public void seDeplacer(int x_destination, int y_destination)
	{

		while(this.x!=x_destination || this.y != y_destination )
		{


			try {
				Thread.sleep(10);

				if(this.x < x_destination)
				{
					this.x ++;
				}
				else if(this.x > x_destination)
				{
					this.x --;
				}


				if(this.y < y_destination)
				{
					this.y ++;
				}
				else if(this.y > y_destination)
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

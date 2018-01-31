package org.inria.restlet.mta.internals;

import java.awt.Graphics;
import java.awt.Graphics2D;

import org.inria.restlet.mta.ihm.IHM;

/**
 * La classe Espace Vente
 * @author SARR
 * @author BOUE
 *
 */
public class EspaceVente {

	public final int limite_nbre_billet = Parameter.NOMBRE_INITIAL_DE_BILLET;
	public final int temps_impression = Parameter.TEMPS_IMPRESSION_BILLET;
	int nbreBilletLibre;


	/*
	 * ***********************************************************************
	 * *********************************GRAPHIQUE*****************************
	 * ***********************************************************************
	 */


	int x_debut, y_debut, width, height  ;
	int x_guichet, y_guichet;
	int entree_y1;
	int entree_y2;
	int sortie_x1;
	int sortie_x2;
	String texte ="";
	IHM ihm;


	public EspaceVente() {
		this.nbreBilletLibre = limite_nbre_billet;
	}

	/**
	 * Imprime le billet d'un voyageur
	 * @param voyageur : le voyageur qui veut un billet
	 */
	public synchronized void imprimer(Voyageur voyageur) {

		System.out.println("Impression de billet du voyageur " + voyageur.getNumero()
					+ ", billet restant avant impression " + nbreBilletLibre);

		voyageur.seDeplacer(x_guichet, y_guichet);

		if (nbreBilletLibre > 0) {
			//s'il y a un billet, on lance l'impression
			texte = "Inpression en cours...";
			ihm.repaint(); //on redessine l'interface graphique

			try {
				Thread.sleep(temps_impression);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nbreBilletLibre--;
			voyageur.setEtat("B - Muni d'un ticket");
			voyageur.setDisposeBillet(true);  //modifie cette variable pour informer le voyageur qu'il a bien un ticket et qu'il peut aller au quai
			notifyAll();  //on notifie les voyageurs qui attendent pour prendre leurs tickets

			texte = "Inpression terminé";
			ihm.repaint(); //on redessine l'interface graphique


		}
		else
		{
			texte = "Plus de billet";
			ihm.repaint();
		}







	}



	/**
	 * Dessine l'espace vente
	 * @param g : le graphique
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D gHere = (Graphics2D) g;
		gHere.drawRect(x_debut, y_debut, width, height);
		gHere.drawString("Espace vente  ("+ nbreBilletLibre+" billet"+((nbreBilletLibre>1)?"s":"")+" restant"+((nbreBilletLibre>1)?"s":"")+")",
				x_debut + width/2 -100 , y_debut+height/2 - 25);
		gHere.drawString(texte, x_debut + width/2 - 50 , y_debut+height-25);

		//Dessine l'entrée
		gHere.drawLine(-5, entree_y1, 10, entree_y1);
		gHere.drawLine(-5, entree_y2, 10, entree_y2);
		gHere.drawString("Entrée", 5, (entree_y1 + entree_y2) / 2);



		//Dessine la sortie
		gHere.drawLine(sortie_x1, ihm.getHeight() - 75, sortie_x1, ihm.getHeight()+5);
		gHere.drawLine(sortie_x2, ihm.getHeight() - 75, sortie_x2, ihm.getHeight()+5);
		gHere.drawString("Sortie", (sortie_x1 + sortie_x2) / 2, ihm.getHeight() - 75);


	}


	public void setIhm(IHM ihm) {
		this.ihm = ihm;

		x_debut =10;
		y_debut = 425;
		width = ihm.getWidth()/4;
		height = 200;

		//la position du guichet
		x_guichet =( x_debut + width )/2 ;
		y_guichet = y_debut + height + 5 ;

		//les données de la porte d'entrée
		entree_y1 = 630 ;
		entree_y2 = (ihm.getHeight()>725)?725:ihm.getHeight()-10;

		//les données de la porte de sortie
		sortie_x1 = ihm.getWidth() / 4 + 30 ;
		sortie_x2 = sortie_x1 + 100;

	}


	public int getSortie_x1() {
		return sortie_x1;
	}


	public int getSortie_x2() {
		return sortie_x2;
	}


	public int getEntree_y1() {
		return entree_y1;
	}


	public int getEntree_y2() {
		return entree_y2;
	}


}

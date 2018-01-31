package org.inria.restlet.mta.ihm;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.inria.restlet.mta.internals.EspaceQuai;
import org.inria.restlet.mta.internals.EspaceVente;
import org.inria.restlet.mta.internals.Train;
import org.inria.restlet.mta.internals.Voyageur;

public class Panneau extends JPanel {

	IHM ihm;
	EspaceQuai quai;
	EspaceVente vente;


	List<Voyageur> voyageurs;
	List<Train> trains;

	/**
	 * Constructeur
	 * @param quai :la gare
	 * @param vente : l'espace vente
	 */
	public Panneau(EspaceQuai quai,EspaceVente vente)
	{
		voyageurs = new ArrayList();
		trains = new ArrayList();


		this.quai = quai;
		this.vente = vente;

	}






	public void setIHM(IHM ihm) {
		this.ihm = ihm;
	}






	/**
	 * redessine les composants graphique
	 */
	public  synchronized void paintComponent(Graphics g)
	{

		//dessine l'espace quai
		quai.paintComponent(g);

		//trace un trait separant l'espace vente de l'espace quai
		g.drawLine(0, 420, IHM.tailleEcran.width, 420);

		//dessine l'espace quai
		vente.paintComponent(g);

		//dessine les voyageurs
		for(Voyageur voyageur : voyageurs)
		{
			voyageur.paintComponent(g);
		}

		//dessine les trains
		for(Train train : trains)
		{
			train.paintComponent(g);
		}




	}


	/**
	 * Ajoute un voyageur
	 * @param voyageur  :le voyageur à ajouter
	 */
	public void ajouterVoyageur(Voyageur voyageur)
	{
		voyageurs.add(voyageur);
	}


	/**
	 * Ajoute un train
	 * @param train  :le train à ajouter
	 */
	public void ajouterTrain(Train train)
	{
		trains.add(train);
	}



}

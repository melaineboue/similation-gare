package org.inria.restlet.mta.ihm;


import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.inria.restlet.mta.internals.EspaceQuai;

public class IHM extends JFrame implements ActionListener{

	public static final String FRAME_NAME="TP CSR";
	public static Dimension tailleEcran;

	EspaceQuai gare;  //la gare
	Panneau panneau;
	Thread generatetrain;
	Thread generatevoyageur;

	JMenuItem menuTrain = new JMenuItem("Stopper les trains");
	JMenuItem menuVoyageur = new JMenuItem("Stopper les voyageurs");
	int cptTrain=0,cptVoyageur=0;






	public IHM(Panneau panneau)
	{
		this.panneau = panneau;
		this.panneau.setIHM(this);
		tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		this.setSize(tailleEcran);  //definit la taille de la fenetre
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //Pour fermer la fenetre quand on clique sur la croix rouge (bouton fermer) de la fenetre
		this.setTitle(FRAME_NAME);  //defini le titre de l'editeur
		//this.setLocationRelativeTo(null);

		JMenuBar menu = new JMenuBar();
		JMenu menuG = new JMenu("Controler");

		menuG.add(menuTrain);
		menuG.add(menuVoyageur);
		menu.add(menuG);

		menuTrain.addActionListener(this);
		menuVoyageur.addActionListener(this);


		this.setJMenuBar(menu);

		this.setContentPane(panneau);
		this.setVisible(true);



	}


	public Panneau getPanneau() {
		return panneau;
	}


	public int getHeight() {
		return tailleEcran.height;
	}


	public int getWidth() {
		return tailleEcran.width;
	}


	@Override
	public void actionPerformed(ActionEvent a) {

		if(a.getSource()==menuTrain)
		{
			if(cptTrain%2==0)
			{

				generatetrain.suspend();
				menuTrain.setText("Lancer les trains");
				cptTrain++;
			}
			else if(menuTrain.getText().equals("Lancer les trains"))
			{
				generatetrain.resume();
				menuTrain.setText("Stopper les trains");
				cptTrain++;

			}

		}




		if(a.getSource()==menuVoyageur)
		{
			if(cptVoyageur%2==0)
			{

				generatevoyageur.suspend();
				menuVoyageur.setText("Lancer les voyageurs");
				cptVoyageur++;
			}
			else
			{
				generatevoyageur.resume();
				menuVoyageur.setText("Stopper les voyageurs");
				cptVoyageur++;

			}

		}


	}


	public void setGenerateTrain(Thread generatetrain) {
		this.generatetrain = generatetrain;
	}


	public void setGenerateVoyageur(Thread generatevoyageur) {
		this.generatevoyageur = generatevoyageur;
	}


















}

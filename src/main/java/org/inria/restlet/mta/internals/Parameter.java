package org.inria.restlet.mta.internals;

/**
 * Les paramètre du système
 * @author SARR
 * @author BOUE
 *
 */
public class Parameter {

	//Espace Vente
	public static final int TEMPS_IMPRESSION_BILLET = 1000 ;
	public static final int NOMBRE_INITIAL_DE_BILLET = 100 ;


	//Espace Quai
	public static final int NOMBRE_VOIE_QUAI = 5 ; //nombre voie au quai, compris entre 1 et 7 (à cause de la limite de l'interface graphique)



	//Train
	public static final int TEMPS_ARRET_AU_QUAI = 5000 ; //temps d'arrêt du train au quai (en ms)
	public static final int CAPACITE_TRAIN = 100;
	public static final int PLACE_VERROUILLE_PAR_DEFAUT = 97;


}

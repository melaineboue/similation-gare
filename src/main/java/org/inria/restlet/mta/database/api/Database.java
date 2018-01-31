package org.inria.restlet.mta.database.api;

import java.util.Collection;

import org.inria.restlet.mta.ihm.IHM;
import org.inria.restlet.mta.internals.EspaceQuai;
import org.inria.restlet.mta.internals.EspaceVente;
import org.inria.restlet.mta.internals.Train;
import org.inria.restlet.mta.internals.Voyageur;

/**
 *
 * Interface to the database.
 *
 * @author SARR
 * @author BOUE
 *
 */
public interface Database
{



    /**
     * Retourne la liste des voyageurs
     * @return liste des voyageurs
     */
    public Collection<Voyageur> getVoyageurs();




   /**
    * Retourne la liste des trains
    * @return liste des trains
    */
   public Collection<Train> getTrains();


   /**
    * Retourne le train ayant le numero numero
    * @param numero le numero du train à retourner
    * @return un train dont le numero est passé en paramètre
    */
   public Train getTrain(int numero);


   /**
    * Retourne le voyageur ayant le numero numero
    * @param numero le numero du voyageur à retourner
    * @return un voyageur dont le numero est passé en paramètre
    */
   public Voyageur getVoyageur(int numero);





   /**
    * crée un voyageur
 * @param nom : nom du voyageur
    * @return le voyageur crée
    */
  Voyageur createVoyageur(String nom);



   /**
    * crée un train avec un certain nombre de place verrouillé
 * @param nom : le nom du train
    * @param nombreVerrou : le nombre de place qui doit être vide quand le train repartira
    * @return le train crée
    */
  Train createTrain(String nom, int nombreVerrou);


  /**
   * Initialise l'espace quai et l'espace vente dans la base de données,
   * ceux ci vont nous servir à créer des voyageurs et trains
   * @param quai : Espace quai
   * @param vente : Espace vente
   * @param ihm : l'interface
   */
  public void setParameter(EspaceQuai quai, EspaceVente vente, IHM ihm);

  /**
   * Supprime du système un voyageur qui n'a pas eu de ticket
   * @param numero : le numero du voyageur
   */
  public void remove(int numero);



  /**
   * Afficher l'etat du système
   */
  public void afficherEtat();







}

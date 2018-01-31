package org.inria.restlet.mta.backend;



import org.inria.restlet.mta.database.api.Database;
import org.inria.restlet.mta.database.api.impl.InMemoryDatabase;
import org.inria.restlet.mta.ihm.IHM;
import org.inria.restlet.mta.internals.EspaceQuai;
import org.inria.restlet.mta.internals.EspaceVente;

/**
 *
 * Backend for all resources.
 *
 * @author SARR
 * @author BOUE
 *
 */
public class Backend
{
    /** Database.*/
    private Database database_;

    public Backend()
    {
        database_ = new InMemoryDatabase();
    }

    public Database getDatabase()
    {
        return database_;
    }


    public void initDatabaseParameter(EspaceQuai quai, EspaceVente vente, IHM ihm)
    {
       database_.setParameter(quai, vente,ihm);


    }

}

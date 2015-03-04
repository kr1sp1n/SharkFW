package net.sharkfw.knowledgeBase.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import net.sharkfw.knowledgeBase.AbstractSharkKB;
import net.sharkfw.knowledgeBase.ContextCoordinates;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Interest;
import net.sharkfw.knowledgeBase.Knowledge;
import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.PeerTaxonomy;
import net.sharkfw.knowledgeBase.STSet;
import net.sharkfw.knowledgeBase.SemanticNet;
import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkCS;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.SpatialSTSet;
import net.sharkfw.knowledgeBase.SpatialSemanticTag;
import net.sharkfw.knowledgeBase.TimeSTSet;
import net.sharkfw.knowledgeBase.TimeSemanticTag;
import net.sharkfw.knowledgeBase.inmemory.InMemoPeerSemanticNet;
import net.sharkfw.knowledgeBase.inmemory.InMemoPeerTaxonomy;
import net.sharkfw.knowledgeBase.inmemory.InMemoSemanticNet;
import net.sharkfw.knowledgeBase.inmemory.InMemoSpatialSTSet;
import net.sharkfw.knowledgeBase.inmemory.InMemoTimeSTSet;
import net.sharkfw.system.L;

/**
 * This shall become a SQL implementation of the SharkKB.
 * 
 * Scatch:
 * properties:
 * <ul>
 * <li>Properties</li>
 * <li>Owner</li>
 * <li>Knowledge</li>
 * <li>Vocabulary: STSet, PeerSTSet, SpatialSTSet, TimeSTSet</li>
 * </ul>
 * 
 * @author thsc
 */
public class SQLSharkKB extends AbstractSharkKB implements SharkKB {
    private Connection connection;
    private String connectionString;
    private String user;
    private String pwd;
    
    public static final int SEMANTIC_TAG_TYPE = 0;
    public static final int PEER_SEMANTIC_TAG_TYPE = 1;
    public static final int SPATIAL_SEMANTIC_TAG_TYPE = 2;
    public static final int TIME_SEMANTIC_TAG_TYPE = 3;
    
    public static final int SEMANTIC_TAG = SEMANTIC_TAG_TYPE;
    public static final int CONTEXT_POINT = 1;
    public static final int KNOWLEDGEBASE = 2;
    public static final int INFORMATION = 3;
    
    public SQLSharkKB(String connectionString, String user, String pwd) throws SharkKBException {
	try {
            this.connectionString = connectionString;
            this.user = user;
            this.pwd = pwd;
            connection = DriverManager.getConnection(connectionString, user, pwd);
 	} catch (SQLException e) {
            throw new SharkKBException("cannot connect to database: " + e.getLocalizedMessage());
 	}
        
 	if (connection == null) {
            throw new SharkKBException("cannot connect to database: reason unknown");
	}
        
        // check if tables already created - if not - do it
        this.setupKB();
        
        // set vocabulary in this kb - use inherited methods
        SemanticNet topics = new InMemoSemanticNet(new SQLGenericTagStorage(this));
        PeerTaxonomy peers = new InMemoPeerTaxonomy(new InMemoPeerSemanticNet((new SQLGenericTagStorage(this))));
        SpatialSTSet locations = new InMemoSpatialSTSet(new SQLGenericTagStorage(this));
        TimeSTSet times = new InMemoTimeSTSet(new SQLGenericTagStorage(this));
        
        this.setTopics(topics);
        this.setPeers(peers);
        this.setLocations(locations);
        this.setTimes(times);
        
        // TODO attach knowledge
    }
    
    Connection getConnection() {
        return this.connection;
    }

    public static final String SHARKKB_TABLE = "knowledgebase";
    public static final String ST_TABLE = "semantictags";
    public static final String PROERTIES_TABLE = "properties";
    public static final String SI_TABLE = "subjectidentifier";
    public static final String ADDRESS_TABLE = "addresses";
    public static final String CP_TABLE = "contextpoints";
    
    public static final String MAX_SI_SIZE = "200";
    public static final String MAX_ST_NAME_SIZE = "200";
    public static final String MAX_EWKT_NAME_SIZE = "200";
    public static final String MAX_PROPERTY_NAME_SIZE = "200";
    public static final String MAX_PROPERTY_VALUE_SIZE = "200";
    public static final String MAX_ADDR_SIZE = "200";
    
    /**
     * Tables: 
     * <ul>
     * <li>SemanticTags</li>
     * <li>Properties</li>
     * <li>SubjectIdentifier</li>
     * <li>addresses</li>
     * <li>knowledgebase</li>
     * <li>contextpoints</li>
     * <Iul>
     */
    private void setupKB() throws SharkKBException {
        Statement statement = null;
        try {
            statement  = connection.createStatement();
            
            /************** Knowledge base table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.SHARKKB_TABLE);
                L.d(SQLSharkKB.SHARKKB_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.SHARKKB_TABLE + "does not exists - create", this);
                try { statement.execute("drop sequence kbid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence kbid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.SHARKKB_TABLE + 
                        " (id integer PRIMARY KEY default nextval('kbid'), "
                        + "ownerID integer" // foreign key in st table
                        + ");");
            }

            /************** semantic tag table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.ST_TABLE);
                L.d(SQLSharkKB.ST_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.ST_TABLE + " does not exists - create", this);
                try { statement.execute("drop sequence stid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence stid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.ST_TABLE + 
                        " (id integer PRIMARY KEY default nextval('stid'), "
                        + "name character varying("+ SQLSharkKB.MAX_ST_NAME_SIZE + "), "
                        + "ewkt character varying("+ SQLSharkKB.MAX_EWKT_NAME_SIZE + "), "
                        + "startTime bigint, "
                        + "durationTime bigint, "
                        + "st_type smallint"
                        + ");");
            }

            /************** properties table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.PROERTIES_TABLE);
                L.d(SQLSharkKB.PROERTIES_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.PROERTIES_TABLE + " does not exists - create", this);
                try { statement.execute("drop sequence propertyid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence propertyid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.PROERTIES_TABLE + 
                        " (id integer PRIMARY KEY default nextval('propertyid'), "
                        + "name character varying("+ SQLSharkKB.MAX_PROPERTY_NAME_SIZE + "), "
                        + "value character varying("+ SQLSharkKB.MAX_PROPERTY_VALUE_SIZE + "), "
                        + "stID integer, "
                        + "entity_type smallint"
                        + ");");
            }
            
            /************** si table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.SI_TABLE);
                L.d(SQLSharkKB.SI_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.SI_TABLE + " does not exists - create", this);
                try { statement.execute("drop sequence siid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence siid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.SI_TABLE + 
                        " (id integer PRIMARY KEY default nextval('siid'), "
                        + "si character varying("+ SQLSharkKB.MAX_SI_SIZE + ") UNIQUE, "
                        + "stID integer"
                        + ");");
            }
            
            // TODO: si must be unique!
            
            /************** addresses table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.ADDRESS_TABLE);
                L.d(SQLSharkKB.ADDRESS_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.ADDRESS_TABLE + " does not exists - create", this);
                try { statement.execute("drop sequence addrid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence addrid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.ADDRESS_TABLE + 
                        " (id integer PRIMARY KEY default nextval('addrid'), "
                        + "addr character varying("+ SQLSharkKB.MAX_ADDR_SIZE + "), "
                        + "stID integer"
                        + ");");
            }
            
            /************** contextpoints table *****************************/
            try {
                statement.execute("SELECT * from " + SQLSharkKB.CP_TABLE);
                L.d(SQLSharkKB.CP_TABLE + " already exists", this);
            }
            catch(SQLException e) {
                // does not exist: create
                L.d(SQLSharkKB.CP_TABLE + " does not exists - create", this);
                try { statement.execute("drop sequence cpid;"); }
                catch(SQLException ee) { /* ignore */ }
                statement.execute("create sequence cpid;");
                statement.execute("CREATE TABLE " + SQLSharkKB.CP_TABLE + 
                        " (id integer PRIMARY KEY default nextval('cpid'), "
                        + "topicID integer, "
                        + "originatorID integer, "
                        + "peerID integer, "
                        + "remotePeerID integer, "
                        + "locationID integer, "
                        + "timeID integer, "
                        + "direction smallint"
                        + ");");
            }
        } catch (SQLException e) {
            L.w("error while setting up tables: " + e.getLocalizedMessage(), this);
            throw new SharkKBException("error while setting up tables: " + e.getLocalizedMessage());
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }
    }
    
    /**
     * Removes all tables in SQL database which store Shark data
     * @throws net.sharkfw.knowledgeBase.SharkKBException
     */
    public void drop() throws SharkKBException {
        Statement statement = null;
        try {
            statement  = connection.createStatement();
            
            /************** Knowledge base table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.SHARKKB_TABLE);
            }
            catch(SQLException e) {
                // go ahead
            }

            /************** semantic tag table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.ST_TABLE);
            }
            catch(SQLException e) {
            }

            /************** properties table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.PROERTIES_TABLE);
            }
            catch(SQLException e) {
            }
            
            /************** si table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.SI_TABLE);
            }
            catch(SQLException e) {
            }
            
            /************** addresses table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.ADDRESS_TABLE);
            }
            catch(SQLException e) {
            }
            
            /************** contextpoints table *****************************/
            try {
                statement.execute("DROP TABLE " + SQLSharkKB.CP_TABLE);
            }
            catch(SQLException e) {
            }
        } catch (SQLException e) {
            L.w("error while creating SQL-statement: " + e.getLocalizedMessage(), this);
            throw new SharkKBException("error while creating SQL-statement: " + e.getLocalizedMessage());
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }
    }
    
    /**
     * close database
     * @throws net.sharkfw.knowledgeBase.SharkKBException
     */
    public void close() throws SharkKBException {
        try {
            this.connection.close();
            this.connection = null;
        } catch (SQLException ex) {
            throw new SharkKBException(ex.getLocalizedMessage());
        }
    }
    
    /**
     * Reconnect after prior close. Note: An open connection would be
     * closed an re-opened.
     * 
     * Note also: A jdbc connection is already opened when constructor is
     * called.
     * 
     * @throws SharkKBException 
     */
    public void reconnect() throws SharkKBException {
        if(this.connection != null) {
            this.close();
        }

        try {
            this.connection = DriverManager.getConnection(connectionString, user, pwd);
        } catch (SQLException ex) {
            throw new SharkKBException(ex.getLocalizedMessage());
        }
    }
    
    /**
     * JDBC connection is open or not
     * @return 
     */
    public boolean connected() {
        return this.connection != null;
    }
    
    String[] getSIs(int id) {
        String[] sis = null;
        
        Statement statement = null;
        try {
            statement  = connection.createStatement();
            
            ArrayList<String> sisList = new ArrayList(); 
            ResultSet result = statement.executeQuery("SELECT si from " + SQLSharkKB.SI_TABLE + " where stid = " + id + ";");
            while(result.next()) {
                sisList.add(result.getString(1));
            }

            if(!sisList.isEmpty()) {
                sis = new String[sisList.size()];
                Iterator<String> sisIter = sisList.iterator();
                for(int i = 0; i < sis.length; i++) {
                    sis[i] = sisIter.next();
                }
            }
        } catch (SQLException e) {
        }
        finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }
        
        return sis;
    }

    @Override
    public Interest createInterest(STSet topics, PeerSemanticTag originator, PeerSTSet peers, PeerSTSet remotePeers, TimeSTSet times, SpatialSTSet locations, int direction) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOwner(PeerSemanticTag owner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PeerSemanticTag getOwner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContextPoint getContextPoint(ContextCoordinates coordinates) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContextCoordinates createContextCoordinates(SemanticTag topic, PeerSemanticTag originator, PeerSemanticTag peer, PeerSemanticTag remotepeer, TimeSemanticTag time, SpatialSemanticTag location, int direction) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContextPoint createContextPoint(ContextCoordinates coordinates) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Knowledge createKnowledge() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<ContextPoint> contextPoints(SharkCS cs, boolean matchAny) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enumeration<ContextPoint> getAllContextPoints() throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interest createInterest() throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Interest createInterest(ContextCoordinates cc) throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<SemanticTag> getTags() throws SharkKBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

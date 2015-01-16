package net.sharkfw.knowledgeBase.sync;

import java.beans.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.system.L;

/**
 *
 * @author s0539710
 */
/**
 * A class that holds the timestamp of the last meeting between this and any other peer.
 * 
 * With this timestamp the knowledge that needs to be synced with another peer can be retrieved 
 * from the knowledge base.
 * @author simon
 */
class TimestampList implements Serializable {
    protected static String FILENAME = "./.shark_timestamps";
    protected List<PeerTimestamp> _timestamps;
        
    public TimestampList() {
        retrieve();
    }
    
    public TimestampList(PeerSTSet peersToSyncWith) {
        retrieve();
        // Add all possible peers to queue
        Enumeration<PeerSemanticTag> peerEnum = peersToSyncWith.peerTags();
        while (peerEnum.hasMoreElements()) {
            _timestamps.add(new PeerTimestamp(peerEnum.nextElement()));
        }
    }
    
    /**
     * Add a peer that will be synced with in the future
     * @param peer 
     */
    public void newPeer(PeerSemanticTag peer) {
        _timestamps.add(new PeerTimestamp(peer));
        persist();
    }
    
    public void removePeer(PeerSemanticTag peer) {
        PeerTimestamp waldo = findPeerTimestamp(peer);
        if (waldo == null) {
            L.w("Tried to remove an unexisting peer from SyncKP Peer timestamps.");
        }
        else {
            _timestamps.remove(waldo);
            persist();
        }
    }
    
    /**
     * Returns all peers currently in the peer timestamp list
     * @return all peers
     * @throws SharkKBException 
     */
    public PeerSTSet getPeers() throws SharkKBException {
        PeerSTSet myPeerSTSet = InMemoSharkKB.createInMemoPeerSTSet();
        for (PeerTimestamp s : _timestamps) {
            myPeerSTSet.merge(s.getPeer());
        }
        return myPeerSTSet;
    }
    
    /** 
     * Get the timestamp when a peer was last encountered.
     * @param peer
     * @return Timestamp of last encounter
     */
    public Date getTimestamp(PeerSemanticTag peer) {
        PeerTimestamp p = findPeerTimestamp(peer);
        if (p == null) return null;
        else return p.getDate();
    }
    
    /**
     * Resets the time of last meeting with this peer to now.
     * @param peer 
     */
    public void resetTimestamp(PeerSemanticTag peer) {
        PeerTimestamp p = findPeerTimestamp(peer);
        if (p != null) {
            p.resetDate();
            persist();
        }
    }
    
    /**
     * Sets the time of last meeting with this peer to 01,01,1970!
     * This will probably sync ALL knowledge with this peer again, so use with caution.
     * @param peer 
     */
    public void setTimestampNull(PeerSemanticTag peer) {
        PeerTimestamp p = findPeerTimestamp(peer);
        if (p != null) {
            p.setTimestampNull();
            persist();
        }
    }
    
    /**
     * Sets the time of last meeting with ALL peers to 01,01,1970!
     * This will probably sync ALL knowledge with ALL peers again, so use with caution.
     * @param peer 
     */
    public void setAllTimestampsNull() {
        for (PeerTimestamp p : _timestamps) {
            p.setTimestampNull();
        }
        persist();
    }
    
    private PeerTimestamp findPeerTimestamp(PeerSemanticTag p) {
        // Find the peer in our list
        Iterator<PeerTimestamp> i = _timestamps.iterator();
        PeerTimestamp waldo;
        // Lets look for waldo
        while(i.hasNext()) {
            waldo = i.next();
            if(waldo.getPeer().equals(p))
                return waldo;
        }
        
        return null;
    }
    
    protected void persist() {
        File f = new File(FILENAME);
        
        if(_timestamps == null || _timestamps.isEmpty()){
            f.delete();
            return;
        }
        else if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                L.e("Creating timestamp file caused IO exception: " + e.getMessage());
            }
        }
            
        // New style: Implicit closing of streams and files with try-with-resources. Cool, huh?
        try (FileOutputStream fos = new FileOutputStream(FILENAME); ObjectOutputStream oos = new ObjectOutputStream(fos)) {               
            oos.writeObject(_timestamps);               
        }catch(FileNotFoundException e){
            L.e("Writing timestamps to disk caused File not Found exception: " + e.getMessage());
        } catch (IOException e) {
            L.e("Writing timestamps to disk caused IO exception: " + e.getMessage());
        } 
    }
    
    protected void retrieve() {
        _timestamps = new ArrayList<>();
                
        try(FileInputStream fos = new FileInputStream(FILENAME); ObjectInputStream oos = new ObjectInputStream(fos)) {               
            _timestamps = (List<PeerTimestamp>) oos.readObject();               
        }catch(FileNotFoundException e){
            L.e("Reading timestamps from disk caused File not Found exception: " + e.getMessage());
        } catch (IOException e) {
            L.e("Reading timestamps from disk caused IO exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            L.e("Reading timestamps from disk caused Class not Found exception: " + e.getMessage());
        } 
    }
    
    class PeerTimestamp implements Serializable {
        private Date _date;
        private final PeerSemanticTag _peer;
        
        public PeerTimestamp(PeerSemanticTag peer) {
            _peer = InMemoSharkKB.createInMemoCopy(peer);
            _date = new Date(0);
        }
        
        /**
         * Returns the date when this peer was last met.
         * @return date of last meeting (actually, of last reset)
         */
        public Date getDate() {
            return _date;
        }
        
        /**
         * Resets the time of last meeting with this peer to now.
         */
        public void resetDate() {
            _date = new Date();
        }
        
        public void setTimestampNull() {
            _date = new Date(0);
        }
        
        public PeerSemanticTag getPeer() {
            return _peer;
        }
        
        
    }
}


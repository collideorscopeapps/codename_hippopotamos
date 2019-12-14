package it.collideorscopeapps.codename_hippopotamos.model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Playlist {

    private String description;

    public String getDescription() {
        return description;
    }

    private boolean disabled;

    public boolean isDisabled() {
        return  this.disabled;
    }

    public TreeMap<Integer, Schermata> getRankedSchermate() {
        return rankedSchermate;
    }

    private TreeMap<Integer, Schermata> rankedSchermate;// key is the play order
    private TreeMap<Integer, Integer> rankedSchermateIds;// key is the schermata id, value the play rank

    public Playlist(String description, TreeMap<Integer,
            Integer> rankedSchermateIds,
                    boolean disabled) {

        this.description = description;
        this.rankedSchermateIds = rankedSchermateIds;
        this.disabled = disabled;
    }

    public void setSchermate(TreeMap<Integer, Schermata> schermateSuperSet) {

        //TODO add tests for this

        this.rankedSchermate = new TreeMap<>();

        for(Schermata schermata: schermateSuperSet.values()) {
            int currentSchermataId = schermata.getId();
            if(this.rankedSchermateIds.containsKey(currentSchermataId)) {
                int playOrder = this.rankedSchermateIds.get(currentSchermataId);
                this.rankedSchermate.put(playOrder, schermata);
            }
        }
    }
}

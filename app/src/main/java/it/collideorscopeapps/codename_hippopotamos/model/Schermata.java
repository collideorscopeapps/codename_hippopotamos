package it.collideorscopeapps.codename_hippopotamos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Schermata implements Serializable {

    private int id;
    private String title;
    private String description;
    private String linguisticNotes;
    private String citation;
    private String easterEggComment;

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void addQuote(Quote quote) {

        this.quotes.add(quote);
    }

    private ArrayList<Quote> quotes;

    public Schermata(int id,
                     String title,
                     String description,
                     String linguisticNotes,
                     String citation,
                     String easterEggComment) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.linguisticNotes = linguisticNotes;
        this.citation = citation;
        this.easterEggComment = easterEggComment;

        this.quotes = new ArrayList<Quote>();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if ((obj instanceof Schermata) && (((Schermata) obj).id == this.id)) {
            return true;
        } else {
            return false;
        }
    }
}

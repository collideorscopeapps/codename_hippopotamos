package it.collideorscopeapps.codename_hippopotamos.model;

import java.util.ArrayList;

public class Schermata {

    private int id;
    private String description;
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
                     String description,
                     String citation,
                     String easterEggComment) {
        this.id = id;
        this.description = description;
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

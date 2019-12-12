package it.collideorscopeapps.codename_hippopotamos.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Schermata implements Serializable {

    //TODO add option in the db: boolean highlighAccent
    //to be used in ear training screens

    public int getId() {
        return id;
    }

    private int id;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLinguisticNotes() {
        return linguisticNotes;
    }

    public String getCitation() {
        return "(" + citation + ")";
    }

    public String getEasterEggComment() {
        return easterEggComment;
    }

    private String title;
    private String description;
    private String citation;
    private String linguisticNotes;
    private String easterEggComment;

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public String getQuotesAsString() {

        if(quotesAsString != null && !quotesAsString.isEmpty()) {
            return quotesAsString;
        }

        quotesAsString = "";

        int currentQuoteNum = 1;
        for(Quote quote : this.getQuotes()) {

            String prevSeparator = "";
            String quoteText = quote.getQuoteText();
            String closingComma = "";

            boolean isFirstQuote = currentQuoteNum == 1;
            if(!isFirstQuote) {
                prevSeparator = ", ";
            }

            boolean isLastQuote = currentQuoteNum == this.quotes.size();
            if(isLastQuote) {
                final String comma = ".";
                if(!quoteText.endsWith(comma)) {
                    closingComma = comma;
                }
            }

            quotesAsString += prevSeparator + quoteText + closingComma;

            currentQuoteNum++;
        }

        return quotesAsString;
    }

    public void addQuote(Quote quote) {

        this.quotes.add(quote);
    }

    private ArrayList<Quote> quotes;
    private String quotesAsString;

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

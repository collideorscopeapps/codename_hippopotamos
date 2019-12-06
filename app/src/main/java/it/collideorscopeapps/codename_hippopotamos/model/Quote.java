package it.collideorscopeapps.codename_hippopotamos.model;

public class Quote {

    private int positionInSchermata;
    private String quoteText;
    private String audioFileName;

    // TODO translation, according to user preference (english, italian)
    // TODO translation into schermata class?

    public Quote(int positionInSchermata,
                 String quoteText,
                 String audioFileName) {
        this.positionInSchermata = positionInSchermata;
        this.quoteText = quoteText;
        this.audioFileName = audioFileName;
    }


}

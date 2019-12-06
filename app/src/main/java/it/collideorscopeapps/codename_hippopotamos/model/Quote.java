package it.collideorscopeapps.codename_hippopotamos.model;

public class Quote {

    private int id;
    private int positionInSchermata;
    private String quoteText;
    private String phoneticTranscription;
    private String audioFileName;

    // TODO translation, according to user preference (english, italian)
    // TODO translation into schermata class?

    public Quote(int id,
                 int positionInSchermata,
                 String quoteText,
                 String phoneticTranscription,
                 String audioFileName) {

        this.id = id;
        this.positionInSchermata = positionInSchermata;
        this.quoteText = quoteText;
        this.phoneticTranscription = phoneticTranscription;
        this.audioFileName = audioFileName;
    }


}

package it.collideorscopeapps.codename_hippopotamos.model;

public class Quote {

    private int id;
    private String quoteText;

    private String easterEggComment;
    private String grAudioResFilename;

    public Quote(int id, String quoteText,
                 String easterEggComment,
                 String grAudioResFilename) {
        this.id = id;
        this.quoteText = quoteText;
        this.easterEggComment = easterEggComment;
        this.grAudioResFilename = grAudioResFilename;
    }


}

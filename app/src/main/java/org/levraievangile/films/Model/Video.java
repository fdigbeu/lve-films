package org.levraievangile.films.Model;

public class Video {
    private int id;
    private String urlacces;
    private String src;
    private String titre;
    private String auteur;
    private String duree;
    private String date;
    private String type_libelle;
    private String type_shortcode;
    private int mipmap;

    @Override
    public String toString() {
        String jsonData = "";
        jsonData += "{";
        jsonData += "\"id\" : \""+id+"\",";
        jsonData += "\"urlacces\" : \""+urlacces+"\",";
        jsonData += "\"src\" : \""+src+"\",";
        jsonData += "\"titre\" : \""+titre+"\",";
        jsonData += "\"auteur\" : \""+auteur+"\",";
        jsonData += "\"duree\" : \""+duree+"\",";
        jsonData += "\"date\" : \""+date+"\",";
        jsonData += "\"type_libelle\" : \""+type_libelle+"\",";
        jsonData += "\"type_shortcode\" : \""+type_shortcode+"\",";
        jsonData += "\"mipmap\" : \""+mipmap+"\",";
        jsonData += "}";
        return jsonData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlacces() {
        return urlacces;
    }

    public void setUrlacces(String urlacces) {
        this.urlacces = urlacces;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType_libelle() {
        return type_libelle;
    }

    public void setType_libelle(String type_libelle) {
        this.type_libelle = type_libelle;
    }

    public String getType_shortcode() {
        return type_shortcode;
    }

    public void setType_shortcode(String type_shortcode) {
        this.type_shortcode = type_shortcode;
    }

    public int getMipmap() {
        return mipmap;
    }

    public void setMipmap(int mipmap) {
        this.mipmap = mipmap;
    }
}

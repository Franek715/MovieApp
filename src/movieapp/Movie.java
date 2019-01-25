package movieapp;

import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private final SimpleStringProperty title;
    private final SimpleStringProperty length;
    private final SimpleStringProperty language;
    private final SimpleStringProperty date;
    private final SimpleStringProperty id;
    
    
    public Movie(){
        this.title = new SimpleStringProperty("");
        this.length = new SimpleStringProperty("");
        this.language = new SimpleStringProperty("");
        this.date = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
    
    public Movie(String title, String length, String lang, String date){
        this.title = new SimpleStringProperty(title);
        this.length = new SimpleStringProperty(length);
        this.language = new SimpleStringProperty(lang);
        this.date = new SimpleStringProperty(date);
        this.id = new SimpleStringProperty("");
    }
    
    public Movie(Integer id, String title, String length, String lang, String date){
        this.title = new SimpleStringProperty(title);
        this.length = new SimpleStringProperty(length);
        this.language = new SimpleStringProperty(lang);
        this.date = new SimpleStringProperty(date);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }
    
    public String getTitle() {
        return this.title.get();
    }
    
    public void setTitle(String name) {
        this.title.set(name);
    }
    
    public String getLength() {
        return this.length.get();
    }
    
    public void setLength(String name) {
        this.length.set(name);
    }
    
    public String getLanguage() {
        return this.language.get();
    }
    
    public void setLanguage(String name) {
        this.language.set(name);
    }
    
    public String getDate() {
        return this.date.get();
    }
    
    public void setDate(String name) {
        this.date.set(name);
    }
    
    public String getId() {
        return this.id.get();
    }
    
    public void setId(String name) {
        this.id.set(name);
    }
}

package gameapp;

import javax.persistence.*;

@Entity
public class Game {

    private int id;
    private String title;
    private String genre;
    private Developer dev;

    public Game(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Game(String title, String genre, Developer dev) {
        this.title = title;
        this.genre = genre;
        this.dev = dev;
    }

    public Game() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }

    @Override
    public String toString() {
        return "\nID: " + id +
                "\nTitle: " + title +
                "\nGenre: " + genre +
                dev;
    }
}

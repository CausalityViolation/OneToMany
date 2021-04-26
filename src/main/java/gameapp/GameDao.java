package gameapp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameDao {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    Scanner input = new Scanner(System.in);


    public void viewAll() {

        viewGames();
        viewDevelopers();

    }

    public void viewGames() {

        EntityManager em = emf.createEntityManager();
        Query gamesQuery = em.createQuery("SELECT g FROM Game g");

        @SuppressWarnings("unchecked")
        List<Game> content = gamesQuery.getResultList();
        System.out.println("<Displaying all games>");

        for (Game g : content) {
            System.out.println(g);
        }
        System.out.println("<End of list>\n");

    }

    public void viewDevelopers() {

        EntityManager em = emf.createEntityManager();
        Query developerQuery = em.createQuery("SELECT d FROM Developer d");

        @SuppressWarnings("unchecked")
        List<Developer> content = developerQuery.getResultList();
        System.out.println("<Displaying all developers>");

        for (Developer d : content) {
            System.out.println(d);
        }
        System.out.println("<End of list>\n");

    }


    public void newDeveloper() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Input Developer Name: ");
        String name = input.nextLine();

        System.out.print("Input Developer HQ Location: ");
        String HQ = input.nextLine();

        Developer d1 = new Developer(name, HQ);

        em.getTransaction().begin();
        em.persist(d1);
        em.getTransaction().commit();
        em.close();

        System.out.println("<Developer successfully added to library>");

    }


    public void newGame() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Input Title: ");
        String title = input.nextLine();

        System.out.print("Input Genre: ");
        String genre = input.nextLine();

        System.out.println("Would you like to add an existing Developer to your game?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("0. Cancel");

        int choice = scanInt();

        if (choice == 1) {

            viewDevelopers();

            System.out.print("Enter the ID of the Developer you'd like to connect: ");
            int devID = scanInt();

            Developer dev = em.find(Developer.class, devID);
            Game newGame = new Game(title, genre, dev);

            em.getTransaction().begin();
            em.persist(newGame);
            em.getTransaction().commit();
            em.close();


        } else if (choice == 2) {

            Game newGame = new Game(title, genre);

            em.getTransaction().begin();
            em.persist(newGame);
            em.getTransaction().commit();
            em.close();

        } else {
            return;
        }


        System.out.println("<Game successfully added to library>");


    }

    public void editGame() {

        EntityManager em = emf.createEntityManager();
        viewGames();

        System.out.print("\nInput the ID of the game you'd like to edit: ");
        int ID = scanInt();

        Game g1 = em.find(Game.class, ID);

        System.out.println("What would you like to edit?");
        System.out.println("1. Title");
        System.out.println("2. Genre");
        System.out.println("0. Return to Main Menu");

        int choice = scanInt();

        if (choice == 1) {

            System.out.println("Enter new Title: ");
            String title = input.nextLine();

            g1.setTitle(title);

        } else if (choice == 2) {

            System.out.println("Enter new Genre: ");
            String genre = input.nextLine();

            g1.setGenre(genre);

        } else {
            return;
        }

        em.getTransaction().begin();
        em.persist(g1);
        em.getTransaction().commit();
        em.close();


    }

    public void editDev() {

        EntityManager em = emf.createEntityManager();
        viewDevelopers();

        System.out.print("\nInput the ID of the Developer you'd like to edit: ");
        int ID = scanInt();

        Developer d1 = em.find(Developer.class, ID);

        System.out.println("What would you like to edit?");
        System.out.println("1. Developer Name");
        System.out.println("2. HQ Location");
        System.out.println("0. Return to Main Menu");

        int choice = scanInt();

        if (choice == 1) {

            System.out.print("Enter new Developer Name: ");
            String name = input.nextLine();

            d1.setDeveloperName(name);

        } else if (choice == 2) {

            System.out.print("Enter new HQ Location: ");
            String HQ = input.nextLine();

            d1.setHQLocation(HQ);

        } else {
            return;
        }

        em.getTransaction().begin();
        em.persist(d1);
        em.getTransaction().commit();
        em.close();

    }

    public void connectDevToGame() {

        viewAll();

        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the Game you'd like to connect: ");
        int gameID = scanInt();

        Game game = em.find(Game.class, gameID);

        System.out.print("Enter the ID of the Developer you'd like to connect: ");
        int devID = scanInt();

        Developer dev = em.find(Developer.class, devID);
        game.setDev(dev);

        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        em.close();

    }

    private int scanInt() {

        int scanned;

        while (true) {
            try {
                scanned = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please input numerical data");
                input.nextLine();
            }
        }
        input.nextLine();
        return scanned;
    }

    public void deleteGame() {

        viewGames();
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter the ID of the game you'd like to DELETE: ");
        int gameID = scanInt();

        Game game = em.find(Game.class, gameID);

        em.getTransaction().begin();
        em.remove(game);
        em.getTransaction().commit();
        em.close();

    }

    public void viewByDev() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Enter the ID of the developer whose games you'd like to view: ");
        int devID = scanInt();

        Query gamesQuery = em.createQuery("SELECT g FROM Game g WHERE g.dev.devID =: devID");
        gamesQuery.setParameter("devID", devID);

        @SuppressWarnings("unchecked")
        List<Game> content = gamesQuery.getResultList();
        System.out.println("<Displaying all games>");

        for (Game g : content) {
            System.out.println(g);
        }
        System.out.println("<End of list>\n");

    }

    public void viewByGenre() {

        EntityManager em = emf.createEntityManager();

        System.out.print("Enter the GENRE of games you'd like to view: ");
        String genre = input.nextLine();

        Query gamesQuery = em.createQuery("SELECT g FROM Game g WHERE g.genre =: genre");
        gamesQuery.setParameter("genre", genre);

        @SuppressWarnings("unchecked")
        List<Game> content = gamesQuery.getResultList();
        System.out.println("<Displaying all games with the GENRE " + genre + ">");

        for (Game g : content) {
            System.out.println(g);
        }
        System.out.println("<End of List>\n");

    }

}

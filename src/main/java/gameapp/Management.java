package gameapp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Management {

    Scanner input = new Scanner(System.in);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

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

        double min = Math.ceil(1000);
        double max = Math.floor(9999);

        int developerID = (int) Math.round(Math.floor(Math.random() * (max - min) + min));

        System.out.print("Input Developer Name: ");
        String name = input.nextLine();

        System.out.print("Input Earnings: ");
        String earnings = input.nextLine();

        Developer d1 = new Developer(developerID, name, earnings);

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

        System.out.println("Input Price: ");
        String price = input.nextLine();

        Game g1 = new Game(title, price);

        em.getTransaction().begin();
        em.persist(g1);
        em.getTransaction().commit();
        em.close();

        System.out.println("<Game successfully added to library>");


    }

    public void editGame() {

        EntityManager em = emf.createEntityManager();
        viewGames();

        System.out.print("\nInput the ID of the game you'd like to edit: ");
        int ID = scanInt();

        Game g1 = em.find(Game.class, ID);

        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("0. Return to Main Menu");

        int choice = scanInt();

        if (choice == 1) {

            System.out.println("Enter new Name: ");
            String name = input.nextLine();

            g1.setName(name);


        } else if (choice == 2) {

            System.out.println("Enter new Price: ");
            String price = input.nextLine();

            g1.setPrice(price);

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
        System.out.println("2. Earnings");
        System.out.println("0. Return to Main Menu");

        int choice = scanInt();

        if (choice == 1) {

            System.out.println("Enter new Developer Name: ");
            String name = input.nextLine();

            d1.setDeveloperName(name);


        } else if (choice == 2) {

            System.out.println("Enter new Earnings: ");
            String earnings = input.nextLine();

            d1.setEarnings(earnings);

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

        System.out.println("Enter the ID of the Developer you'd like to connect: ");
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

}

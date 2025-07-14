package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.menuNavigator.MenuNavigator;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {
    protected final MenuNavigator navigator;
    protected final DbClient dbClient;
    protected final Scanner scanner = new Scanner(System.in);
    protected List<String> options;

    public Menu(DbClient dbClient, MenuNavigator navigator) {
        this.navigator = navigator;
        this.dbClient = dbClient;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }


    public void display() {
        if (this.options != null && !this.options.isEmpty()) {
            this.options.forEach(System.out::println);
        }
        this.listenToCommand();
    }

    protected int readInt() {
        return Integer.parseInt(this.scanner.nextLine());
    }

    protected String readString() {
        return this.scanner.nextLine();
    }

    public abstract void listenToCommand();
}

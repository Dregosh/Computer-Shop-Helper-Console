package controller;

import view.CustomerMenuState;
import view.MainMenuState;
import view.MenuState;
import view.ProductMenuState;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainController {
    private Deque<MenuState> states;

    public MainController() {
        this.states = new ArrayDeque<>();
    }

    public void start() {
        states.push(new MainMenuState(this));
        while (!states.isEmpty()) {
            states.getFirst().show();
        }
    }

    public void toProductMenu() {
        states.push(new ProductMenuState(new ProductController(states)));
    }

    public void toCustomerMenu() {
        states.push(new CustomerMenuState(new CustomerController(states)));
    }

    public void toOrderMenu() {

    }

    public void exitProgram() {
        states.pop();
    }
}

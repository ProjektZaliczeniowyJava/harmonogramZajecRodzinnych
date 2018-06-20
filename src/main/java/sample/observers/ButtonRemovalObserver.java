package sample.observers;

import sample.controller.Controller;

public class ButtonRemovalObserver implements Observer {
    private static final ButtonRemovalObserver instance = new ButtonRemovalObserver();

    private Controller controller;

    private ButtonRemovalObserver() {}

    public static ButtonRemovalObserver getInstance() {
        return instance;
    }

    public void addControllerToObserver(Controller controller) {
        this.controller = controller;
    }

    public void update(int idEvent) {
        this.controller.removeButtonFromGrid(idEvent);
        this.controller.removeButtonFromDataBase(idEvent);
    }
}

package sample;

public class Observer {
    private static final Observer instance = new Observer();

    private Controller controller;

    private Observer() {
    }

    public static Observer getInstance() {
        return instance;
    }

    public void addControllerToObserver(Controller controller) {
        this.controller = controller;
    }

    public void update(int idEvent) {
        this.controller.clickEventButton(idEvent);
    }
}
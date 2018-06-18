package sample;

public class ButtonObserver implements Observer{
    private static final ButtonObserver instance = new ButtonObserver();

    private Controller controller;

    private ButtonObserver() {
    }

    public static ButtonObserver getInstance() {
        return instance;
    }

    public void addControllerToObserver(Controller controller) {
        this.controller = controller;
    }

    public void update(int idEvent) {
        this.controller.clickEventButton(idEvent);
    }
}
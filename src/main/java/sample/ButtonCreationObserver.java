package sample;

public class ButtonCreationObserver implements Observer{
    private static final ButtonCreationObserver instance = new ButtonCreationObserver();

    private Controller controller;

    private ButtonCreationObserver() {
    }

    public static ButtonCreationObserver getInstance() {
        return instance;
    }

    public void addControllerToObserver(Controller controller) {
        this.controller = controller;
    }

    public void update(int idEvent) {
        this.controller.clickEventButton(idEvent);
    }
}
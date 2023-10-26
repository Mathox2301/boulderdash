package boulderDash.controller;

import boulderDash.model.*;
import boulderDash.view.*;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lejeu
 */
public class ControllerFX extends Application {

    private final Façade bd;
    private final ViewFX view;
    /**
     * Construit un objet de type ControllerFX
     */
    public ControllerFX() {
        this.bd = new Façade();
        this.view = new ViewFX(bd);
    }
    /**
     * Lance l'application et active les evenements necessaires pour pouvoir jouer.
     * @param stage un Stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        view.setDisplay();
        Scene scene = new Scene(view.getRoot());
        view.displayLevelFX(bd.getLvl(), false);
        stage.setScene(scene);
        stage.show();
        Command c = new CommandDeplacements(bd.getBd());
        bd.getCommandUndo().add(c);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            view.askDeplacementFX(event);
        }
        );
    }

}

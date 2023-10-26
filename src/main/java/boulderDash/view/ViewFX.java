package boulderDash.view;

import boulderDash.model.*;
import boulderDash.model.Position;
import boulderDash.oo.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author lejeu
 */
public class ViewFX implements Observer {

    private final Façade bd;
    private final HBox root;
    private final GridPane jeu;
    private final GridPane infos;
    private final VBox game;
    private final Label c;
    private final Label e;
    private final Label wrong;
    private final Label c1;
    private final Label c2;
    private final Label c3;
    private final Label l1;
    private final Label l2;
    private final Label l3;
    private int cpt1;
    private int cpt2;
    private String abandon;

    /**
     * Construit un objet de type ViewFX
     *
     * @param bd une Façade
     */
    public ViewFX(Façade bd) {
        this.bd = bd;
        Font font = Font.loadFont("file:src\\main\\font\\Boulder Dash 6128.ttf", 10);
        this.abandon = "";
        this.c = new Label("Commandes : ");
        this.wrong = new Label("\n");
        this.c.setUnderline(true);
        this.e = new Label("");
        this.c1 = new Label("Deplacements : Z/Q/S/D\nou les fleches directionelles");
        this.c2 = new Label("Undo/Redo :U/R");
        this.c3 = new Label("Abandonner : L");
        this.game = new VBox();
        this.root = new HBox();
        this.jeu = new GridPane();
        this.infos = new GridPane();
        infos.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.cpt1 = 0;
        this.cpt2 = bd.nbDiamondLvl();
        this.l1 = new Label("Diamants collectés : " + cpt1);
        this.l2 = new Label("Diamants restants : " + cpt2);
        this.l3 = new Label("Diamants necessaires\npour ouvrir le portail : " + (bd.nbDiamondLvl() / 4));
        this.c.setFont(font);
        this.c1.setFont(font);
        this.c2.setFont(font);
        this.c3.setFont(font);
        this.l1.setFont(font);
        this.l2.setFont(font);
        this.l3.setFont(font);
        this.wrong.setFont(font);
        this.l1.setTextFill(Color.WHITE);
        this.l2.setTextFill(Color.WHITE);
        this.l3.setTextFill(Color.WHITE);
        this.c.setTextFill(Color.WHITE);
        this.c1.setTextFill(Color.WHITE);
        this.c2.setTextFill(Color.WHITE);
        this.c3.setTextFill(Color.WHITE);
        this.wrong.setTextFill(Color.WHITE);
        bd.addObserver(this);
    }

    /**
     * affiche et met à jour l'affichage JavaFX
     *
     * @param old un Level
     * @param isDisplayOnce un boolean
     */
    public void displayLevelFX(Level old, boolean isDisplayOnce) {
        Image image;
        ImageView im;
        cpt1 = bd.nbDiamondLvl() - bd.nbRemainingDiamond();
        cpt2 = bd.nbDiamondLvl() - cpt1;
        for (int i = 0; i < bd.heightLvl(); i++) {
            for (int j = 0; j < bd.widthLvl(); j++) {
                Position pos = new Position(i, j);
                if (isDisplayOnce == false || !old.getCase(pos).getTypeCase().equals(bd.getTypeCase(pos))) {
                    wrong.setText("\n");
                    switch (bd.getTypeCase(pos)) {
                        case PLAYER:
                            image = new Image("file:src\\main\\images\\player1.png");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case ROCK:
                            image = new Image("file:src\\main\\images\\rock.png");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case DIAMOND:
                            image = new Image("file:src\\main\\images\\diamond.gif");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case MUD:
                            image = new Image("file:src\\main\\images\\ground.png");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case PORTAL:
                            image = new Image("file:src\\main\\images\\exit.gif");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case WALL:
                            image = new Image("file:src\\main\\images\\wall1.png");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;
                        case EMPTY:
                            image = new Image("file:src\\main\\images\\emptyTile.png");
                            im = new ImageView(image);
                            jeu.add(im, j, i);
                            im.setFitHeight(16);
                            im.setFitWidth(16);
                            break;

                    }

                    l1.setText("Diamants collectés  : " + cpt1);
                    l2.setText("Diamants restants : " + cpt2);
                    if (cpt1 >= bd.nbDiamondLvl() / 4) {
                        l3.setText("Portail ouvert !");
                    }
                    if (bd.isGameFinished() && (bd.nbLvl() + 1) <= 2) {
                        if (bd.isGameWin()) {
                            l2.setText("Vous avez gagné !      ");
                            l1.setText("");
                            l3.setText("");
                            bd.newLvl(bd.nbLvl() + 1);
                        } else {
                            l2.setText("Vous etes mort         ");
                            l1.setText("");
                            l3.setText(abandon);
                        }
                    } else if (bd.isGameFinished() && (bd.nbLvl() + 1) > 2) {
                        l2.setText("La partie est terminée !    ");
                        l1.setText("");
                        l3.setText("");

                    }

                }
            }
        }

    }

    /**
     * assigne une methode à la touche pressée
     *
     * @param event un événement de clavier
     */
    public void askDeplacementFX(KeyEvent event) {
        Level old;
        boolean isMooved = true;
        old = new Level(bd.nbLvl());
        for (int i = 0; i < bd.heightLvl(); i++) {
            for (int j = 0; j < bd.widthLvl(); j++) {
                Position pos = new Position(i, j);
                old.setCase(pos, bd.getTypeCase(pos));
            }

        }

        if (!bd.isGameFinished()) {

            switch (event.getCode()) {
                case DOWN,S:
                    isMooved = bd.isPossibleMove(bd.posPlayer(), Direction.SOUTH);
                    bd.movePlayer(Direction.SOUTH);
                    break;
                case LEFT,Q:
                    isMooved = bd.isPossibleMove(bd.posPlayer(), Direction.WEST);
                    bd.movePlayer(Direction.WEST);
                    break;
                case UP,Z:
                    isMooved = bd.isPossibleMove(bd.posPlayer(), Direction.NORTH);
                    bd.movePlayer(Direction.NORTH);
                    break;
                case RIGHT,D:
                    isMooved = bd.isPossibleMove(bd.posPlayer(), Direction.EAST);
                    bd.movePlayer(Direction.EAST);
                    break;
                case U:
                    bd.undo();
                    break;
                case R:
                    bd.redo();
                    break;
                case L: {
                    bd.giveUp();
                    abandon = "abandon";
                    break;
                }
            }
            if (isMooved==true) {
                bd.fallRocks();
            boolean isFinish;
            do {
                isFinish = bd.slipRocks();
            } while (isFinish == false);
            displayLevelFX(old, true);
            wrong.setText("\n");
        }else{
            wrong.setText("\nCe mouvement\nn'est pas possible");
            }

        }
    }

    /**
     * Getter pour l'attribut root
     *
     * @return une HBox
     */
    public HBox getRoot() {
        return root;
    }

    /**
     * Setter pour les éléments de la scene
     */
    public void setDisplay() {
        infos.add(l1, 0, 0);
        infos.add(l2, 0, 1);
        infos.add(l3, 0, 2);
        infos.add(e, 0, 4);
        infos.add(c, 0, 5);
        infos.add(c1, 0, 6);
        infos.add(c2, 0, 7);
        infos.add(c3, 0, 8);
        infos.add(wrong, 0, 9);
        game.getChildren().add(jeu);
        root.getChildren().addAll(infos, game);

    }

    /**
     * Met à jour la vue avec les attributs du modele
     */
    @Override
    public void update() {
        cpt1 = 0;
        cpt2 = bd.nbDiamondLvl();
        l1.setText("Diamants collectés :  " + cpt1);
        l2.setText("Diamants restants : " + cpt2);
        l3.setText("Diamants necessaires\npour ouvrir le portail : " + (bd.nbDiamondLvl() / 4));
        displayLevelFX(bd.getLvl(), false);
    }

}

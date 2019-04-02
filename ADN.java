/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iajoconde_final;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author bilal
 */
public class ADN {

    private ArrayList<ConvexPolygon> mesPolygones; //Liste des ensembles de polygones 
    private double fitness; //Fitness d'un ADN
    public static short nombrePolygone = 50; //Permet de specifier le nombre de polygones de notre ADN
    public static short nombreSommet = 6; //Permet de specifier le nombre de sommet de notre polygone
    private Random monAleatoire; //Lors des operation de mutations notamment on utilise des choses aleatoires afin de muter nos individus 
    private static int tauxCouleur = 30; //Modification d'un taux d'une certaine couleurs 
    private static int translateVY = 20; //Delta de modification d'un point lors d'une strategie souple
    private static int distAddSommet = 10; //Delta de la distance du nouveau sommet ajouter par rapport à un existant lors d'une strategie souple
    private static double tauxOpacite = 0.2; //Delta de la modifacation de l'opacité lors de la strategie souple
    public static boolean isGeneConvex; //Permet de definir si on ne considere que des polygones convexe ou non 
    public static Color[][] target; //Cible de notre ADN
    //Création d'un ADN avec un etat initial de 50 polygone transparent et vide
    public ADN() {
        monAleatoire = new Random();
        mesPolygones = new ArrayList<ConvexPolygon>();
        for (int i = 0; i < ADN.nombrePolygone; i++) {
            mesPolygones.add(new ConvexPolygon(ADN.nombreSommet));
        }
        this.calculFitness();

    }
    //Création d'un ADN avec un gene existant , et sa fitness deja calculée
    public ADN(ArrayList<ConvexPolygon> gene, double fitness) {
        monAleatoire = new Random();
        this.mesPolygones = gene;
        this.fitness = fitness;

    }
    //Permet de modifier une composante(Rouge(RGB)) de nos genes
    public double mutateRedComponent(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));
        switch (strategie) {

            case 0: {
                Color maCouleur = (Color) change.getFill();
                int red = (int) (maCouleur.getRed() * 255);
                int changementRed = monAleatoire.nextInt(ADN.tauxCouleur);
                if (monAleatoire.nextBoolean()) {
                    if (red + changementRed <= 255) {
                        red = red + changementRed;
                    }
                } else {
                    if (red - changementRed >= 0) {
                        red = red - changementRed;

                    } else {
                        red = 0;
                    }

                }
                change.setFill(Color.rgb(red, (int) (maCouleur.getGreen() * 255), (int) (maCouleur.getBlue() * 255), maCouleur.getOpacity()));
                break;
            }
            case 1: {
                Color maCouleur = (Color) change.getFill();
                int red = monAleatoire.nextInt(256);
                change.setFill(Color.rgb(red, (int) (maCouleur.getGreen() * 255), (int) (maCouleur.getBlue() * 255), maCouleur.getOpacity()));
                break;
            }
            case 2: {
                return this.hauteMutation();
            }

            default:
                //Generer Exceptions
                break;

        }
        return this.calculFitness();

    }
    //Permet de modifier une composante(Verte(RGB)) de nos genes
    public double mutateGreenComponent(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));
        switch (strategie) {

            case 0: {
                Color maCouleur = (Color) change.getFill();
                int green = (int) (maCouleur.getGreen() * 255);
                int changementGreen = monAleatoire.nextInt(ADN.tauxCouleur);
                if (monAleatoire.nextBoolean()) {
                    if (green + changementGreen <= 255) {
                        green = green + changementGreen;
                    }

                } else {
                    if (green - changementGreen >= 0) {
                        green = green - changementGreen;

                    } else {
                        green = 0;
                    }

                }
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), green, (int) (maCouleur.getBlue() * 255), maCouleur.getOpacity()));
                break;
            }
            case 1: {
                Color maCouleur = (Color) change.getFill();
                int green = monAleatoire.nextInt(256);
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), green, (int) (maCouleur.getBlue() * 255), maCouleur.getOpacity()));
                break;
            }
            case 2: {
                return this.hauteMutation();
            }
            default:
                //Generer Exceptions
                break;

        }
        return this.calculFitness();

    }
    //Permet de modifier une composante(Bleu(RGB)) de notre ADN
    public double mutateBlueComponent(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));
        switch (strategie) {

            case 0: {
                Color maCouleur = (Color) change.getFill();
                int blue = (int) (maCouleur.getBlue() * 255);
                int changementBlue = monAleatoire.nextInt(ADN.tauxCouleur);
                if (monAleatoire.nextBoolean()) {
                    if (blue + changementBlue <= 255) {
                        blue = blue + changementBlue;
                    }

                } else {
                    if (blue - changementBlue >= 0) {
                        blue = blue - changementBlue;

                    } else {
                        blue = 0;
                    }

                }
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), (int) (maCouleur.getGreen() * 255), blue, maCouleur.getOpacity()));
                break;
            }
            case 1: {
                Color maCouleur = (Color) change.getFill();
                int blue = monAleatoire.nextInt(256);
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), (int) (maCouleur.getGreen() * 255), blue, maCouleur.getOpacity()));
                break;
            }
            case 2: {
                return this.hauteMutation();
            }
            default:
                //Generer Exceptions
                break;

        }
        return this.calculFitness();

    }
    //Permet de translater un des sommet(X) de notre gene 
    public double mutateXSommet(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));

        switch (strategie) {

            case 0: {
                ObservableList<Double> mesPoitns = change.getPoints();
                int translate = monAleatoire.nextInt(ADN.translateVY);
                Double[] points = this.translate(mesPoitns, translate, 0);
                if (points == null) {
                    return this.mutateYSommet(strategie);

                }
                change.getPoints().setAll(points);
                break;
            }
            case 1: {
                ObservableList<Double> mesPoitns = change.getPoints();
                int translate = monAleatoire.nextInt(Image.resX);
                Double[] points = this.translate(mesPoitns, translate, 0);
                if (points == null) {
                    return this.mutateYSommet(strategie);

                }
                change.getPoints().setAll(points);
                break;
            }
            case 2: {
                return this.hauteMutation();
            }

            default:
                break;

        }

        return this.calculFitness();

    }
    //Permet de translater un des sommet(Y) de notre gene 
    public double mutateYSommet(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));

        switch (strategie) {

            case 0: {
                ObservableList<Double> mesPoitns = change.getPoints();
                int translate = monAleatoire.nextInt(ADN.translateVY);
                Double[] points = this.translate(mesPoitns, 0, translate);
                if (points == null) {
                    return this.mutateYSommet(strategie);

                }
                change.getPoints().setAll(points);
                break;
            }
            case 1: {
                ObservableList<Double> mesPoitns = change.getPoints();
                int translate = monAleatoire.nextInt(Image.resY);
                Double[] points = this.translate(mesPoitns, 0, translate);
                if (points == null) {
                    return this.mutateYSommet(strategie);

                }
                change.getPoints().setAll(points);
                break;
            }
            case 2: {
                return this.hauteMutation();
            }

            default:
                break;

        }

        return this.calculFitness();

    }
    //Permet de modifier l'opacité de notre gene
    public double mutateOpacity(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));

        switch (strategie) {

            case 0: {
                Color maCouleur = (Color) change.getFill();
                double opacity = maCouleur.getOpacity();
                int changementOpacity = monAleatoire.nextInt((int) ((double) ADN.tauxOpacite * 10) + 1);
                double changement = (double) changementOpacity / 10;
                if (monAleatoire.nextBoolean()) {
                    if (opacity + changement <= 1) {
                        opacity = opacity + changement;
                    }

                } else {
                    if (opacity - changement >= 0) {
                        opacity = opacity - changement;

                    } else {
                        opacity = 0;
                    }

                }
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), (int) (maCouleur.getGreen() * 255), (int) (maCouleur.getBlue() * 255), opacity));
                break;
            }
            case 1: {
                Color maCouleur = (Color) change.getFill();
                double opacity = monAleatoire.nextDouble();
                change.setFill(Color.rgb((int) (maCouleur.getRed() * 255), (int) (maCouleur.getGreen() * 255), (int) (maCouleur.getBlue() * 255), opacity));
                break;
            }
            case 2: {
                return this.hauteMutation();
            }

            default:
                break;

        }

        return this.calculFitness();

    }
    //Permet de modifier le nombre de sommet d'un de nos gene
    public double mutateNombreSommet(short strategie) {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));

        switch (strategie) {

            case 0: {
                ObservableList<Double> mesPoitns = change.getPoints();
                Double[] ptsC = new Double[mesPoitns.size() + 2];
                for (int z = 0; z < mesPoitns.size(); z++) {
                    ptsC[z] = mesPoitns.get(z);
                }
                int indexPoint = monAleatoire.nextInt(mesPoitns.size());
                double xBase, yBase;
                if (indexPoint % 2 == 0) {
                    xBase = ptsC[indexPoint];
                    yBase = ptsC[indexPoint + 1];
                } else {
                    yBase = ptsC[indexPoint];
                    xBase = ptsC[indexPoint - 1];

                }
                double newX = 0, newY = 0;
                int deplaceX = monAleatoire.nextInt(ADN.distAddSommet);
                int deplaceY = monAleatoire.nextInt(ADN.distAddSommet);

                if (monAleatoire.nextBoolean() && xBase + deplaceX <= Image.resX) {
                    newX = xBase + deplaceX;
                    if (monAleatoire.nextBoolean() && yBase + deplaceY <= Image.resY) {
                        newY = yBase + deplaceY;

                    } else if (yBase - deplaceY >= 0) {
                        newY = yBase - deplaceY;
                    }
                } else if (xBase - deplaceX >= 0) {
                    newX = xBase - deplaceX;
                    if (monAleatoire.nextBoolean() && yBase + deplaceY <= Image.resY) {
                        newY = yBase + deplaceY;

                    } else if (yBase - deplaceY >= 0) {
                        newY = yBase - deplaceY;
                    }

                }

                ptsC[mesPoitns.size()] = newX;
                ptsC[mesPoitns.size() + 1] = newY;

                if (newX < xBase && newY < yBase) {
                    if (ptsC[indexPoint].equals(yBase) && !(ptsC[indexPoint].equals(xBase))) {
                        ptsC[indexPoint - 1] = newX;
                        ptsC[indexPoint] = newY;
                    } else {
                        ptsC[indexPoint] = newX;
                        ptsC[indexPoint + 1] = newY;

                    }
                }

                if (ADN.isGeneConvex) {
                    Double[] pointAdd = this.parcoursWagrham(ptsC);
                    change.getPoints().setAll(pointAdd);

                } else {
                    change.getPoints().setAll(ptsC);
                }

                break;
            }
            case 1: {
                ObservableList<Double> mesPoitns = change.getPoints();
                Double[] ptsC = new Double[mesPoitns.size() + 2];
                for (int z = 0; z < mesPoitns.size(); z++) {
                    ptsC[z] = mesPoitns.get(z);
                }

                double newX = (double) monAleatoire.nextInt(Image.resX);
                double newY = (double) monAleatoire.nextInt(Image.resY);
                if (change.contains(newX, newY)) {
                    double distMax = this.distPoints(0, 0, Image.resX, Image.resY);
                    int index = 0;
                    for (int i = 0; i < mesPoitns.size(); i += 2) {

                        double distAct = this.distPoints(mesPoitns.get(i), mesPoitns.get(i + 1), newX, newY);
                        if (distAct < distMax) {
                            distMax = distAct;
                            index = i;
                        }
                    }
                    ptsC[index] = newX;
                    ptsC[index + 1] = newY;
                    ptsC[mesPoitns.size()] = newX;
                    ptsC[mesPoitns.size() + 1] = newY;

                } else {
                    ptsC[mesPoitns.size()] = newX;
                    ptsC[mesPoitns.size() + 1] = newY;

                }

                if (ADN.isGeneConvex) {
                    Double[] pointAdd = this.parcoursWagrham(ptsC);
                    change.getPoints().setAll(pointAdd);

                } else {
                    change.getPoints().setAll(ptsC);
                }

                break;
            }
            case 2: {
                return this.hauteMutation();
            }

            default:
                break;

        }
        return this.calculFitness();

    }
    //Modification simultanée de plusieurs composantes de nos genes
    public double hauteMutation() {
        ConvexPolygon change = this.mesPolygones.get(monAleatoire.nextInt(this.mesPolygones.size()));
        Color maCouleur = (Color) change.getFill();
        double opacity = monAleatoire.nextDouble();
        ObservableList<Double> mesPoitns = change.getPoints();
        int translateY = monAleatoire.nextInt(Image.resY);
        int translateX = monAleatoire.nextInt(Image.resX);;
        Double[] points = this.translate(mesPoitns, translateX, translateY);
        if (points == null) {
            return this.hauteMutation();
        }
        change.getPoints().setAll(points);
        change.setFill(Color.rgb(monAleatoire.nextInt(256), monAleatoire.nextInt(256), monAleatoire.nextInt(256), monAleatoire.nextDouble()));
        return this.calculFitness();
    }

    //Permet de cloner un ADN avec une deep copy 
    @Override
    public Object clone() {
        ArrayList<ConvexPolygon> nouv = new ArrayList<ConvexPolygon>();
        for (ConvexPolygon a : this.mesPolygones) {
            ConvexPolygon b = new ConvexPolygon();
            b.setFill(a.getFill());
            b.getPoints().setAll(a.getPoints());
            nouv.add(b);
        }
        return new ADN(nouv, this.getFitness());
    }

    //Calcul de la fitness d'un ADN
    private double calculFitness() {
        Group picture = new Group();
        for (ConvexPolygon e : this.mesPolygones) {
            picture.getChildren().add(e);
        }
        WritableImage wimg = new WritableImage(Image.resX, Image.resY);
        picture.snapshot(null, wimg);
        PixelReader pr = wimg.getPixelReader();
        double res = 0;
        for (int i = 0; i < Image.resX; i++) {
            for (int j = 0; j < Image.resY; j++) {
                Color c = pr.getColor(i, j);
                res += Math.pow(c.getBlue() - target[i][j].getBlue(), 2)
                        + Math.pow(c.getRed() - target[i][j].getRed(), 2)
                        + Math.pow(c.getGreen() - target[i][j].getGreen(), 2);
            }
        }
        this.fitness = Math.sqrt(res);
        return this.fitness;
    }
    //renvoie la fitness
    public double getFitness() {
        return this.fitness;

    }
    //Permet de deplacer un point au hasard dans un ensemble de points 
    private Double[] translate(ObservableList<Double> points, int moveX, int moveY) {

        int nbPoint = points.size() / 2;
        Double[] ptsC = new Double[nbPoint * 2];
        int indexPoint = monAleatoire.nextInt(nbPoint * 2);
        for (int z = 0; z < nbPoint * 2; z++) {
            ptsC[z] = points.get(z);
        }
        if (indexPoint % 2 == 0) {

            if (monAleatoire.nextBoolean() && ptsC[indexPoint] + moveX <= Image.resX) {
                ptsC[indexPoint] = ptsC[indexPoint] + moveX;

                if (ptsC[indexPoint + 1] + moveY <= Image.resY && monAleatoire.nextBoolean()) {
                    ptsC[indexPoint + 1] = ptsC[indexPoint + 1] + moveY;

                } else if (ptsC[indexPoint + 1] - moveY >= 0) {
                    ptsC[indexPoint + 1] = ptsC[indexPoint + 1] - moveY;

                }

            } else if (ptsC[indexPoint] - moveX >= 0) {
                ptsC[indexPoint] = ptsC[indexPoint] - moveX;
                if (ptsC[indexPoint + 1] + moveY < Image.resY && monAleatoire.nextBoolean()) {
                    ptsC[indexPoint + 1] = ptsC[indexPoint + 1] + moveY;

                } else if (ptsC[indexPoint + 1] - moveY >= 0) {
                    ptsC[indexPoint + 1] = ptsC[indexPoint + 1] - moveY;

                }

            }

        } else {

            if (monAleatoire.nextBoolean() && ptsC[indexPoint - 1] + moveX <= Image.resX) {
                ptsC[indexPoint - 1] = ptsC[indexPoint - 1] + moveX;

                if (ptsC[indexPoint] + moveY <= Image.resY && monAleatoire.nextBoolean()) {
                    ptsC[indexPoint] = ptsC[indexPoint] + moveY;

                } else if (ptsC[indexPoint] - moveY >= 0) {
                    ptsC[indexPoint] = ptsC[indexPoint] - moveY;

                }

            } else if (ptsC[indexPoint - 1] - moveX >= 0) {
                ptsC[indexPoint - 1] = ptsC[indexPoint - 1] - moveX;
                if (ptsC[indexPoint] + moveY <= Image.resY && monAleatoire.nextBoolean()) {
                    ptsC[indexPoint] = ptsC[indexPoint] + moveY;

                } else if (ptsC[indexPoint] - moveY >= 0) {
                    ptsC[indexPoint] = ptsC[indexPoint] - moveY;

                }

            }
        }

        if (nbPoint == 3) {
            return ptsC;

        } else {
            try {
                return this.parcoursWagrham(ptsC);
            } catch (IllegalArgumentException e) {
                return null;

            }
        }

    }
    //Permet de composer un polygone convex à partir d'un ensemble de points 
    private Double[] parcoursWagrham(Double[] points) {
        int[] ptsX;
        int[] ptsY;
        ptsX = new int[points.length / 2];
        ptsY = new int[points.length / 2];
        int cof = 0;
        for (int y = 0; y < points.length; y += 2) {
            ptsX[cof] = points[y].intValue();
            ptsY[cof] = points[y + 1].intValue();
            cof++;
        }
        List<java.awt.Point> convexHull = GrahamScan.getConvexHull(ptsX, ptsY);
        Double[] ptsC = new Double[convexHull.size() * 2];
        int z = 0;
        for (java.awt.Point p : convexHull) {
            ptsC[z] = p.getX();
            ptsC[z + 1] = p.getY();
            z += 2;
        }
        return ptsC;

    }
    //Renvoie notre ADN
    public ArrayList<ConvexPolygon> getGene() {
        return this.mesPolygones;

    }
    //Renvoie la distance entre deux points
    private double distPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iajoconde_final;

import java.util.ArrayList;
import java.util.Collections;
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
public class Image {

   
    private Random monAleatoire; //Lors des operation de mutations notamment on utilise des choses aleatoires afin de muter nos individus 
    private ADN monAdn; //Liste des ensembles de polygones
    private static double tauxOpacite = 0.3; //Facteur de changement d'opacite d'un polygone
    public static int resX, resY; //Résolution de notre image

    //Construit une image avec un ADN aléatoire 
    public Image() {
        this.monAdn = new ADN() ; 
        monAleatoire = new Random() ; 
       
    }


  
    //Permet de muter l'ADN de notre image avec une certaine stratégie (forte mutation , mutation modérérée , mutation souple)
    public double mutation(short strategie) {
        
        int changeComponent = monAleatoire.nextInt(7) ;
        ADN pere = (ADN) this.monAdn.clone() ; 
        double fit = 0 ; 
        switch(changeComponent){
            
            case 0 :
                 fit = this.monAdn.mutateBlueComponent(strategie) ; 
                break ;
            case 1 :
                fit = this.monAdn.mutateGreenComponent(strategie) ; 
                break ; 
            case 2 : 
                fit = this.monAdn.mutateRedComponent(strategie);
                break ;
            case 3 : 
                fit = this.monAdn.mutateOpacity(strategie) ; 
                break ; 
            case 4 :
                fit = this.monAdn.mutateXSommet(strategie) ; 
                break ;
            case 5 : 
                fit = this.monAdn.mutateYSommet(strategie) ; 
                break ;
            case 6 :
                 fit = this.monAdn.mutateNombreSommet(strategie) ;  
                 break ; 
                
            default : 
                break ;
        }
        if(!(this.monAdn.getFitness() < pere.getFitness())){
            this.monAdn = pere ;
            
        }
        return this.monAdn.getFitness() ; 
        

    

}
    
    //Retourne l'ADN
    public ArrayList<ConvexPolygon> getImage(){
        
        return this.monAdn.getGene() ; 
    
    
    }
    
   
}

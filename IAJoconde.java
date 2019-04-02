/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iajoconde_final;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author bilal
 */
public class IAJoconde extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       String targetImage = "monaLisa-100.jpg";
		Color[][] target=null;
		int maxX=0;
                int maxY=0;
		try{
		BufferedImage bi = ImageIO.read(new File(targetImage));
		maxX = bi.getWidth();
		maxY = bi.getHeight();
                target = new Color[maxX][maxY];
        	for (int i=0;i<maxX;i++){
        		for (int j=0;j<maxY;j++){
        			int argb = bi.getRGB(i, j);
        			int b = (argb)&0xFF;
        			int g = (argb>>8)&0xFF;
        			int r = (argb>>16)&0xFF;
        			int a = (argb>>24)&0xFF;
        			target[i][j] = Color.rgb(r,g,b);
                        }
        	}
        }
        catch(IOException e){
        	System.err.println(e); 
        	System.exit(9);
        }
		System.out.println("Read target image " + targetImage + " " + maxX + "x" + maxY);
                ConvexPolygon.max_X = maxX ;
                ConvexPolygon.max_Y = maxY ; 
                Image.resX = maxX ; 
                Image.resY = maxY ;
                ADN.target = target ; 
                ADN.isGeneConvex = true ; 
                Image monImage = new Image() ; 
                int nbMutation = 0 ; 
                int nbScreen = 1000 ; 
                double mut = 400 ; 
                while(1==1){
                    if(mut < 15){
                        mut = monImage.mutation((short)0) ; 
                    
                    }else{
                      mut = monImage.mutation((short)1) ;
                    }
                    System.out.println("Fitness :" + mut + " Nb Mutation :" + nbMutation);
                    nbMutation++ ; 
                    if(nbMutation % nbScreen == 0){
                         Group image = new Group() ; 
                        for (ConvexPolygon p : monImage.getImage())
                        image.getChildren().add(p);
                        WritableImage wimg = new WritableImage(maxX,maxY);
                        image.snapshot(null,wimg);
                        PixelReader pr = wimg.getPixelReader();
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(wimg, null); 
                        try {
                                ImageIO.write(renderedImage, "png", new File("Joconde" +nbMutation+".png"));
                                System.out.println("wrote image in " + "test.png");
                        } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                    }
                
                
                }
                
                
                
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

package com.exemple.enjoyfood;

import android.util.TypedValue;

import com.exemple.enjoyfood.model.Produit;

import java.util.ArrayList;

public class Nutriscore {

    public static String calcul(Produit produit){
        ////// calculer les points ///////////////////////////////////////////////////////////////
        ////// Etape 1  ///////////////////////////////////////////////////////////////
        //                          calcules les point en communs
        //***** parraport les boissons ******


        int point, point_N = 0, point_P = 0, fruits_legumesScore = 0, fibreScore = 0;
        if(produit.getCategorie().equals(Config.CATEGORIES[0])) {
            // calcul de l'energie
            if (produit.getEnergie() <= 30) point_N += 1;
            else if (produit.getEnergie() <= 60 && produit.getEnergie() > 30) point_N += 2;
            else if (produit.getEnergie() <= 90 && produit.getEnergie() > 60) point_N += 3;
            else if (produit.getEnergie() <= 120 && produit.getEnergie() > 90) point_N += 4;
            else if (produit.getEnergie() <= 150 && produit.getEnergie() > 120) point_N += 5;
            else if (produit.getEnergie() <= 180 && produit.getEnergie() > 150) point_N += 6;
            else if (produit.getEnergie() <= 210 && produit.getEnergie() > 180) point_N += 7;
            else if (produit.getEnergie() <= 240 && produit.getEnergie() > 210) point_N += 8;
            else if (produit.getEnergie() <= 270 && produit.getEnergie() > 240) point_N += 9;
            else if (produit.getEnergie() > 270) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() > 4.5 && produit.getSucre() <= 9) point_N += 1;
            else if (produit.getSucre() > 9 && produit.getSucre() <= 13.5) point_N += 2;
            else if (produit.getSucre() > 13.5 && produit.getSucre() <= 18) point_N += 3;
            else if (produit.getSucre() > 18 && produit.getSucre() <= 22.5) point_N += 4;
            else if (produit.getSucre() > 22.5 && produit.getSucre() <= 27) point_N += 5;
            else if (produit.getSucre() > 27 && produit.getSucre() <= 31) point_N += 6;
            else if (produit.getSucre() > 31 && produit.getSucre() <= 36) point_N += 7;
            else if (produit.getSucre() > 36 && produit.getSucre() <= 40) point_N += 8;
            else if (produit.getSucre() > 40 && produit.getSucre() <= 45) point_N += 9;
            else if (produit.getSucre() > 45) point_N += 10;
            // Graisses saturées
            if(produit.getGraisse() > 1 && produit.getGraisse() <=2) point_N += 1;
            else if (produit.getGraisse() > 2 && produit.getGraisse() <=3) point_N += 2;
            else if (produit.getGraisse() > 3 && produit.getGraisse() <=4) point_N += 3;
            else if (produit.getGraisse() > 4 && produit.getGraisse() <=5) point_N += 4;
            else if (produit.getGraisse() > 5 && produit.getGraisse() <=6) point_N += 5;
            else if (produit.getGraisse() > 6 && produit.getGraisse() <=7) point_N += 6;
            else if (produit.getGraisse() > 7 && produit.getGraisse() <=8) point_N += 7;
            else if (produit.getGraisse() > 8 && produit.getGraisse() <=9) point_N += 8;
            else if (produit.getGraisse() > 9 && produit.getGraisse() <=10) point_N += 9;
            else if (produit.getGraisse() > 10) point_N += 10;
            // Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium()*1000 > 180 && produit.getSodium()*1000 <= 270) point_N += 2;
            else if (produit.getSodium()*1000 > 270 && produit.getSodium()*1000 <= 360) point_N += 3;
            else if (produit.getSodium()*1000 > 360 && produit.getSodium()*1000 <= 450) point_N += 4;
            else if (produit.getSodium()*1000 > 450 && produit.getSodium()*1000 <= 540) point_N += 5;
            else if (produit.getSodium()*1000 > 540 && produit.getSodium()*1000 <= 360) point_N += 6;
            else if (produit.getSodium()*1000 > 630 && produit.getSodium()*1000 <= 720) point_N += 7;
            else if (produit.getSodium()*1000 > 720 && produit.getSodium()*1000 <= 810) point_N += 8;
            else if (produit.getSodium()*1000 > 810 && produit.getSodium()*1000 <= 900) point_N += 9;
            else if (produit.getSodium()*1000 > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40 && produit.getFruits_legumes() <= 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 60 && produit.getFruits_legumes() <= 80){
                point_N += 4;
                fruits_legumesScore += 4;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 10;
                fruits_legumesScore += 10;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéine
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;
        }
        //****Parraport les matieres grasses ********************
        else if(produit.getCategorie().equals(Config.CATEGORIES[2])){
            //L'énergie
            if (produit.getEnergie() > 335 && produit.getEnergie() <= 670) point_N += 1;
            else if (produit.getEnergie() > 670 && produit.getEnergie() <= 1005) point_N += 2;
            else if (produit.getEnergie() > 1005 && produit.getEnergie() <= 1340) point_N += 3;
            else if (produit.getEnergie() > 1340 && produit.getEnergie() <= 1675) point_N += 4;
            else if (produit.getEnergie() > 1675 && produit.getEnergie() <= 2010) point_N += 5;
            else if (produit.getEnergie() > 2010 && produit.getEnergie() <= 2345) point_N += 6;
            else if (produit.getEnergie() > 2345 && produit.getEnergie() <= 2680) point_N += 7;
            else if (produit.getEnergie() > 2680 && produit.getEnergie() <= 3015) point_N += 8;
            else if (produit.getEnergie() > 3015 && produit.getEnergie() <= 3350) point_N += 9;
            else if (produit.getEnergie() > 3350) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() <= 1.5) point_N += 1;
            else if (produit.getSucre() <= 3 && produit.getSucre() > 1.5) point_N += 2;
            else if (produit.getSucre() <= 4.5 && produit.getSucre() > 3) point_N += 3;
            else if (produit.getSucre() <= 6 && produit.getSucre() > 4.5) point_N += 4;
            else if (produit.getSucre() <= 7.5 && produit.getSucre() > 6) point_N += 5;
            else if (produit.getSucre() <= 9 && produit.getSucre() > 7.5) point_N += 6;
            else if (produit.getSucre() <= 10.5 && produit.getSucre() > 9) point_N += 7;
            else if (produit.getSucre() <= 12 && produit.getSucre() > 10.5) point_N += 8;
            else if (produit.getSucre() <= 13.5 && produit.getSucre() > 12) point_N += 9;
            else if (produit.getSucre() > 13.5) point_N += 10;
            // calcul du gresse saturé
            if(produit.getGraisse() < 16) point_N += 1;
            else if (produit.getGraisse() < 22 && produit.getGraisse() >= 16) point_N += 2;
            else if (produit.getGraisse() < 28 && produit.getGraisse() >= 22) point_N += 3;
            else if (produit.getGraisse() < 34 && produit.getGraisse() >= 28) point_N += 4;
            else if (produit.getGraisse() < 40 && produit.getGraisse() >= 34) point_N += 5;
            else if (produit.getGraisse() < 46 && produit.getGraisse() >= 40) point_N += 6;
            else if (produit.getGraisse() < 52 && produit.getGraisse() >= 46) point_N += 7;
            else if (produit.getGraisse() < 58 && produit.getGraisse() >= 52) point_N += 8;
            else if (produit.getGraisse() < 64 && produit.getGraisse() >= 58) point_N += 9;
            else if (produit.getGraisse() >= 64) point_N += 10;
            // Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium()*1000 > 180 && produit.getSodium()*1000 <= 270) point_N += 2;
            else if (produit.getSodium()*1000 > 270 && produit.getSodium()*1000 <= 360) point_N += 3;
            else if (produit.getSodium()*1000 > 360 && produit.getSodium()*1000 <= 450) point_N += 4;
            else if (produit.getSodium()*1000 > 450 && produit.getSodium()*1000 <= 540) point_N += 5;
            else if (produit.getSodium()*1000 > 540 && produit.getSodium()*1000 <= 360) point_N += 6;
            else if (produit.getSodium()*1000 > 630 && produit.getSodium()*1000 <= 720) point_N += 7;
            else if (produit.getSodium()*1000 > 720 && produit.getSodium()*1000 <= 810) point_N += 8;
            else if (produit.getSodium()*1000 > 810 && produit.getSodium()*1000 <= 900) point_N += 9;
            else if (produit.getSodium()*1000 > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40){
                point_N += 1;
                fruits_legumesScore += 1;
            }
            else if (produit.getFruits_legumes() > 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 5;
                fruits_legumesScore += 5;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéines
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;
        }
        //****Parraport les autres ********************
        else{
            // calcul de l'Energie
            if (produit.getEnergie() > 335 && produit.getEnergie() <= 670) point_N += 1;
            else if (produit.getEnergie() > 670 && produit.getEnergie() <= 1005) point_N += 2;
            else if (produit.getEnergie() > 1005 && produit.getEnergie() <= 1340) point_N += 3;
            else if (produit.getEnergie() > 1340 && produit.getEnergie() <= 1675) point_N += 4;
            else if (produit.getEnergie() > 1675 && produit.getEnergie() <= 2010) point_N += 5;
            else if (produit.getEnergie() > 2010 && produit.getEnergie() <= 2345) point_N += 6;
            else if (produit.getEnergie() > 2345 && produit.getEnergie() <= 2680) point_N += 7;
            else if (produit.getEnergie() > 2680 && produit.getEnergie() <= 3015) point_N += 8;
            else if (produit.getEnergie() > 3015 && produit.getEnergie() <= 3350) point_N += 9;
            else if (produit.getEnergie() > 3350) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() <= 1.5) point_N += 1;
            else if (produit.getSucre() <= 3 && produit.getSucre() > 1.5) point_N += 2;
            else if (produit.getSucre() <= 4.5 && produit.getSucre() > 3) point_N += 3;
            else if (produit.getSucre() <= 6 && produit.getSucre() > 4.5) point_N += 4;
            else if (produit.getSucre() <= 7.5 && produit.getSucre() > 6) point_N += 5;
            else if (produit.getSucre() <= 9 && produit.getSucre() > 7.5) point_N += 6;
            else if (produit.getSucre() <= 10.5 && produit.getSucre() > 9) point_N += 7;
            else if (produit.getSucre() <= 12 && produit.getSucre() > 10.5) point_N += 8;
            else if (produit.getSucre() <= 13.5 && produit.getSucre() > 12) point_N += 9;
            else if (produit.getSucre() > 13.5) point_N += 10;
            // calcul du gresse saturé
            if(produit.getGraisse() > 1 && produit.getGraisse() <=2) point_N += 1;
            else if (produit.getGraisse() > 2 && produit.getGraisse() <=3) point_N += 2;
            else if (produit.getGraisse() > 3 && produit.getGraisse() <=4) point_N += 3;
            else if (produit.getGraisse() > 4 && produit.getGraisse() <=5) point_N += 4;
            else if (produit.getGraisse() > 5 && produit.getGraisse() <=6) point_N += 5;
            else if (produit.getGraisse() > 6 && produit.getGraisse() <=7) point_N += 6;
            else if (produit.getGraisse() > 7 && produit.getGraisse() <=8) point_N += 7;
            else if (produit.getGraisse() > 8 && produit.getGraisse() <=9) point_N += 8;
            else if (produit.getGraisse() > 9 && produit.getGraisse() <=10) point_N += 9;
            else if (produit.getGraisse() > 10) point_N += 10;
            //Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium() > 180 && produit.getSodium() <= 270) point_N += 2;
            else if (produit.getSodium() > 270 && produit.getSodium() <= 360) point_N += 3;
            else if (produit.getSodium() > 360 && produit.getSodium() <= 450) point_N += 4;
            else if (produit.getSodium() > 450 && produit.getSodium() <= 540) point_N += 5;
            else if (produit.getSodium() > 540 && produit.getSodium() <= 360) point_N += 6;
            else if (produit.getSodium() > 630 && produit.getSodium() <= 720) point_N += 7;
            else if (produit.getSodium() > 720 && produit.getSodium() <= 810) point_N += 8;
            else if (produit.getSodium() > 810 && produit.getSodium() <= 900) point_N += 9;
            else if (produit.getSodium() > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40){
                point_N += 1;
                fruits_legumesScore += 1;
            }
            else if (produit.getFruits_legumes() > 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 5;
                fruits_legumesScore += 5;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéines
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;

        }

        ///////// Etape 2 //////////////////////////////////////////////////////////////////////
        if(point_N >= 11){
            if(fruits_legumesScore >= 5){
                point = point_N - point_P;
            }
            else{
                point = point_N - (fibreScore + fruits_legumesScore);
            }
        }
        else{
            point = point_N - point_P;
        }
        ////////// Etape 3 //////////////////////////////////////////////////////////////////////
        if(produit.getCategorie().equals(Config.CATEGORIES[0])){
            if (point < 1){
                return "B";
            }
            else if(point >=2 && point <= 5){
                return "C";
            }
            else if(point >=6 && point <= 9){
                return "D";
            }
            else if(point >= 10){
                return "E";
            }
        }
        else if(produit.getCategorie().equals(Config.CATEGORIES[7])){
            return "A";
        }
        else{
            if(point < -1){
                return "A";
            }
            else if(point >= 0 && point <= 2){
                return "B";
            }
            else if(point >= 3 && point <= 10){
                return "C";
            }
            else if(point >= 11 && point <= 18){
                return "D";
            }
            else if(point >= 19){
                return "E";
            }
        }
        return null;
    }
}

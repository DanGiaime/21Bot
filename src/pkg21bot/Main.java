/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg21bot;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Dan
 */
public class Main {
    public static int[] deck;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random randy = new Random();
        deck = new int[10];
        resetDeck();
        int busts = 0;
        int wins = 0;
        
        int iterations = 1000;
        int playerScore = 0;
        double chanceOfWinning = 0;
        for (int i = 0; i < iterations; i++) {
            //Create scenario, preform inital calculations
            playerScore = generateRandomScenario(randy);
            displayScenario();
            chanceOfWinning = chanceOfWinning(playerScore);
            
            //Play out hand
            while (chanceOfWinning >= 0.4) {
                playerScore += hit(randy);
                System.out.println("Hit!: New Score: " + playerScore);
                chanceOfWinning = chanceOfWinning(playerScore);
            }
            
            
            System.out.println(((playerScore>21) ? "Bust!" : "Stand!") 
                    + ": Final Score: " + playerScore);
            if(playerScore>21){busts++;}
            else if(playerScore>19){wins++;}
            System.out.println("--------------------");
            resetDeck();
        }
        System.out.println("Busts: " + busts);
        System.out.println("Wins: " + wins);
    }
    
   /**
    * Randomly chooses a few cards to be visible for calculation
    */
    public static int generateRandomScenario(Random r){
        int numCardsVisible = r.nextInt(9) + 1; //min 1 for our dealer
        int playerScore = 0; //sum of player's two cards
        
        for (int i = 0; i < 2; i++) {
            int index = r.nextInt(12);
            
            if(index >= 9){
                index = 9; //Since we combine all Jacks, Queens, and Kings
            }
            
            deck[index]--;
            playerScore += index + 1;
            System.out.println("Player hand has a " + (index+1));
        }
        
        while(numCardsVisible > 0){
            int index = r.nextInt(12);
            
            if(index >= 9){
                index = 9; //Since we combine all Jacks, Queens, and Kings
            }
            
            if(deck[index] > 0){
                deck[index]--; numCardsVisible--;
            }
        }
        
        return playerScore;
    }
    
   /**
    * Resets deck.
    */
    public static void resetDeck(){
        for (int i = 0; i < deck.length-1; i++) {
            deck[i] = 4;
        }
        deck[deck.length-1] = 12;
    }
    
    /**
    * Hits
    */
    public static int hit(Random r){
        int card = r.nextInt(numCards()) + 1;
        return chooseCard(card);
    }
    
    /**
    * Calculates chance of a positive outcome by hitting
    */
    public static double chanceOfWinning(int playerScore){
        int maxValue = 21 - playerScore; //Highest beneficial card value
        double chance = 0;
        
        if(maxValue > 10){maxValue = 10;}
        for (int i = 0; i < maxValue; i++) {
            chance += deck[i];
        }
        
        System.out.println("Chance of success:" + chance/52);
        return chance/52;
    }
    
    public static int numCards(){
        int numCardsInDeck = 0;
        for (int i = 0; i < deck.length; i++) {
            numCardsInDeck += deck[i];
        }
        System.out.println("The deck has "+numCardsInDeck+" cards");
        return numCardsInDeck;
    }
    
    public static int chooseCard(int card){
        int currCard = 0;
        
        while(card > 0){
            card -= deck[currCard];
            currCard++;
        }
        
        deck[currCard-1]--;
        if (card!=0) {
            return currCard;
        }
        else{
            return currCard-1;
        }
    }
    
    public static void displayScenario(){
        for (int i = 0; i < deck.length-1; i++) {
            if(deck[i]<4){
                System.out.println("There are " + (4-deck[i])
                       + " " + (i+1) + "'s shown");
            }
        }
        if(deck[deck.length-1]<12){
            System.out.println("There are " + (12-deck[deck.length-1])
                   + " " + (deck.length) + "'s shown");
        }
        
        System.out.println(Arrays.toString(deck));
    }
}

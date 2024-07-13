package hammurabi.docs.matuszek;
import java.sql.SQLOutput;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    int population = 100;
    int bushels = 2800;
    int acresOwned = 1000;
    int landValue = 19;
    public boolean gameon;
    int years = 0;
    int peopleStarved;
    int bushelsFed;
    int grainEatenRats;
    int newImmigrants;
    int totalBushels;
    int totalAcres;
    int totalStarved;



    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        Scanner scanner = new Scanner(System.in);
        gameon = true;


        for (int i = 0; i <= 10; i++){
        // declare local variables here: grain, population, etc.
        // statements go after the declations

        while(gameon) {
            i++;
            years++;
            totalAcres += acresOwned;
            totalBushels += bushels;
            if (years < 10) {
                System.out.println(">>>>> You are in year " + years + ".");
                System.out.println(">>>>> In the previous year, " + peopleStarved + " people starved to death.");
                System.out.println(">>>>> New immigrants arrived: " + newImmigrants);
                System.out.println(">>>>> The population is now " + population);
                System.out.println(">>>>> We currently have " + bushels + " bushels.");
                System.out.println(">>>>> Rats destroyed " + grainEatenRats + " bushels.");
                System.out.println(">>>>> The city owns " + acresOwned + " acres.");
                System.out.println(">>>>> Land is currently worth " + landValue + " per acre.");
                askHowManyAcresToBuy(landValue, bushels);
                sellHowManyAcres(acresOwned);
                askHowMuchGrainToFeedPeople(bushels);
                askHowManyAcresToPlant(acresOwned, population, bushels);
                plagueDeaths(population);
                starvationDeaths(population, bushelsFed);
                harvest(acresOwned);
                uprising(population, peopleStarved);
                immigrants(population, acresOwned, bushels);
                grainEatenByRats(bushels);
                newCostOfLand();
            }
            if (years == 10){
                gameon = false;
                break;
            }

        }

        }
        if (years == 10){
            finalSummary();
        }
        scanner.close();
    }



    //ALL GAME METHODS DOWN BELOW!!!

    public int askHowManyAcresToBuy(int priceP, int bushels){
        while(true) {
            System.out.print("HOW MANY ACRES DO YOU WISH TO BUY?  ");
            int userInput = scanner.nextInt();
            priceP = userInput * 19;
            if (bushels >= priceP) {
                this.bushels -= priceP;
                this.acresOwned += userInput;
                return userInput;
            } else {
                System.out.println("YOU DON'T HAVE ENOUGH BUSHELS TO PURCHASE.");
                System.out.println("You have " + this.bushels + " bushels left, and " + this.acresOwned + " acres left.");
            }
        }
    }

    public int sellHowManyAcres(int acresOwned){
        while(true) {
            System.out.println("HOW MANY ACRES WOULD YOU LIKE TO SELL?");
            int userInput = scanner.nextInt();
            if (userInput <= acresOwned) {
                this.acresOwned -= userInput;
                this.bushels += landValue * userInput;
                System.out.println(this.acresOwned + " acres left.");
                return userInput;
            } else {
                System.out.println("YOU DON'T HAVE ENOUGH ACRES BUDDY!");
            }
        }
    }

    public int askHowMuchGrainToFeedPeople(int bushels){
        while(true) {
            System.out.println("HOW MANY BUSHELS WOULD YOU LIKE TO FEED THE PEOPLE?");
            System.out.println("YOU HAVE " + bushels + " CURRENTLY.");
            int userInput = scanner.nextInt();
            if (userInput < bushels) {
                this.bushels -= userInput;
                System.out.println("THANKS FOR FEEDING YOUR PEOPLE. VERY GENEROUS.");
                System.out.println("YOU NOW HAVE " + this.bushels + " LEFT.");
                bushelsFed = userInput;
                return userInput;
            } else {
                System.out.println("YOU DON'T HAVE ENOUGH BUSHELS FOR THIS AMOUNT!");
            }
        }
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
//        int peoplePerAcres = population / 10;
        while(true) {
            System.out.println("How many acres would you like to plant?");
            int userInput = scanner.nextInt();
            int amountOfBushelsNeeded = userInput * 2;
            int amountOfPeopleNeeded = userInput / 10;
            if (acresOwned >= userInput && bushels >= amountOfBushelsNeeded && this.population >= amountOfPeopleNeeded) {
                System.out.println("You're in the process of planting " + userInput + " acres.");
                this.bushels -= amountOfBushelsNeeded;
//                System.out.println("You now have " + this.bushels + " bushels left.");

//                uprising(this.population, this.peopleStarved);


                return userInput;
            } else {
                System.out.println("You can't plant this amount of acres.");
            }
        }


        //have enough acres, bushels, and enough people to work the acres
    }



    public int plagueDeaths(int i) {
        Random random = new Random();
        if (random.nextInt(101) <= 15) {
            System.out.println("!!!!!!!!!! IT'S A PLAGUE!!! - HALF YOUR POPULATION DIED !!!!!!!!!!");
            this.population -= this.population / 2;
            return this.population;
        } else {
//            System.out.println("No plague- pHEW.");
            return 0;
        }

    }

    public int starvationDeaths(int ppl, int bushelsFed) {
        int amountOfBushelsNeeded = ppl * 20;
        if (bushelsFed >= amountOfBushelsNeeded){
            this.bushels -= amountOfBushelsNeeded;
//            immigrants(this.population,this.acresOwned,this.bushels);
//            System.out.println("No one went starving. Nice.");
            this.peopleStarved = 0;
            return 0;
        } else {
            int bushelsShort = amountOfBushelsNeeded - bushelsFed;
            double amountOfPplStarved = (double) bushelsShort / 20;
            int numberCeil = (int) Math.ceil(amountOfPplStarved);
            this.population -= numberCeil;
            this.peopleStarved = numberCeil;
            totalStarved += numberCeil;
//            System.out.println("YOU HAVE " + this.peopleStarved + " STARVED!");
            return numberCeil;
        }
//        return 0;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        double populationNumber = (int)population * 0.45;
        if (howManyPeopleStarved > populationNumber){
            System.out.println("YOU'RE PEOPLE HAVE STARVED. YOU LOST!");
            gameon = false;
            return true;
        } else {
//            System.out.println("No one starved. Nice");
            return false;
        }
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
//        if(starvationDeaths(population, grainInStorage) == 0){
        if (this.peopleStarved == 0){
            int results = (20 * acresOwned + grainInStorage) / (100 * population) +1;
            this.population += results;
//            System.out.println("You gained " + results + " immigrants.");
            newImmigrants = results;
            return results;
        }
        System.out.println("You get no immigrants.");
        newImmigrants = 0;
        return 0;
    }

    public int harvest(int acres) {
        Random random = new Random();
        int randoNumber = random.nextInt(6) + 1;
        this.bushels += acres * randoNumber;
        return acres * randoNumber;
    }

    public int grainEatenByRats(int bushels) {
            Random random = new Random();
            while (years < 10) {
                if (random.nextInt(101) < 40) {
                    Random random2 = new Random();
                    double daNumber = (random2.nextInt(21) + 10);
                    grainEatenRats = (int) (daNumber * bushels) / 100;
                    this.bushels -= grainEatenRats;
                    System.out.println("!!!!!!!!!! RATS ARE HERE !!!!!!!!!!");
                    return grainEatenRats;

                } else {
//                System.out.println("No rats, lucky!");
                    return 0;

                }
            }
            return 0;
    }

    public int newCostOfLand() {
        Random random = new Random();
        int price = (random.nextInt(7) + 17);
        landValue = price;
        return price;
    }

    public void finalSummary(){
        System.out.println("***************");
        System.out.println("CONGRATULATIONS, YOU WIN!");
        System.out.println("IN TEN YEARS...");
        System.out.println("YOU GAINED " + totalBushels + " TOTAL BUSHELS!");
        System.out.println("YOU LET " + totalStarved + " STARVE!");
        System.out.println("YOU GAINED " + totalAcres + " TOTAL ACRES!");
        System.out.println("GOOD JOB!");
        System.out.println("***************");
    }

    //other methods go here
}
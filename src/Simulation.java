import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Pat
 */
public class Simulation {
    
    public int popSize, percHawk, resourceValue, conflictCost;
    public int encounterNumber = 0, numHawkStart;
    public ArrayList<Animal> population_whole;
    public ArrayList<Animal> population_valid;
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Core Functions">
    
    /***
     * Simulate a single conflict between two animals, any animal that dies
     * is removed from the valid population list and return a string to record 
     * the conflict
     * @param an1 Animal 1
     * @param an2 Animal 2
     * @return String representation of the conflict
     */
    public String IndividualConflict(Animal an1, Animal an2){
        String encounterString = "Encounter: " + encounterNumber + "\n";
        if(an1.type == Animal.AnimalType.dead ||
                an2.type == Animal.AnimalType.dead){
            
            encounterString += "ONE OR MORE DEAD ANIMALS WAS SELECTED";
            
        }
        else if(an1.type == Animal.AnimalType.dove &&
                an2.type == Animal.AnimalType.dove){
            
            an1.AddResource(resourceValue/2);
            an2.AddResource(resourceValue/2);
            encounterString += "Individual " + an1.number + ": Dove\n" +
            "Individual " + an2.number + ": Dove\n" +
            "Dove/Dove: Dove: +" + resourceValue/2 +
                    "	Dove: +" + resourceValue/2 + "\n" +
            "Individual " + an1.number + "=" + an1.resourceCount +
                    "Individual " + an2.number + "=" + an2.resourceCount + "\n";
        }
        else if(an1.type == Animal.AnimalType.hawk &&
                an2.type == Animal.AnimalType.dove){
            
            an1.AddResource(resourceValue);
            encounterString += "Individual " + an1.number + ": Hawk\n" +
            "Individual " + an2.number + ": Dove\n" +
            "Hawk/Dove: Hawk: +" + resourceValue + "	Dove: +0\n" +
            "Individual " + an1.number + "=" + an1.resourceCount +
                    "Individual " + an2.number + "=" + an2.resourceCount + "\n";
        }
        else if(an1.type == Animal.AnimalType.dove &&
                an2.type == Animal.AnimalType.hawk){
            
            an2.AddResource(resourceValue);
            encounterString += "Individual " + an1.number + ": Dove\n" +
            "Individual " + an2.number + ": Hawk\n" +
            "Dove/Hawk: Dove: +0	Hawk: +" + resourceValue + "\n" +
            "Individual " + an1.number + "=" + an1.resourceCount +
                    "Individual " + an2.number + "=" + an2.resourceCount + "\n";
        }
        else if(an1.type == Animal.AnimalType.hawk &&
                an2.type == Animal.AnimalType.hawk){
            
            encounterString += "Individual " + an1.number + ": Hawk\n" +
            "Individual " + an2.number + ": Hawk\n" +
            "Hawk/Hawk: Hawk: -" + (conflictCost - resourceValue) + 
                    "Hawk: -" + conflictCost + "\n";
            if(!an1.FightResourceLoss(conflictCost - resourceValue)){
                population_valid.remove(an1);
                encounterString += "Hawk one has died!";
            }
            if(!an2.FightResourceLoss(conflictCost)){
                population_valid.remove(an2);
                encounterString += "Hawk two has died!";
            }
            encounterString += "Individual " + an1.number + "=" + an1.resourceCount +
                    "Individual " + an2.number + "=" + an2.resourceCount + "\n";
        }
        else{
            encounterString += "INVALID ANIMAL TYPE";
        }
        return encounterString;
    }
    
    /***
     * Run simulation N many times
     * @param N 
     * @return 0: normal exit, 1: population too low
     */
    public int RunSimulationNTimes(int N){
        Random gen = new Random();
        for (int i = 0; i < N; i++) {
            if(population_valid.size() <= 1){
                System.out.println("Population size: " + population_valid.size() +
                        ", too low to continue, returning to menu.");
                return 1;
            }
            encounterNumber++;
            int ann1, ann2;
            ann2 = -1;
            ann1 = gen.nextInt(population_valid.size());
            while(ann2 == -1){
                ann2 = gen.nextInt(population_valid.size());
                if(ann2 == ann1)
                    ann2 = -1;
            }
            
            System.out.println(IndividualConflict(population_valid.get(ann1),
                    population_valid.get(ann2)));
        }
        return 0;
    }
    
    
    /***
     * Prints menu to console
     */
    public void Menu(){
        System.out.print("===============MENU=============\n" +
            "1 ) Starting Stats\n" +
            "2 ) Display Individuals and Points\n" +
            "3 ) Display Sorted\n" +
            "4 ) Have 1000 interactions\n" +
            "5 ) Have 10000 interactions\n" +
            "6 ) Have N interactions\n" +
            "7 ) Step through interactions \"Stop\" to return to menu\n" +
            "8 ) Quit\n" +
            "================================\n" +
            "> ");
    }
    
    /***
     * 
     */
    public void MenuLoop(){
        
        boolean notDone = true;
        String nLine;
        Scanner in;
        in = new Scanner(System.in);
        while(notDone){
            Menu();
            nLine = in.nextLine();
            
            if(nLine.compareTo("1") == 0){
                StartingStats();
            }
            else if(nLine.compareTo("2") == 0){
                DisplayIndAndResults();
            }
            else if(nLine.compareTo("3") == 0){
                DisplaySorted();
            }
            else if(nLine.compareTo("4") == 0){
                RunOneThousand();
            }
            else if(nLine.compareTo("5") == 0){
                RunTenThousand();
            }
            else if(nLine.compareTo("6") == 0){
                String inL;
                int c = -1;
                while(true){
                    System.out.println("How many?");
                    inL = in.nextLine();
                    try{
                        c = Integer.parseInt(inL);
                        if(c <= 0){
                            System.out.println("Must be positive number");
                            c = -1;
                        }
                    }
                    catch(Exception e){
                        System.out.println("That was not a number");
                    }
                    if(c != -1)
                        break;
                }
                RunSimulationNTimes(c);
            }
            else if(nLine.compareTo("7") == 0){
                StepRun();
            }
            else if(nLine.compareTo("8") == 0){
                return;
            }
            else{
                System.out.println("Unknown input");
            }
        }
        
    }
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Menu Functions">
    
    
    /***
     * Displays statistics given at program start
     */
    public void StartingStats(){
        System.out.println("Population size: " + popSize + "\n" +
            "Percentage of Hawks: " + percHawk + "%\n" +
            "Number of Hawks: " + numHawkStart + "\n" +
            "\n" +
            "Percentage of Doves: " + (100-percHawk) + "%\n" +
            "Number of Doves: " + (popSize-numHawkStart) + "\n" +
            "\n" +
            "Each resource is worth: " + resourceValue + "\n" +
            "Cost of Hawk-Hawk interaction: " + conflictCost);
    }
    
    
    /***
     * Display a list of animals, number type and resources
     */
    public void DisplayIndAndResults(){
        for(int i = 0; i < population_whole.size(); i++) {
            System.out.println(population_whole.get(i).toStringVerbose());
        }
    }
    
    
    /***
     * Displays a sorted list of animals, by type then resource count
     */
    public void DisplaySorted(){
        ArrayList<Animal> f = new ArrayList();
        f.addAll(population_whole);
        f.sort((a,b) -> Animal.compareTo(a, b));
        for (int i = 0; i < f.size(); i++) {
            System.out.println(f.get(i).toString());
        }
    }
    
    
    /***
     * Run simulation one thousand times
     */
    public void RunOneThousand(){
        RunSimulationNTimes(1000);
    }
    
    
    /***
     * Run simulation ten thousand times
     */
    public void RunTenThousand(){
        RunSimulationNTimes(10000);
    }
    
    
    /***
     * Run through a simulation step by step
     * Press enter to step
     * Input "Stop" to abort to menu
     */
    public void StepRun(){
        Scanner in;
        in = new Scanner(System.in);
        Random gen = new Random();
        boolean notDone = true;
        boolean notHasInput;
        while(notDone){
            if(population_valid.size() <= 1){
                System.out.println("Population size: " + population_valid.size() +
                        ", too low to continue, returning to menu.");
                return;
            }
            encounterNumber++;
            notHasInput = true;
            int ann1, ann2;
            ann2 = -1;
            ann1 = gen.nextInt(population_valid.size());
            while(ann2 == -1){
                ann2 = gen.nextInt(population_valid.size());
                if(ann2 == ann1)
                    ann2 = -1;
            }
            System.out.println(IndividualConflict(population_valid.get(ann1),
                    population_valid.get(ann2)));
            while(notHasInput){
                System.out.println(">");
                String nLine;
                nLine = in.nextLine();
                if(nLine.compareTo("") == 0){
                    notHasInput = false;
                }
                else if(nLine.compareTo("Stop") == 0){
                    notDone = false;
                    notHasInput = false;
                }
                else{
                    System.out.println("Unknown Input");
                }
            }
        }
    }
    
    
    //</editor-fold>
    
    
    /***
     * Create a new simulation
     * @param popSize Size of initial population
     * @param percHawk Percentage of hawks in initial population
     * @param resourceValue Value of each resource
     * @param conflictCost Cost of Hawk/Hawk conflict
     */
    public Simulation(int popSize, int percHawk, int resourceValue,
            int conflictCost){
        this.popSize = popSize;
        this.percHawk = percHawk;
        this.resourceValue = resourceValue;
        this.conflictCost = conflictCost;
        
        population_whole = new ArrayList();
        
        this.numHawkStart = (popSize * percHawk / 100);
        
        for (int i = 0; i < numHawkStart; i++) {
            population_whole.add(new Animal(Animal.AnimalType.hawk, i));
        }
        for (int i = numHawkStart; i < popSize; i++) {
            population_whole.add(new Animal(Animal.AnimalType.dove, i));
        }
        
        population_valid = new ArrayList(population_whole);
    }
}

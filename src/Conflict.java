/**
 *
 * @author Pat
 */
public class Conflict {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int popSize = 0, percHawk = 20, resourceValue = 50, conflictCost = 100;
        if(args.length > 4 || args.length == 0){
            System.err.println("Usage: Conflict popSize [percentHawks] [resourceAmt] [costHawk-Hawk]");
            return;
        }
        if(args.length == 4){
            try{
                conflictCost = Integer.parseInt(args[3]);
            }
            catch(Exception e){
                System.err.println("Input 4 was NAN");
                return;
            }
        }
        if(args.length >= 3){
             try{
                resourceValue = Integer.parseInt(args[2]);
            }
            catch(Exception e){
                System.err.println("Input 3 was NAN");
                return;
            }
        }
        if(args.length >= 2){
             try{
                percHawk = Integer.parseInt(args[1]);
            }
            catch(Exception e){
                System.err.println("Input 2 was NAN");
                return;
            }
        }
        if(args.length >= 1){
             try{
                popSize = Integer.parseInt(args[0]);
            }
            catch(Exception e){
                System.err.println("Input 1 was NAN");
                return;
            }
        }
        
        Simulation sim = new Simulation(popSize, percHawk, resourceValue, conflictCost);
        
        sim.MenuLoop();
        
    }
    
    
}

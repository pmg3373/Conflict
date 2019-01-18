/**
 *
 * @author Pat
 */
public class Animal {
    
    public enum AnimalType{
        hawk(2),
        dove(1),
        dead(0);
        
        private int value;
        
        AnimalType(int value){
            this.value = value;
        }
    }
    
    public AnimalType type;
    public int resourceCount, number;
    
    /***
     * Type of animal as string
     * @return 
     */
    public String TypeString(){
        if(null == this.type){
            return "ERROR: NULL";
        }
        else switch (this.type) {
            case dead:
                return "DEAD";
            case dove:
                return "Dove";
            case hawk:
                return "Hawk";
            default:
                return "ERROR: UNDEFINED";
        }
    }
    
    /***
     * [AnimalType]:[ResourceCount]
     * @return 
     */
    @Override
    public String toString(){
        return TypeString() + ":" + resourceCount;
    }
    
    /***
     * Individual[number}=[AnimalType]:[ResourceCount]
     * @return 
     */
    public String toStringVerbose(){
        return "Individual[" + this.number + "]=" +this.toString();
    }
    
    /***
     * Compares two animals, by type then by resource amount
     * @param first
     * @param second
     * @return 
     */
    public static int compareTo(Animal first, Animal second){
        if(first.type.value > second.type.value){
            return -1;
        }
        else if(first.type.value < second.type.value){
            return 1;
        }
        else if(first.resourceCount > second.resourceCount){
            return -1;
        }
        else if(first.resourceCount < second.resourceCount){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    /***
     * Update the resource count for an animal
     * @param resourceValue Integer value of the resource
     */
    public void AddResource(int resourceValue){
        this.resourceCount += resourceValue;
    }
    
    /***
     * Subtract the cost of the conflict from the animal's resource count
     * then if the animal has a negative resource count update type to dead
     * @param conflictCost Integer cost of conflict
     * @return True if the animal survived, False if it died
     */
    public boolean FightResourceLoss(int conflictCost){
        this.resourceCount -= conflictCost;
        if(this.resourceCount < 0){
            this.type = AnimalType.dead;
            return false;
        }
        return true;
    }
    
    public Animal(AnimalType type, int number){
        this.type = type;
        this.number = number;
        resourceCount = 0;
    }
}

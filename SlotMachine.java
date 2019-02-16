import java.util.*;

public class SlotMachine{
    private final List<Reel> reels;
    
    public SlotMachine(Iterable<Reel> reels) {
        if (reels == null) {
            throw new IllegalArgumentException("argument to constructor is null");
        }
        for (Reel reel : reels) {
            if (reel == null) {
                throw new IllegalArgumentException("argument to constructor is null");
            }
        }
        
        this.reels = new ArrayList<>();
        for (Reel reel : reels) {
            this.reels.add(reel);
        }
    }
    
    public int getNumberOfReels() {
        return reels.size();
    }
    
    public Reel getReel(int i) {
    	if (i < 0 || i >= this.reels.size()) {
            throw new IllegalArgumentException("reel index: " + i + " should be between 0 and " + (this.reels.size() - 1));
        } 
    	return reels.get(i);
    }
    
    public int get(int reelIndex, int symbolIndex) {
        if (reelIndex < 0 || reelIndex >= this.reels.size()) {
            throw new IllegalArgumentException("reel index: " + reelIndex + " should be between 0 and " + (this.reels.size() - 1));
        }
        if (symbolIndex < 0 || symbolIndex >= this.reels.get(reelIndex).getNumberOfSymbols()) {
            throw new IllegalArgumentException("symbol index: " + symbolIndex + " should be between 0 and " + (this.reels.get(reelIndex).getNumberOfSymbols() - 1));
        }
        return reels.get(reelIndex).get(symbolIndex);
    }

     public static void main(String []args){
         int[] r1 = {0,1,2,3,4,5};
         int[] r2 = {100,101,102,103};
         int[] r3 = {4,8,15,16,23,42};
         
         Reel reel1 = new Reel(r1, true);
         Reel reel2 = new Reel(r2, true);
         Reel reel3 = new Reel(r3, true);
         
         List<Reel> reels = new ArrayList<>();
         reels.add(reel1);
         reels.add(reel2);
         reels.add(reel3);
         SlotMachine sm = new SlotMachine(reels);
         System.out.println(sm.get(2,5));
        System.out.println("Hello World");
     }
}

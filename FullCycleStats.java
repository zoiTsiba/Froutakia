
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FullCycleStats {
    private final Manager manager;
    private double FullCycleSize;
    private double RTP;
    private double hitRate;
    private double hitFreq;
    private double hits;
    private double totalPrize;
    private double volatility;
    private double confLevel = 1.96; // Confidence level for 95%
    private double stDev;
    private Map<Integer, Integer> prizeCounters = new HashMap<>();
    private int screenPrizeCounter;

    public double getRTP() {
        return RTP;
    }

    public double getHitRate() {
        return hitRate;
    }

    public double getHitFreq() {
        return hitFreq;
    }

    public double getHits() {
        return hits;
    }

    public double getTotalPrize() {
        return totalPrize;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getStDev() {
        return stDev;
    }

    public double getFullCycleSize() {
        return FullCycleSize;
    }

    public FullCycleStats(Manager manager) {
        if (manager == null)
            throw new IllegalArgumentException("argument to constructor is null");
        this.manager = manager;
        RTP = 0;
        hitRate = 0;
        hits = 0;
        totalPrize = 0;
        volatility = 0;
        stDev = 0;
        FullCycleSize = 0;
        screenPrizeCounter = 1;
        runFullCycle();
    }

    private void runFullCycle() {

        final ReelManager reelManager = manager.getReelManager();
        final SlotMachine slotMachine = reelManager.getSlotMachine();
        final WindowManager windowManager = manager.getWindowManager();
        final PaylineManager paylineManager = manager.getPaylineManager();
        final PaymentManager paymentManager = manager.getPaymentManager();
        final RewardManager rewardManager = manager.getRewardManager();
        final SymbolManager symbolManager = manager.getSymbolManager();
        final ScatterManager scatterManager = manager.getScatterManager();
        final ExcelOutputStatistics excelOutputStatistics = new ExcelOutputStatistics();
        // number of reels
        int nReels = slotMachine.getNumberOfReels();

        // to keep track of next element in each of the nReels reels
        int[] indices = new int[nReels];

        int temp = 0;

        while (true) {

            windowManager.getWindow(indices);

            // *****************************************************
            // BEGIN :: do whatever you want with current window
            // *****************************************************

            // Itterate through all PAYLINES
            int screenPrize = 0;
            for (Payline payline : paylineManager.paylines()) {
                int[] paylineSymbols = windowManager.getPaylineSymbols(payline);
                int reward = paymentManager.getReward(paylineSymbols);

                // Calculate the Winnings only
                if (reward > 0) {
                    totalPrize += reward; // Total reward of the Full Cycle
                    hits++; // Winning combinations of the Full Cycle
                    // screenPrize += reward; // Full prize for the screen table (all paylines)
                }

            }

            // Adding scatter PRIZES
            for (Integer scatter : symbolManager.getAllScatters()) {

                int scatReels = scatterManager.getNumberOfReelsContainingScatters(scatter, indices); // Calculate the scatter combination in the window
                int scatCombs = scatterManager.getNumberOfScatterCombinations(scatter, indices); // Calculate how many scatter combinations exists in the window (e.x. 2 or more scatter in one reel produce multiple winning scatter combinations)
                if (scatReels >= 2) {
                    int scatTotalPrize = rewardManager.getScatterReward(scatter, scatReels) * scatCombs; // The Total Prize of the window winning scatter combinations
                    totalPrize += scatTotalPrize; // Adding to Total Prize of Full Cycle
                    screenPrize += scatTotalPrize; // Adding to Total Prize of the current window
                    hits++; // Winning hits of Full Cycle
//                    System.out.println("Scatters on reel " + scatReels + " Scatter combs " + scatCombs);
//                    System.out.println(windowManager.windowToString());
                }
//                if (scatReels == 4) { // scatter hits for quartet is 27540 and for quintet is 486
//                    System.out.println("Scatters on reel " + scatReels + " Scatter combs " + scatCombs);
//                    for (int i = 0; i < scatCombs; i++) {
//                        temp++;
//                    }
//                    System.out.println(temp);
//                    System.out.println(windowManager.windowToString());
//                }
            }

            FullCycleSize++; // All combinations of the Full Cycle

            int prizeHits = 0;
            // Prizes and a counter for how many times they appear in all paylines
            if (prizeCounters.containsKey(screenPrize)) {
                prizeHits = prizeCounters.get(screenPrize) + 1;
                prizeCounters.put(screenPrize, prizeHits);
            } else {
                screenPrizeCounter = 1;
                prizeCounters.put(screenPrize, screenPrizeCounter++);
            }

            // *****************************************************
            // END :: do whatever you want with current window
            // *****************************************************

            // find the rightmost reel that has more
            // elements left after the current element
            // in that reel
            int next = nReels - 1;
            while (next >= 0 && (indices[next] + 1 >= slotMachine.getReel(next).getNumberOfSymbols()))
                next--;

            // no such reel is found so no more combinations left
            if (next < 0)
                break;

            // if found move to next element in that reel
            indices[next]++;

            // for all reels to the right of this
            // reel current index again points to
            // first element
            for (int i = next + 1; i < nReels; i++)
                indices[i] = 0;

        }

        int paylineSize = paylineManager.paylinesSize(); //  Paylines  of the Full Cycle
        hitRate = FullCycleSize / hits; // Hit rate is the ratio of winning combinations in a Full Cycle
        hitFreq = hits / FullCycleSize; // Hit frequency is the odds that the machine will hit a payout on any given spin.
        RTP = totalPrize / (FullCycleSize * paylineSize); // Return to player (RTP) is the theoretical percentage of playing money that returns to the player

        // Iterate through screen prizes and how many times they appear
        Iterator it = prizeCounters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            int prize = (int) pair.getKey();
            int prizeHits = (int) pair.getValue();
            double prizeProb = prizeHits / FullCycleSize;
            stDev += prizeHits * Math.pow((prize - RTP), 2);
            it.remove(); // avoids a ConcurrentModificationException
        }

        //Standard Deviation and Volatility mathematics
        stDev = stDev / FullCycleSize;
        stDev = Math.sqrt(stDev);
        volatility = confLevel * stDev;

        // Output for Excel
        DecimalFormat df = new DecimalFormat("#.#####");

        String out = "\u001B Statistics Full Cycle\n\n";
        out += ("Full Cycle " + "\t" + df.format(FullCycleSize) + "\n");
        out += ("Total Prize  " + "\t" + df.format(totalPrize) + "\n");
        out += ("Hits " + "\t" + df.format(hits) + "\n");
        out += ("Hit frequency " + "\t" + df.format(hitFreq) + "\n");
        out += ("Hit rate " + "\t" + df.format(hitRate) + "\n");
        out += ("RTP " + "\t" + df.format(RTP) + "\n");
        out += ("Standard deviation " + "\t" + df.format(stDev) + "\n");
        out += ("Volatility " + "\t" + df.format(volatility) + "\n");

        // excelOutputStatistics.ExcelOutputBuilder(out); // Writing the output to an excel file.xls
    }

    public static void main(String[] args) {

        final String filename;
        final StringBuilder sb;
        long start, end;
        DecimalFormat df;
        float elapsedTimeMinutes;

        filename = "gamed.txt";
//		filename = "IO/bill.txt";
        sb = new StringBuilder();
        df = new DecimalFormat("#.#####");

        start = System.currentTimeMillis();
        In in = new In(filename);
        Manager manager = new Manager(in.readAll());
        FullCycleStats fcs = new FullCycleStats(manager);
        end = System.currentTimeMillis();

        elapsedTimeMinutes = (end - start) / (60 * 1000F);
        sb.append(" Time : ");
        sb.append(elapsedTimeMinutes);
        sb.append(" min");
        sb.append("\n");
        sb.append(" FullCycle Size :");
        sb.append(df.format(fcs.getFullCycleSize()));
        sb.append("\n");
        sb.append(" Total prize :");
        sb.append(df.format(fcs.getTotalPrize()));
        sb.append("\n");
        sb.append(" Hits :");
        sb.append(df.format(fcs.getHits()));
        sb.append("\n");
        sb.append(" HitRate :");
        sb.append(df.format(fcs.getHitRate()));
        sb.append("\n");
        sb.append(" Hit Frequency :");
        sb.append(df.format(fcs.getHitFreq() * 100));
        sb.append("%");
        sb.append("\n");
        sb.append(" Standard Deviation :");
        sb.append(df.format(fcs.getStDev()));
        sb.append("\n");
        sb.append(" Volatility :");
        sb.append(df.format(fcs.getVolatility()));
        sb.append("\n");
        sb.append(" RTP :");
        sb.append(df.format(fcs.getRTP()));
        sb.append("\n");
        System.out.println(sb.toString());
    }

}
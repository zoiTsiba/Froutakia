import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ExcelOutputStatistics {


    public ExcelOutputStatistics() {
    }

    public void ExcelOutputBuilder(String statistics) {

        BufferedWriter writer = null;
        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog + ".xls");

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));

            writer.write(statistics);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {

    }

}

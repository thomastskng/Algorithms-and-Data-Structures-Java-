import java.util.*;
import java.io.*;
import java.math.*;

public class ArrayClass{
    protected int[] input_array;
    protected int nElems;

    // multiple constructor
    public ArrayClass(){
        // nothing here
        this(0);
    }

    public ArrayClass(int max){
        input_array = new int [max];
        nElems = 0;
    }

    public void insert(int value){
        input_array[nElems++] = value;
    }

    public void display(int stop_index){
        for(int j = 0; j < nElems; j++){
            System.out.print(input_array[j] + " ");
            if(stop_index >= 0 && j == stop_index){
                break;
            }
        }
        System.out.println("");
    }

    public void swap(int i, int j){
        int temp = input_array[i];
        input_array[i] = input_array[j];
        input_array[j] = temp;
    }

    public void read_file_and_populate(String file_loc) throws IOException{
        // Read input file
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader( new InputStreamReader(fil));
          String element = null;
        while( ( element = br.readLine()) != null){
            insert(Integer.parseInt(element));
        }

    }

    public int file_line_count(String filepath) throws IOException{

        // Execute terminal command to count line of file
        String[] Command = new String[]{"/usr/bin/wc", "-l", filepath};
        ProcessBuilder builder = new ProcessBuilder(Command);
        // Redirect errorstream
        builder.redirectErrorStream(true);
        Process process = builder.start();
        // Read output on Terminal as our input stream
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // Get line count for filepath
        String line = reader.readLine();
        String[] line_parts = line.split(" ");

        // Read Input file and initialize the input array with size = line count
        return Integer.parseInt(line_parts[line_parts.length - 2]);
    }
}

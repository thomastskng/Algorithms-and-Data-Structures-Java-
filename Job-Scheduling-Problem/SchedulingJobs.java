/*
 *	javac SchedulingJobs.java
 *	java RunAlgo
 */
import java.io.*;
import java.util.*;
import java.math.*;


class Job implements Comparable<Job>{

    private int w;
    private int l;
    private static int job_no = 0;		// class variable
    private int job_num;			// instance variable
    // constructor
    public Job(int w, int l){
        this.w = w;
        this.l = l;
        job_num = job_no++;
    }

    public int get_w(){
        return w;
    }

    public void set_w(int w){
        this.w = w;
    }

    public int get_l(){
        return l;
    }

    public void set_l(int l){
        this.l = l;
    }

    public int get_jobNo(){
        return job_num;
    }

    public String toString(){
        return  get_jobNo() + " :{w: " + w + ", l: " + l + "}";
    }

    public int hashCode(){
        return (job_num * 31);
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Job other = (Job)o;
        return job_num == other.get_jobNo();
    }

    // uncomment the metric you want to use
    public double metric(){
        //return (double)w-l; 	// greedy algorithm that schedules jobs in decreasing order of the difference (weight - length)
        return (double)w/l;	// greedy algorithm that schedules jobs (optimally) in decreasing order of the ratio (weight/length)
    }

    public int compareTo(Job job){
        if(metric() < job.metric()){
            return -1;
        } else if(metric() == job.metric()){
            return 0;
        } else{
            return 1;
        }
    }
}

class RunAlgo{
    public static void main(String[] args) throws IOException{
        try{
            Job[] schedule = null;
            schedule = read_file_and_populate(schedule, "jobs.txt");
            quickSortAlgo(schedule);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static Job[] read_file_and_populate(Job[] schedule, String file_loc) throws IOException{
        FileInputStream fil = new FileInputStream(file_loc);
        BufferedReader br = new BufferedReader(new InputStreamReader(fil));
        String element = null;
        int counter = 0;
        while((element = br.readLine()) != null){
            String[] line = element.split("\\s+");
            if(counter == 0){
                schedule = new Job[Integer.parseInt(line[0])];
            } else{
                Job job = new Job(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
                schedule[counter - 1] = job;
            }
            counter++;
        }
        return schedule;
    }

    /*
     *	QuickSort Algo to sort the schedule[] in DECREASING order
     */
    public static void quickSortAlgo(Job[] input_arr){
        quickSort(input_arr, 0, input_arr.length-1);
        // completion times of job i
        double c_i = 0;
        double weighted_sum = 0;
        for(int i = 0; i < input_arr.length; i++){
            c_i += input_arr[i].get_l();
            weighted_sum += input_arr[i].get_w() * c_i;
        }
        System.out.println(weighted_sum);
    }

    public static void quickSort(Job[] input_arr, int p, int r){
        if(p < r){
            int q = randomized_partition(input_arr, p, r);
            quickSort(input_arr, p, q - 1);
            quickSort(input_arr, q + 1, r);
        } else{
            return;
        }
    }

    public static int partition(Job[] input_arr, int p, int r){
        Job x = input_arr[r];
        int i = p - 1;
        for(int j = p; j <= r - 1; j++){
            //if(input_arr[j] >= x){		// >= leads to DECREASING order
            // handling ties: if two jobs have equal differences/ratio, schedule the job with higher weight first
            if(input_arr[j].compareTo(x) > 0 || (input_arr[j].compareTo(x) == 0 &&
                                                 input_arr[j].get_w() > x.get_w() ) ){
                i = i + 1;
                swap(input_arr, i, j);
            }
        }
        swap(input_arr, i+1, r);
        return i+1;
    }

    public static void swap(Job[] input_arr, int i, int j){
        Job temp = input_arr[i];
        input_arr[i] = input_arr[j];
        input_arr[j] = temp;
    }

    /*
     *  Randomized Partition: generate a random number , then swap it with originally fixed pivot input_arr[r]
     *  before actually implementing the partition
     */

    public static int randomized_partition(Job[] input_arr, int p, int r){
        int i = p + (int)(Math.random() * ((r-p) + 1));
        swap(input_arr, r, i);
        return partition(input_arr, p, r);
    }
    // End of QuickSort Algo
}

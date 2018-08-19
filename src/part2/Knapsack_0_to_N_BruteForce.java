package part2;

import java.util.Arrays;

public class Knapsack_0_to_N_BruteForce {

	private static int[] weights;
	private static int[] values;
	private static int[] groupsize;
	private static int[] choices;
	private static int[] bestChoices;
	private static int weightLimt;
	
	public static void main(String[] args){
		
		String[] w = args[0].split(",");
		weights = new int[w.length];
		for(int i = 0; i < w.length; i++){
			weights[i] = Integer.parseInt(w[i]);
		}
		
		String[] v = args[1].split(",");
		values = new int[v.length];
		for(int i = 0; i < v.length; i++){
			values[i] = Integer.parseInt(v[i]);
		}
		
		String[] g = args[2].split(",");
		groupsize = new int[g.length];
		for(int i = 0; i < g.length; i++){
			groupsize[i] = Integer.parseInt(g[i]);
		}

		weightLimt = Integer.parseInt(args[3]);
		
		if(values.length != weights.length || values.length != groupsize.length || weights.length != groupsize.length){
			throw new Error("number of values and weights needs to be the same");
		}
		
		for(int i = 0; i < groupsize.length; i++){
			if(groupsize[i] < 1){
				throw new Error("every group must be atleast of size 1");
			}
		}
		
		final long startTime = System.currentTimeMillis();
		knapsack();
		final long endTime = System.currentTimeMillis();

		System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms");
	}

	private static void knapsack() {
		
		int maxValue = 0;
		int maxWeight = 0;
		
		choices = new int[groupsize.length];
		bestChoices = new int[groupsize.length];
		
		int combinations = groupsize[0]+1;
		for(int i = 1; i < groupsize.length; i++){
			combinations *= groupsize[i]+1;
		}
		//System.out.println("START");
		int j = 0;
		while(j < combinations-1){
			boolean iter = false;
			int k = 0;
			while(!iter){
				int sel = choices[k]+1;
				if(sel <= groupsize[k]){
					choices[k]++;
					iter = true;
				}
				else{
					choices[k] = 0;
					k++;
				}
			}
			
			int totValue = 0;
			int totWeight = 0;
			for(int i = 0; i < choices.length; i++){
				for(int c = 0; c < choices[i]; c++){
					totValue += values[i];
					totWeight += weights[i];
				}
			}
			
			if((totValue > maxValue && totWeight <= weightLimt) || (totValue == maxValue && totWeight <= weightLimt && totWeight < maxWeight)){//if we have a solution with same value but less weight we want the one with less weight
				maxValue = totValue;
				maxWeight = totWeight;
				for(int x = 0; x < bestChoices.length; x++){
					bestChoices[x] = choices[x];
				}
			}
			
			j++;
			
			//System.out.println("Amount of each object taken: "+Arrays.toString(choices)+"\tIteration: "+(j)+"\tTotal Value: "+totValue+"\tTotal Weight: "+totWeight);
		}
		
		System.out.println("Weights: "+Arrays.toString(weights));
		System.out.println("Values: "+Arrays.toString(values));
		System.out.println("Groupsize: "+Arrays.toString(groupsize));
		System.out.println("Weight Limit: "+weightLimt+"\n");
		System.out.println("Choices: "+Arrays.toString(bestChoices));
		System.out.println("Total Weight of chosen objects: "+maxWeight);
		System.out.println("Total Value of chosen objects: "+maxValue);
	}
}

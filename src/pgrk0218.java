/* MANANGATTI DHARMAN LEKHA KUMARAN cs610 PP WXYZ 
 * GOOGLE PAGE ALGORITHM IMPLEMENTATION
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class GPRAlgorithm
{
	int noOfvertices;
	int noOfEdges;
	HashMap<Integer, ArrayList<Integer>> outDegreeMap;
	HashMap<Integer, ArrayList<Integer>> inDegreeMap;
	HashMap<Integer, Double> pageRankMap;;
	double d = 0.85;
	public void calculateNoOfVeticesEdges(String fileName)
	{
		String currentLine;
		String[] vertEdge;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//					while((currentLine = br.readLine())!= null)
			currentLine = br.readLine();
			vertEdge = currentLine.split(" ");
			noOfvertices = Integer.valueOf(vertEdge[0]);
			noOfEdges = Integer.valueOf(vertEdge[1]);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public double calculateConstFn()
	{
		double constTerm = (1-d)/noOfvertices;
		return constTerm;
	}	
	public HashMap<Integer, ArrayList<Integer>> calculateOutDegree(String fileName) throws FileNotFoundException
	{
		String currentLine;
		int outVertexKey,inVertex;
		String[] outInVert;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		outDegreeMap = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> arr = new ArrayList<>();
		try
		{
			currentLine = br.readLine();

			while ((currentLine = br.readLine())!= null)
			{
				outInVert = currentLine.split(" ");
				outVertexKey = Integer.valueOf(outInVert[0]);
				inVertex = Integer.valueOf(outInVert[1]);
				if(outDegreeMap.containsKey(outVertexKey))
				{
					arr = outDegreeMap.get(outVertexKey);
					arr.add(inVertex);
					outDegreeMap.put(outVertexKey, arr);
				}
				else
				{
					ArrayList<Integer> arr1 = new ArrayList<>();
					arr1.add(inVertex);
					outDegreeMap.put(outVertexKey, arr1);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return outDegreeMap;
	}
	public HashMap<Integer, ArrayList<Integer>> calculateInDegree(String fileName) throws FileNotFoundException
	{
		String currentLine;
		int outVertex,inVertexKey;
		String[] outInVert;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		inDegreeMap = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> arr = new ArrayList<>();
		try
		{
			currentLine = br.readLine();

			while ((currentLine = br.readLine())!= null)
			{
				outInVert = currentLine.split(" ");
				outVertex = Integer.valueOf(outInVert[0]);
				inVertexKey = Integer.valueOf(outInVert[1]);
				if(inDegreeMap.containsKey(inVertexKey))
				{
					arr = inDegreeMap.get(inVertexKey);
					arr.add(outVertex);
					inDegreeMap.put(inVertexKey, arr);
				}
				else
				{
					ArrayList<Integer> arr1 = new ArrayList<>();
					arr1.add(outVertex);
					inDegreeMap.put(inVertexKey, arr1);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return inDegreeMap;
	}
	public void computeInitialValues(double initialValue)
	{
		pageRankMap = new HashMap<>();
		if(initialValue == 0)
		{
			for(int i = 0; i<noOfvertices; i++)
			{
				pageRankMap.put(i, initialValue);
			}

		}
		if(initialValue == 1)
		{
			for(int i = 0; i<noOfvertices; i++)
			{
				pageRankMap.put(i, initialValue);
			}
		}
		if(initialValue == -1)
		{
			for(int i = 0; i<noOfvertices; i++)
			{
				pageRankMap.put(i, 1.0/noOfvertices);
			}
		}
		if(initialValue == -2)
		{
			for(int i = 0; i<noOfvertices; i++)
			{
				pageRankMap.put(i, 1.0/Math.sqrt(noOfvertices));
			}
		}
	}
	public void printResult(HashMap<Integer, Double> pageRankMap)
	{
		double pgrValue ;
		Integer key;
		for(Entry <Integer, Double> entry : pageRankMap.entrySet())
		{
			key = entry.getKey();
			pgrValue = entry.getValue();
			System.out.print(" P[ " + key + "]=");
			System.out.printf("%.6f",pgrValue);	
		}
		System.out.println();
	}

	public void printResultVeryLargeGraph(HashMap<Integer, Double> pageRankMap)
	{
		double pgrValue ;
		Integer key = 0;
		int i=0;
		for(Entry <Integer, Double> entry : pageRankMap.entrySet())
		{
			if(i<3)
			{
				key = entry.getKey();
				pgrValue = entry.getValue();
				System.out.print("P[ " + key + "]=");
				System.out.printf("%.6f",pgrValue);
				System.out.println();
				i++;
			}
		}
		System.out.println();
	}
	public double calculatePageRankSubPart(int pageRankKey)
	{
		double pageRankValueEntry=0, pageOutSubValue = 0, pageOutSubValueTerm = 0, pageRankFinalTerm = 0 ;
		int outDegreeValue = 0;
		ArrayList<Integer> arr = new ArrayList<>();
		ArrayList<Integer> arr1 = new ArrayList<>();
		for (Entry<Integer, ArrayList<Integer>> entry : inDegreeMap.entrySet())
		{
			if(entry.getKey() == pageRankKey)
			{
				arr = entry.getValue();
				for(int i = 0; i<arr.size(); i++)
				{
					for (Entry<Integer, Double> entry1 : pageRankMap.entrySet())
					{
						if(entry1.getKey() == arr.get(i))
						{
							pageRankValueEntry = entry1.getValue();
							break;
						}
					}
					for (Entry<Integer, ArrayList<Integer>> entry2 : outDegreeMap.entrySet())
					{
						if(entry2.getKey() == arr.get(i))
						{
							arr1 = entry2.getValue();
							outDegreeValue = arr1.size();
							break;
						}
					}
					pageOutSubValue +=  (pageRankValueEntry/outDegreeValue);
				}
			}
		}
		pageOutSubValueTerm = d * pageOutSubValue;
		pageRankFinalTerm = calculateConstFn() + pageOutSubValueTerm;
		return pageRankFinalTerm;
	}

	public void GPRImplementation(int iterationValue, int initialValue, String fileName)
	{
		int i = 1;
		double pageRankFinalTerm = 0;
		HashMap<Integer, Double> pageRankMapCopy = new HashMap<>();
		calculateNoOfVeticesEdges(fileName);
		computeInitialValues(initialValue);
		try {
			calculateOutDegree(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			calculateInDegree(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(noOfvertices<=10)
		{
			System.out.print("Base :  0 :");
			printResult(pageRankMap);
			if(iterationValue>0)
			{
				while(i<=iterationValue)
				{
					for(Entry<Integer, Double> entry : pageRankMap.entrySet())
					{
						Integer key = entry.getKey();
						pageRankFinalTerm = calculatePageRankSubPart(key);
						pageRankMapCopy.put(key, pageRankFinalTerm);					
					}
					pageRankMap.putAll(pageRankMapCopy);
					if(i<10)
						System.out.print("Iter :  "+i+ " :");
					else if(i>9)
						System.out.print("Iter : "+i+ " :");
					i++;
					printResult(pageRankMap);
				}
			}
			else if(iterationValue == 0)
			{
				i = 0;
				while(true)
				{		
					for(Entry<Integer, Double> entry : pageRankMap.entrySet())
					{
						Integer key = entry.getKey();
						pageRankFinalTerm = calculatePageRankSubPart(key);
						pageRankMapCopy.put(key, pageRankFinalTerm);
					}
					for(int j=0;j<pageRankMap.size();j++)
					{
						if((pageRankMap.get(j) - pageRankMapCopy.get(j))> Math.pow(10, -5))
						{
							if(i>0)
							{
								if(i<10)
									System.out.print("Iter :  "+i+ " :");
								else if(i>9)
									System.out.print("Iter : "+i+ " :");
								printResult(pageRankMap);
							}
							break;
						}
						else if(j == pageRankMap.size()-1)
						{
							if(i<10)
								System.out.print("Iter :  "+i+ " :");
							else if(i>9)
								System.out.print("Iter : "+i+ " :");
							printResult(pageRankMap);
							return;
						}
					}
					pageRankMap.putAll(pageRankMapCopy);
					i++;
				}
			}
		}
		else if(noOfvertices>10)
		{
			computeInitialValues(-1);
			while(true)
			{
				for(Entry<Integer, Double> entry : pageRankMap.entrySet())
				{
					Integer key = entry.getKey();
					pageRankFinalTerm = calculatePageRankSubPart(key);
					pageRankMapCopy.put(key, pageRankFinalTerm);
				}
				for(int j=0;j<pageRankMap.size();j++)
				{
					if((pageRankMap.get(j) - pageRankMapCopy.get(j))> Math.pow(10, -5))
					{
						break;
					}
					else if(j == pageRankMap.size()-1)
					{
						System.out.println("Iter  : "+i);
						printResultVeryLargeGraph(pageRankMap);
						return;
					}
				}
				pageRankMap.putAll(pageRankMapCopy);
				i++;
			}
		}
	}
}
public class pgrk0218 {
	public static void main(String[] args) {
		GPRAlgorithm g = new GPRAlgorithm();
		int initialValue, iterations;
		String filename;
		iterations = Integer.valueOf(args[0]);
		initialValue = Integer.valueOf(args[1]);
		filename = args[2];
		g.GPRImplementation(iterations, initialValue, filename);
	}
}

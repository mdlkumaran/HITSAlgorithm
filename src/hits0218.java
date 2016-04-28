/* MANANGATTI DHARMAN LEKHA KUMARAN cs610 PP WXYZ 
 * HITS ALGORITHM IMPLEMENTATION
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class HitsAlgorithm0218
{
	int noOfvertices;
	int noOfEdges;
	double[][] hub;
	double[][] authority;
	double[][] adjMatrix;

	public void calculateNoOfVeticesEdges0218(String fileName)
	{
		String currentLine;
		String[] vertEdge;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
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
	public void initializeAdjMatrixArray0218(int noOfVertices)
	{

		noOfVertices = this.noOfvertices;

		hub = new double[noOfvertices][1];
		authority = new double[noOfvertices][1];
		adjMatrix = new double[noOfvertices][noOfvertices];
	}

	public void displayArray0218(double[][] a)
	{
		for (int row = 0; row < a.length; row ++)
			for (int col = 0; col < a[row].length; col++)
			{
				System.out.println("a["+ row+"]["+col+"]" + a[row][col]);
			}
	}

	public void computeInitialValues0218(double initialValue)
	{
		if(initialValue == 0)
		{
			for (int row = 0; row < noOfvertices; row ++)
				for (int col = 0; col < 1; col++)
				{
					hub[row][col] = initialValue;
					authority[row][col] = initialValue;
				}

		}

		if(initialValue == 1)
		{
			for (int row = 0; row < noOfvertices; row ++)
				for (int col = 0; col < 1; col++)
				{
					hub[row][col] = initialValue;
					authority[row][col] = initialValue;
				}
		}
		if(initialValue == -1)
		{
			for (int row = 0; row < noOfvertices; row ++)
				for (int col = 0; col < 1; col++)
				{
					hub[row][col] = 1.0/noOfvertices;
					authority[row][col] = 1.0/noOfvertices;
				}

		}
		if(initialValue == -2)
		{
			for (int row = 0; row < noOfvertices; row ++)
				for (int col = 0; col < 1; col++)
				{
					hub[row][col] = 1.0/Math.sqrt(noOfvertices);
					authority[row][col] = 1.0/Math.sqrt(noOfvertices);
				}
		}
	}

	public void initializeAdjMatrixValues0218(String fileName)
	{
		String currentLine;
		int edgeI;
		int edgeJ;
		String[] edgeIJ;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			while((currentLine = br.readLine())!= null)
			{
				edgeIJ = currentLine.split(" ");
				edgeI = Integer.valueOf(edgeIJ[0]);
				edgeJ = Integer.valueOf(edgeIJ[1]);
				adjMatrix[edgeI][edgeJ] = 1;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public double[][] calculateMatrixMultiplication0218(double[][] A, double[][] B)
	{
		int mA = A.length;
		int nA = A[0].length;
		int mB = B.length;
		int nB = B[0].length;
		if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
		double[][] C = new double[mA][nB];
		for (int i = 0; i < mA; i++)
			for (int j = 0; j < nB; j++)
				for (int k = 0; k < nA; k++)
					C[i][j] += A[i][k] * B[k][j];
		return C;
	}

	public double[][] calculateMatrixTranspose0218(double[][] A)
	{
		int m = A.length;
		int n = A[0].length;
		double[][] C = new double[n][m];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C[j][i] = A[i][j];
		return C;
	}

	public double calculateScaleValue0218(double[][] arr)
	{
		double calc = 0;
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				calc += arr[row][col] * arr[row][col];
			}
		double scale = Math.sqrt(calc);
		return scale;
	}

	public void scaleMatrixPrint0218(double scaleAuthority, double scaleHub)
	{

		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				System.out.print(" A/H["+ row + "]="); 
				System.out.printf("%.6f", authority[row][col]/scaleAuthority); 
				System.out.print("/");
				System.out.printf("%.6f",hub[row][col]/scaleHub);
			}
	}

	public void scaleMatrixPrintZero0218(int iterationValue)
	{
		int i =1;
		System.out.print("Base :  0 :");
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				System.out.print(" A/H["+ row + "]="); 
				System.out.printf("%.6f", 0.0); 
				System.out.print("/");
				System.out.printf("%.6f",0.0);
			}
		System.out.println();
		while(i<=iterationValue)
		{
			if(i<10)
				System.out.print("Iter :  "+i+ " :");
			else if(i>9)
				System.out.print("Iter : "+i+ " :");
			for (int row = 0; row < noOfvertices; row ++)
				for (int col = 0; col < 1; col++)
				{
					System.out.print(" A/H["+ row + "]="); 
					System.out.printf("%.6f", 0.0); 
					System.out.print("/");
					System.out.printf("%.6f",0.0);
				}
			System.out.println();
			i++;
		}
		System.exit(0);
	}

	public void scaleMatrixPrintForLargeGraph0218(double scaleAuthority, double scaleHub)
	{
		double hubResult,authorityResult;
		for (int row = 0; row < 3; row ++)
			for (int col = 0; col < 1; col++)
			{
				authorityResult = authority[row][col]/scaleAuthority;
				hubResult = hub[row][col]/scaleHub;

				System.out.print(" A/H["+ row + "]=");
				System.out.printf("%.6f", authorityResult);
				System.out.print("/");
				System.out.printf("%.6f",hubResult);
				System.out.println();
			}
	}

	public ArrayList<Double> doubMatrixToArrList0218(double[][] arr)
	{
		ArrayList<Double> arrList = new ArrayList<>();
		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr[i].length; j++)
				arrList.add(arr[i][j]);
		return arrList;
	}

	public void scaleMatrixCompare0218(double scaleAuthority, double scaleHub)
	{
		int i=0;
		ArrayList<Double> compareAuthArrayList = new ArrayList<>();
		ArrayList<Double> compareHubArrayList = new ArrayList<>();
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				if(i>0)
				{
					System.out.println(compareAuthArrayList.get(i));
					System.out.println(compareAuthArrayList.get(i));
					if((compareAuthArrayList.get(i) - compareHubArrayList.get(i)) >(Math.pow(10, -5)))
						break;
				}
				i++;
			}

	}

	public void hitsImplementation0218(int iterationValue, int initialValue, String fileName)
	{
		int i = 1;
		double[][] lTranspose;
		double scaleHub = 1, scaleAuthority = 1;
		calculateNoOfVeticesEdges0218(fileName);
		initializeAdjMatrixArray0218(noOfvertices);
		computeInitialValues0218(initialValue);
		if(initialValue == 0)
		{
			scaleMatrixPrintZero0218(iterationValue);
		}
		initializeAdjMatrixValues0218(fileName);
		if(noOfvertices<=10)
		{
			System.out.print("Base :  0 :");
			scaleMatrixPrint0218(scaleAuthority,scaleHub);
			System.out.println();
			if(iterationValue>0)
			{
				while(i<=iterationValue)
				{
					if(i<10)
						System.out.print("Iter :  "+i+ " :");
					else if(i>9)
						System.out.print("Iter : "+i+ " :");
					lTranspose = calculateMatrixTranspose0218(adjMatrix);
					authority = calculateMatrixMultiplication0218(lTranspose, hub);
					hub = calculateMatrixMultiplication0218(adjMatrix, authority);
					scaleHub = calculateScaleValue0218(hub);
					scaleAuthority = calculateScaleValue0218(authority);
					scaleMatrixPrint0218(scaleAuthority,scaleHub);
					i++;
					System.out.println();
				}
			}
			else if(iterationValue == 0)
			{
				i = 0;
				ArrayList<Double> hubArrList1 = new ArrayList<>();
				ArrayList<Double> authCompList1 = new ArrayList<>();
				ArrayList<Double> hubArrList2 = new ArrayList<>();
				ArrayList<Double> authCompList2 = new ArrayList<>();
				double scaleHub1 = 1, scaleAuthority1 = 1;
				while(true)
				{
					hubArrList1 = doubMatrixToArrList0218(hub);
					authCompList1 = doubMatrixToArrList0218(authority);
					scaleHub1 = scaleHub;
					scaleAuthority1 = scaleAuthority;
					lTranspose = calculateMatrixTranspose0218(adjMatrix);
					authority = calculateMatrixMultiplication0218(lTranspose, hub);
					authCompList2 = doubMatrixToArrList0218(authority);
					hub = calculateMatrixMultiplication0218(adjMatrix, authority);
					hubArrList2 = doubMatrixToArrList0218(hub);
					scaleHub = calculateScaleValue0218(hub);					
					scaleAuthority = calculateScaleValue0218(authority);
					if(i>0)
					{
						int j;

						for(j=0; j<hubArrList1.size();j++)	
						{
							if((((hubArrList1.get(j)/scaleHub1)-(hubArrList2.get(j)/scaleHub)) > Math.pow(10, -5)) || (((authCompList1.get(j)/scaleAuthority1)-(authCompList2.get(j)/scaleAuthority)) > Math.pow(10, -5)))
							{
								if(i<10)
									System.out.print("Iter :  "+i+ " :");
								else if(i>9)
									System.out.print("Iter : "+i+ " :");
								scaleMatrixPrint0218(scaleAuthority,scaleHub);
								System.out.println();
								break;
							}
							else if(j==hubArrList1.size()-1)
							{
								if(i<10)
									System.out.print("Iter :  "+i+ " :");
								else if(i>9)
									System.out.print("Iter : "+i+ " :");
								scaleMatrixPrint0218(scaleAuthority,scaleHub);
								System.out.println();
								return;
							}
						}
					}					
					i++;
				}
			}
		}
		else if(noOfvertices>10)
		{
			computeInitialValues0218(-1);
			i = 0;
			ArrayList<Double> hubArrList1 = new ArrayList<>();
			ArrayList<Double> authCompList1 = new ArrayList<>();
			ArrayList<Double> hubArrList2 = new ArrayList<>();
			ArrayList<Double> authCompList2 = new ArrayList<>();
			double scaleHub1 = 1, scaleAuthority1 = 1;
			while(true)
			{
				hubArrList1 = doubMatrixToArrList0218(hub);
				authCompList1 = doubMatrixToArrList0218(authority);
				scaleHub1 = scaleHub;
				scaleAuthority1 = scaleAuthority;
				lTranspose = calculateMatrixTranspose0218(adjMatrix);
				authority = calculateMatrixMultiplication0218(lTranspose, hub);
				authCompList2 = doubMatrixToArrList0218(authority);
				hub = calculateMatrixMultiplication0218(adjMatrix, authority);
				hubArrList2 = doubMatrixToArrList0218(hub);
				scaleHub = calculateScaleValue0218(hub);
				scaleAuthority = calculateScaleValue0218(authority);
				if(i>0)
				{
					int j;

					for(j=0; j<hubArrList1.size();j++)	
					{
						if((((hubArrList1.get(j)/scaleHub1)-(hubArrList2.get(j)/scaleHub)) > Math.pow(10, -5)) || (((authCompList1.get(j)/scaleAuthority1)-(authCompList2.get(j)/scaleAuthority)) > Math.pow(10, -5)))
						{
							break;
						}
						if(j==hubArrList1.size()-1)
						{
							System.out.println(" Iter  : "+i);
							scaleMatrixPrintForLargeGraph0218(scaleAuthority,scaleHub);
							System.out.println();
							return;
						}
					}
				}
				i++;
			}
		}
	}
}

public class hits0218 {

	public static void main(String[] args) {
		int initialValue, iterations;
		String filename;
		HitsAlgorithm0218 h = new HitsAlgorithm0218();
		iterations = Integer.valueOf(args[0]);
		initialValue = Integer.valueOf(args[1]);
		filename = args[2];
		h.hitsImplementation0218(iterations, initialValue, filename);
	}
}

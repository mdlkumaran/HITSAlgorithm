import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

class HitsAlgorithm
{
	int noOfvertices;
	int noOfEdges;
	double[][] hub;
	double[][] authority;
	double[][] adjMatrix;

	public void calculateNoOfVeticesEdges(String fileName)
	{
		String currentLine;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//					while((currentLine = br.readLine())!= null)
			currentLine = br.readLine();

			noOfvertices = Integer.valueOf(currentLine.substring(0, 1));
			noOfEdges = Integer.valueOf(currentLine.substring(2, 3));

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void initializeAdjMatrixArray(int noOfVertices)
	{

		noOfVertices = this.noOfvertices;

		hub = new double[noOfvertices][1];
		authority = new double[noOfvertices][1];
		adjMatrix = new double[noOfvertices][noOfvertices];
	}

	public void displayArray(double[][] a)
	{
		for (int row = 0; row < a.length; row ++)
			for (int col = 0; col < a[row].length; col++)
			{
				System.out.println("a["+ row+"]["+col+"]" + a[row][col]);
			}
	}

	public void computeInitialValues(double initialValue)
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

	public void initializeAdjMatrixValues(String fileName)
	{
		String currentLine;
		int edgeI;
		int edgeJ;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			while((currentLine = br.readLine())!= null)
			{
				edgeI = Integer.valueOf(currentLine.substring(0, 1));
				edgeJ = Integer.valueOf(currentLine.substring(2, 3));
				//				System.out.println("edgeI:"+edgeI);
				//				System.out.println("edgeJ:"+edgeJ);
				adjMatrix[edgeI][edgeJ] = 1;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public double[][] calculateMatrixMultiplication(double[][] A, double[][] B)
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

	public double[][] calculateMatrixTranspose(double[][] A)
	{
		int m = A.length;
		int n = A[0].length;
		double[][] C = new double[n][m];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				C[j][i] = A[i][j];
		return C;
	}

	public double calculateScaleValue(double[][] arr)
	{
		double[][] C;
		double calc = 0;
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				calc += arr[row][col] * arr[row][col];
			}
		double scale = Math.sqrt(calc);
		//		System.out.println("calc:"+calc);
		return scale;
	}

	public void scaleMatrixPrint(double scaleAuthority, double scaleHub)
	{
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				System.out.print(" A/H["+ row + "]="); 
				System.out.printf("%.5f", authority[row][col]/scaleAuthority); 
				System.out.print("/");
				System.out.printf("%.5f",hub[row][col]/scaleHub);

			}
	}
	
	public void scaleMatrixCompare(double scaleAuthority, double scaleHub)
	{
		int i=0;
		double authVal,hubVal = 0.0;
		ArrayList<Double> compareAuthArrayList = new ArrayList<>();
		ArrayList<Double> compareHubArrayList = new ArrayList<>();
		for (int row = 0; row < noOfvertices; row ++)
			for (int col = 0; col < 1; col++)
			{
				authVal = authority[row][col]/scaleAuthority;
				compareAuthArrayList.set(i, authVal); 
				hubVal = hub[row][col]/scaleHub;
				compareHubArrayList.set(i, hubVal);
				i++;
			}
	}
	
	public void hitsImplementation(int iterationValue, int initialValue, String fileName)
	{
		int i = 1;
		double[][] lTranspose;
		int iter;
		double scaleHub, scaleAuthority;
		calculateNoOfVeticesEdges(fileName);
		initializeAdjMatrixArray(noOfvertices);
		computeInitialValues(initialValue);
		initializeAdjMatrixValues(fileName);
		//		iter = noOfvertices;
		if(noOfvertices<10)
		{
			if(iterationValue>0)
			{
				while(i<=iterationValue)
				{
					System.out.println("i"+i);
					//				System.out.println("adjMatrix");
					//				displayArray(adjMatrix);
					//				System.out.println("authorityMatrix");
					//				displayArray(authority);
					//				System.out.println("hubMatrix");
					//				displayArray(hub);
					lTranspose = calculateMatrixTranspose(adjMatrix);
					//				System.out.println("lTranspose");
					//				displayArray(lTranspose);
					authority = calculateMatrixMultiplication(lTranspose, hub);
					//				System.out.println("authority:");
					//				displayArray(authority);
					hub = calculateMatrixMultiplication(adjMatrix, authority);
					//				System.out.println("hub:");
					//				displayArray(hub);
					scaleHub = calculateScaleValue(hub);
					//				System.out.println("scaleHub:"+scaleHub);
					scaleAuthority = calculateScaleValue(authority);
					//				System.out.println("scaleAuthority:"+scaleAuthority);
					scaleMatrixPrint(scaleAuthority,scaleHub);
					//			System.out.print("Iterat: " + i);

					//			System.out.printf("%.2f", val);
					i++;
					//			System.out.println();
				}
			}
			else if(iterationValue == 0)
			{
				
//				while(true)
	//			{
					System.out.println("i"+i);
									System.out.println("adjMatrix");
									displayArray(adjMatrix);
									System.out.println("authorityMatrix");
									displayArray(authority);
									System.out.println("hubMatrix");
									displayArray(hub);
					lTranspose = calculateMatrixTranspose(adjMatrix);
									System.out.println("lTranspose");
									displayArray(lTranspose);
					authority = calculateMatrixMultiplication(lTranspose, hub);
									System.out.println("authority:");
									displayArray(authority);
					hub = calculateMatrixMultiplication(adjMatrix, authority);
									System.out.println("hub:");
									displayArray(hub);
					scaleHub = calculateScaleValue(hub);
									System.out.println("scaleHub:"+scaleHub);
					scaleAuthority = calculateScaleValue(authority);
									System.out.println("scaleAuthority:"+scaleAuthority);
									
//					scaleMatrixPrint(scaleAuthority,scaleHub);
								System.out.print("Iterat: " + i);

					i++;
								System.out.println();
				//}
			}
		}
		else if(noOfvertices>10)
		{
			
		}
	}
}

public class Hits {

	public static void main(String[] args) {
		// TODO Auto-generaHted method stub
		int initialValue, noVertices;
		String filename;
		HitsAlgorithm h = new HitsAlgorithm();
		//		h.computeInitialValues(0.2);
		//		h.calculateNoOfVeticesEdges(args[2]);
		//		h.initializeAdjMatrixArray(4);
		//		h.initializeAdjMatrixValues(args[2]);
		initialValue = Integer.valueOf(args[0]);
		noVertices = Integer.valueOf(args[1]);
		filename = args[2];
		h.hitsImplementation(initialValue, noVertices, filename);
	}

}

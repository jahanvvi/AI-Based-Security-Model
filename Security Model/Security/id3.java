package Security;
import Security.id3.TreeNode;
class id3 {
	class TreeNode {
		TreeNode[] children;
		int value;

		public TreeNode(TreeNode[] ch, int val) {
			value = val;
			children = ch;
		}

		public String toString() {
			return toString("");
		}

		String toString(String indent) {
			if (children != null) {
				String s = "";
				for (int i = 0; i < children.length; i++)
					s += indent + data[0][value] + "=" + strings[value][i] + "\n" + children[i].toString(indent + '\t');
				return s;
			} else
				return indent + "Class: " + strings[attributes - 1][value] + "\n";
		}
	}

	public String used;
	public int attributes; // Number of attributes (including the class)
	public int examples; // Number of training examples
	public TreeNode decisionTree; // Tree learnt in training, used for classifying
	public String[][] data; // Training data indexed by example, attribute
	public String[][] strings; // Unique strings for each attribute
	public int[] stringCount; // Number of unique strings for each attribute

	public id3() {
		used = "used";
		attributes = 0;
		examples = 0;
		decisionTree = null;
		data = null;
		strings = null;
		stringCount = null;
	} // constructor

	
	 public void printTree() {
		 if (decisionTree == null)
	 error("Attempted to print null Tree"); 
		 else 
			 System.out.print(decisionTree); 
		 }
	 //printTree()
	 
	/** Print error message and exit. **/
	static void error(String msg) {
		System.err.print("Error: " + msg);
		System.exit(1);
	} // error()

	static final double LOG2 = Math.log(2.0);

	static double xlogx(double x) {
		return x == 0 ? 0 : x * Math.log(x) / LOG2;
	}
	public void train(String[][] trainingData) {
		indexStrings(trainingData);
		String[] usedAttributes = data[0].clone(); // Get all the headers or rather attribute names
		decisionTree = new TreeNode(null, 0);
		buildTree(decisionTree, trainingData, usedAttributes);
	}
	public String transverse(TreeNode currentNode, String[] row) {

		if (currentNode.children == null) {

			return strings[attributes - 1][currentNode.value].toString();
		} else {
			int posInStrings = -1;
			for (int i = 0; i < strings[currentNode.value].length; i++) {
				if (row[currentNode.value].equals(strings[currentNode.value][i])) {
					posInStrings = i;
				}

			}

			return transverse(currentNode.children[posInStrings], row);

		}

	}
	boolean checkUsedAttributes(String[] attrCol) {
		int attrCounter = 0;
		for (int i = 0; i < attrCol.length - 1; i++) {
			if (attrCol[i].equals(used)) {
				attrCounter++;
			}

		}
		if (attrCounter == attrCol.length - 1) {
			return true;
		} else {
			return false;
		}

	}
	public String[][] getSubset(String[][] currentDataSet, int attr, int attrVal) {
		int attrCounter = countAttributes(currentDataSet, attr, attrVal);
		// Again we don't want a class "attribute" column
		String[][] subSet = new String[attrCounter + 1][currentDataSet[0].length - 1];
		int rowCount = 1;
		int rows = currentDataSet.length;
		subSet[0] = currentDataSet[0];
		for (int i = 1; i < rows; i++) {
			if (currentDataSet[i][attr].equals(strings[attr][attrVal])) {
				subSet[rowCount] = currentDataSet[i];
				rowCount++;
			}
		}
		return subSet;

	}
	public void buildTree(TreeNode node, String[][] currentDataSet, String[] usedAttributes) {
		// Calculate the root entropy
		double rootEntropy = calcEntropy(currentDataSet);
		double rows = examples - 1;
		double comparator = 0;
		int bestAttribute = 0;
		double[] infoGain = new double[attributes];
		double[] subSetEntropy;
		double[] instanceCount;

		// most common attribute in the subset
		if (rootEntropy <= 0.0 || checkUsedAttributes(usedAttributes)) {
			int leafClass = 0;
			int instances = 0;
			for (int z = 0; z < stringCount[attributes - 1]; z++) {
				if (instances < countAttributes(currentDataSet, currentDataSet[0].length - 1, z)) {
					instances = countAttributes(currentDataSet, currentDataSet[0].length - 1, z);
					leafClass = z;
				}
			}
			node.value = leafClass;
			return;
		} else {

			for (int i = 0; i < currentDataSet[0].length - 1; i++) {
				if (usedAttributes[i].equals(used)) {

					infoGain[i] = 0;
				} else {

					subSetEntropy = new double[stringCount[i]];
					instanceCount = new double[stringCount[i]];
					for (int j = 0; j < stringCount[i]; j++) {

						String[][] subSet = getSubset(currentDataSet, i, j);
						subSetEntropy[j] = calcEntropy(subSet);
						instanceCount[j] = countAttributes(subSet, i, j);
					}

					infoGain[i] = rootEntropy;
					double tmp = 0;
					for (int a = 0; a < subSetEntropy.length; a++) {

						tmp = (instanceCount[a] / rows * subSetEntropy[a]);
						if (!Double.isNaN(tmp)) {
							infoGain[i] -= tmp;
						}
					}
					infoGain[i] = Math.abs(infoGain[i]);

					if (infoGain[i] >= comparator && !usedAttributes[i].equals(used)) {
						comparator = infoGain[i];
						bestAttribute = i;
					}
				}
			}
			node.value = bestAttribute;
			node.children = new TreeNode[stringCount[bestAttribute]];

			for (int n = 0; n < stringCount[bestAttribute]; n++) {
				String[] temp = usedAttributes.clone();
				String[][] newSubSet = getSubset(currentDataSet, bestAttribute, n);
				node.children[n] = new TreeNode(null, 0);
				if (newSubSet.length != 1) {
					temp[bestAttribute] = used;
					buildTree(node.children[n], newSubSet, temp);

				} else {

					for (int m = 0; m < temp.length - 1; m++) {
						temp[m] = used;
					}
					buildTree(node.children[n], currentDataSet, temp);
				}
			}
		}

	}
	public double calcEntropy(String[][] currentDataSet) {
		double rows = currentDataSet.length - 1;
		double[] noClassInstances = new double[stringCount[attributes - 1]];
		for (int i = 0; i < stringCount[attributes - 1]; i++) {
			noClassInstances[i] = countAttributes(currentDataSet, attributes - 1, i);
		}

		double entropy = -xlogx(noClassInstances[0] / rows);
		for (int a = 1; a < noClassInstances.length; a++) {
			entropy -= (xlogx(noClassInstances[a] / rows));
		}
		return Math.abs(entropy);
	}
	public int countAttributes(String[][] currentDataSet, int attr, int attrVal) {
		int count = 0;
		if (currentDataSet.length == 1) {
			return count;
		}

		for (int i = 1; i < currentDataSet.length; i++) {
			if (currentDataSet[i][attr].equals(strings[attr][attrVal])) {
				count++;
			}
		}
		return count;
	}
	void indexStrings(String[][] inputData) {
		data = inputData;
		examples = data.length;
		attributes = data[0].length;
		stringCount = new int[attributes];
		strings= new String[attributes][examples];
		int index = 0;
		for (int attr = 0; attr < attributes; attr++) {
			stringCount[attr] = 0;
			for (int ex = 1; ex < examples; ex++) {
				for (index = 0; index < stringCount[attr]; index++)
					if (data[ex][attr].equals(strings[attr][index]))
						break;
				if (index == stringCount[attr])
					strings[attr][stringCount[attr]++] = data[ex][attr];
			}
		}
	}

	public String classify(String[][] testData, String name,int s) {
		if (decisionTree == null) {
			error("Please run training phase before classification");
		}
		
		String ans = transverse(decisionTree, testData[s]);
		return ans.toString();

	}
}
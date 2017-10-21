
// Ron Altboum
// Amit Yaakobovich

public class TrinaryHeap
{
	
	public HeapNode root;
	public int size;
	public int min;
	
	public TrinaryHeap() {
		root = null; // creates an empty root linked list
		min=Integer.MAX_VALUE;
		setSize(0);
	  }

	public TrinaryHeap(HeapNode origin){
		HeapNode newRoot= origin.MostLeftChild;
		this.setRoot(newRoot);
		this.setSize((int)(Math.pow(3, origin.rank))-1);
		this.setMin(findMinOfNewRoots(newRoot)); 
	}
	
		

/**
    * public boolean empty()
    *
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean empty(){
    	if (this.root== null)
    		return true;
    	else
    		return false;
    }
		
   /**
    * public int insert(int value)
    * Insert value into the heap 
    * Return number of linking operations during this operation.
    *
    */
    public int insert(int value) 
    {
    	HeapNode newRoot= new HeapNode(value);
    	if (this.getRoot()==null)
    		this.setRoot(newRoot);
    	else{
    		HeapNode lastRoot=this.getRoot();
    		this.setRoot(newRoot);
    		this.getRoot().RightSibling=lastRoot;
    	}
    	int numOfLinks;
    	TrinaryHeap newHeap= new TrinaryHeap();
    	numOfLinks= this.meld(newHeap);
    	this.setSize(this.getSize()+1);
    	return numOfLinks; 
    	
    }

   /**
    * public int deleteMin()
    *
    * Delete the minimum value or do nothing if heap is empty.
    * Return number of linking operations during this operation.
    *
    */
    public int deleteMin()
    {
    	int NumOfLinkings=0;
    	if (this.empty()) // heap is empty
    		return 0;
    	else if (this.getSize()==1){ //heap has only one Node
    		this.setRoot(null);
    		this.setSize(0);
    		this.setMin(Integer.MAX_VALUE);
    		return 0;
    	}
    	HeapNode originalRoot= this.getRoot();
    	HeapNode MinimalNode=findMinRoot(originalRoot, this.getMin());
    	HeapNode BeforeMinimalNode=findNodeBeforeMinRoot
    			                   (originalRoot,MinimalNode);
    	
     	if (originalRoot==MinimalNode){ //root is the minimum
     		if (MinimalNode.RightSibling==null){ //heap has only one root
     			this.setRoot(MinimalNode.MostLeftChild);
     			this.setSize(this.getSize()-1);
     			this.setMin(findMinOfNewRoots(MinimalNode.MostLeftChild));
     			MinimalNode.MostLeftChild=null;
     			MinimalNode.RightestChild=null;
     			return 0;
     		}
     		else{ //MinimalNode is root and has RightSibling
     			if (MinimalNode.rank==0){
     				this.setSize(this.getSize()-1);
     				this.setMin(findMinOfNewRoots
     						(MinimalNode.RightSibling));
     				this.setRoot(MinimalNode.RightSibling);
     				MinimalNode.RightSibling=null;
     				return 0;
     			}
     			else{ //MinimalNode is root and has RightSibling 
     				  //and have childs
     				this.setSize(this.getSize()-((int)Math.pow(3,MinimalNode.rank)));
     				this.setMin(findMinOfNewRoots(MinimalNode.RightSibling));
     				this.setRoot(MinimalNode.RightSibling);
     			}
     		}
     	}
     	else{ //minimum is not the root
     		BeforeMinimalNode.RightSibling=MinimalNode.RightSibling;
     		if (MinimalNode.rank==0){ //minimum has no childs
 				this.setSize(this.getSize()-1);
 				this.setMin(findMinOfNewRoots(this.root));
 				MinimalNode.RightSibling=null;
 				return 0;
     		}
     		else{ //minimum has childs
 				this.setSize(this.getSize()-((int)Math.pow(3,MinimalNode.rank)));
 				this.setMin(findMinOfNewRoots(this.root));
     		}
     	}
     	TrinaryHeap newHeap= new TrinaryHeap(MinimalNode);
     	MinimalNode.RightSibling=null;
     	MinimalNode.MostLeftChild=null;
     	MinimalNode.RightestChild=null;
		NumOfLinkings=this.meld(newHeap);
		return NumOfLinkings;
    }

   /**
    * public int findMin()
    *
    * Return the minimum value or -1 if heap is empty.
    *
    */
    public int findMin()
    {
    	if (this.empty()==true)
    		return -1;
    	return this.min;
    } 
    
   /**
    * public int meld (TrinaryHeap heap2)
    * Meld the heap with heap2
    * Return number of linking operations during this operation.
    */
    public int meld (TrinaryHeap heap2)
    {
    	if((this.empty()==true)&&(heap2.empty()==true))
    		return 0;
    	if(this.empty()==true){
    		this.setRoot(heap2.getRoot());
    		this.setSize(heap2.getSize());
    		this.setMin(heap2.getMin());
    		return 0;
    	}
    	int min1=this.min; //begin to define new Min for heap
    	int min2=heap2.min;
    	int final_min;
    	if (min1<min2)
    		final_min=min1;
    	else
    		final_min=min2; //end of define new Min for heap
	    TrinaryHeap heap = new TrinaryHeap();
		heap.min=final_min;
	    heap.setSize(this.getSize()+heap2.getSize()); 
	    if (heap2.empty()==true)
	    	heap=this;
	    else if (this!=heap2)
		    heap.setRoot(arrangeTreesByRank(this, heap2));
	    else if (this==heap2)
	    	 heap.setRoot(arrangeEqualTreesByRank(this));
    	int linkCnt=0; //num of linking
	    HeapNode heapRoot= heap.getRoot();
	    HeapNode preTemp = null;
	    HeapNode temp = heapRoot;
	    HeapNode temp_Right = temp.RightSibling;
	    HeapNode temp_Second_Right =null;
	    if(temp_Right!=null)
	    	temp_Second_Right = temp_Right.RightSibling;
	    HeapNode refLinking;	    
	    while(temp_Second_Right!= null){//beginning of while
	    	if (temp.rank!=temp_Second_Right.rank){
	    		preTemp=temp;
	    		temp=temp_Right;
	    		temp_Right=temp_Second_Right;
	    		temp_Second_Right=temp_Second_Right.RightSibling;
	    	}
	    	else{
	    		if(temp_Second_Right.RightSibling==null){ //1,2,3 have same rank
	    			// and 4 is null
	    			refLinking= linking(temp,temp_Right,temp_Second_Right);
	    			if (preTemp!=null)
	    				preTemp.RightSibling= refLinking;
	    			else if (preTemp==null)
	    				heap.setRoot(refLinking); 
	    			refLinking.RightSibling=null;	
	    			linkCnt++;
	    			this.setRoot(heap.getRoot()); 
	    			this.setSize(heap.getSize()); 
	    			this.min= heap.min;
	    			return linkCnt;
	    		}
	    		else if (temp_Second_Right.RightSibling.rank!= temp.rank){
	    			HeapNode after_ref=temp_Second_Right.RightSibling;
	    			//1,2,3 have same rank, 4 isn't null and 4 has different rank
	    			refLinking= linking(temp,temp_Right,temp_Second_Right);
	    			refLinking.RightSibling=after_ref;
	    			if (preTemp!=null){
	    				preTemp.RightSibling= refLinking;
	    				temp=preTemp.RightSibling;
	    				temp_Right=temp.RightSibling;
	    				temp_Second_Right = temp_Right.RightSibling;
	    			}
	    			else if (preTemp==null){
	    				heap.setRoot(refLinking);
	    				temp=heap.getRoot();
	    				temp_Right=temp.RightSibling;
	    				temp_Second_Right = temp_Right.RightSibling;
	    				}
	    			linkCnt++; 	
	    		}
	    		else if (temp_Second_Right.RightSibling.rank== temp.rank){
	    			
	    			// above is general case of 1 to 4 have same rank 
	    			if(temp_Second_Right.RightSibling.RightSibling==null){
	    				//a subcase when the fifth is null
	    				refLinking= linking
	    						(temp_Right,temp_Second_Right,temp_Second_Right.RightSibling);
	    				refLinking.RightSibling=null;
	    				if (preTemp!=null)
	    					temp.RightSibling= refLinking;	
	    				else if (preTemp==null){
	    					temp.RightSibling= refLinking;
	    					heap.setRoot(temp);	
	    				}
	    				linkCnt++;
	    				this.setRoot(heap.getRoot()); 
	    				this.setSize(heap.getSize());
	    				this.min=heap.min;
	    				return linkCnt;
	    			}
	    			else if(temp_Second_Right.RightSibling.RightSibling.rank
	    					!=temp.rank){
	    				// 1 and 5 have diff rank
	    				HeapNode fifth=temp_Second_Right.RightSibling.RightSibling;
	    				refLinking= linking
	    						(temp_Right,temp_Second_Right,temp_Second_Right.RightSibling);
	    				refLinking.RightSibling=fifth;
	    				if (preTemp!=null){
	    					preTemp=temp;
	    					preTemp.RightSibling= refLinking;
	    					preTemp.RightSibling.RightSibling=fifth;
	    					temp=preTemp.RightSibling;
	    					temp_Right=temp.RightSibling;
		    				temp_Second_Right = temp_Right.RightSibling;
	    				}
	    				else if (preTemp==null){
	    					preTemp=temp;
	    					heap.setRoot(preTemp);
	    					preTemp.RightSibling= refLinking;
	    					preTemp.RightSibling.RightSibling=fifth;
	    					temp=preTemp.RightSibling;
	    					temp_Right=temp.RightSibling;
		    				temp_Second_Right = temp_Right.RightSibling;
	    				}
	    				linkCnt++;
	    			}
	    			else if(temp_Second_Right.RightSibling.RightSibling.rank== temp.rank){
	    				
	    				//1 to 5 have same rank
	    				if(temp_Second_Right.RightSibling.RightSibling.RightSibling==null){
	    					refLinking= linking(temp_Second_Right,temp_Second_Right.RightSibling,
	    							temp_Second_Right.RightSibling.RightSibling);
	    					refLinking.RightSibling=null;
	    					if (preTemp!=null){
	    						temp_Right.RightSibling=refLinking;
	    					}
	    					else if(preTemp==null){
	    						temp_Right.RightSibling= refLinking;
	    						heap.setRoot(temp);	
	    					}
	    					linkCnt++;
	    					this.setRoot(heap.getRoot()); 
	    					this.setSize(heap.getSize());
	    					this.min=heap.min;
		    				return linkCnt;
	    				}
	    				else{  // 6th isn't null. notice that it's not possible that
	    					   // 1 and 6 have same rank
	    					HeapNode sixth=temp_Second_Right.RightSibling.RightSibling.RightSibling;
	    					refLinking= linking
	    							(temp_Second_Right,temp_Second_Right.RightSibling,
	    							temp_Second_Right.RightSibling.RightSibling);
	    					refLinking.RightSibling=sixth;
	    					if (preTemp!=null){
	    						preTemp=temp_Right;
	    						preTemp.RightSibling= refLinking;
	    						preTemp.RightSibling.RightSibling=sixth;
	    						temp=preTemp.RightSibling;
		    					temp_Right=temp.RightSibling;
			    				temp_Second_Right = temp_Right.RightSibling;
	    					}
	    					else if(preTemp==null){
	    						preTemp=temp_Right;
	    						heap.setRoot(temp);
	    						preTemp.RightSibling= refLinking;
	    						preTemp.RightSibling.RightSibling=sixth;
	    						temp=preTemp.RightSibling;
		    					temp_Right=temp.RightSibling;
			    				temp_Second_Right = temp_Right.RightSibling;
	    					}
	    					linkCnt++;
	    				}
	    				
	    			}
	    		}
	    	}	
	    }//ending of while
	    this.setRoot(heap.getRoot());  
	    this.setSize(heap.getSize());
	    this.min=heap.min;
	    return linkCnt;
    }
    
   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.getSize(); 
    }
    
   /**
    * public int[] treesSize()
    *
    * Return an array containing the sizes of the trees that represent the heap
    * in ascending order.
    * 
    */
    public int[] treesSize()
    {
    	if (this.empty())
    		return new int[0];
        HeapNode Node=this.getRoot();
        int length=0;
        while (Node!=null){
        	length++;
        	Node=Node.RightSibling;
        }
        int[] arr=new int[length];
        int index=0;
        Node=this.getRoot();
        while (Node!=null){
        	arr[index]=(int)Math.pow(3,Node.rank);
        	index++;
        	Node=Node.RightSibling;
        }
        return arr;
    }

   /**
    * public int arrayToHeap()
    *
    * Insert the array to the heap. Delete previous elements in the heap.
    * Return number of linking operations during this operation.
    * 
    */
    public int arrayToHeap(int[] array)
    {
    	if(array.length==0){
    		this.setRoot(null);
    		return 0;
    	}
		TrinaryHeap newTHeap= new TrinaryHeap();
		int linkSum= 0;
		for (int i=0;i<array.length;i++) {
			linkSum= linkSum+ newTHeap.insert(array[i]);
		}
		this.setRoot(newTHeap.getRoot());
		this.setSize(newTHeap.getSize());
		this.setMin(newTHeap.getMin());
        return linkSum; 
    }
	
	   /**
    * public static int sortArray()
    *
    * Sort the given array using heapsort.
    * Return number of linking operations during this operation.
    * 
    */
    public static int sortArray(int[] array)
    {
    	if(array.length==0)
    		return 0;
    	TrinaryHeap newTHeap= new TrinaryHeap();
    	int linkSum=0;
    	for (int i = 0; i < array.length; i++) {
    		linkSum= linkSum+ newTHeap.insert(array[i]);
		}
    	
    	for (int i=0;i<array.length;i++){
    		array[i]=newTHeap.findMin();
    		linkSum=linkSum+newTHeap.deleteMin();
    		}

        return linkSum; 
    }
	
   /**
    * public boolean isHeap()
    *
    * Returns true if and only if the heap is valid.
    *   
    */
    public boolean isHeap() 
    {
    	HeapNode Node=this.getRoot();
    	while (Node!=null){
    		if (isThere_A_Smaller_KeyThanTheRoot(Node)==true)// if there is
 //a smaller key than the root, then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	Node=this.getRoot();
    	while (Node!=null){
    		if (IsThereAnOddNumOfChilds(Node)==true)
    			//if a Node has an odd number of childs, 
    			//then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	Node=this.getRoot();
    	while (Node!=null){
    		if (isThereABiggerChildRank(Node)==true)
    			//if a Node has a Child with equal or bigger rank, 
    			//then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	Node=this.getRoot();
    	while (Node!=null){
    		if ((IsRanksNotOrderedToLeft(Node)==true)&&
    		(IsRanksNotOrderedToRight(Node)==true))
    			//if ranks of childs is not in a certain order, 
    			//then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	Node=this.getRoot();
    	if (AreThere3RootsWithSameRank(Node)==true)
    		//if there are 3 Roots with same rank, 
			//then the heap isn't correct
    		return false;
    	
    	Node=this.getRoot();
    	while (Node!=null){
    		if (AreTotalChildsRanksNumNotLegal(Node)==true)
    			//if there are 3 Childs with same rank, 
    			//then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	Node=this.getRoot();
    	while (Node!=null){
    		if (isHeightDiffThanRank(Node)==true)
    			//if Node height is not equal to his rank, 
    			//then the heap isn't correct
    			return false;
    		else
    			Node=Node.RightSibling;
    	}
    	
    	return true;
    }
    
    public HeapNode getRoot(){
    	return this.root;
    }
    public void setRoot(HeapNode newRoot){
    	this.root= newRoot;
    }
    
    public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getMin(){
		return this.min;
	}
	
	public void setMin(int value){
		this.min=value;
	}

	public HeapNode getMinOf3Roots(HeapNode heapNode1,HeapNode heapNode2,HeapNode heapNode3){
		if ((heapNode1.minNode<=heapNode2.minNode)&&(heapNode1.minNode<=heapNode3.minNode))
			return heapNode1;
		else if ((heapNode2.minNode<=heapNode1.minNode)&&(heapNode2.minNode<=heapNode3.minNode))
			return heapNode2;
		else if((heapNode3.minNode<=heapNode1.minNode)&&(heapNode3.minNode<=heapNode2.minNode))
			return heapNode3;
		return heapNode1;
	}
    
    public  HeapNode getMiddleRoot(HeapNode heapNode1,HeapNode heapNode2,HeapNode heapNode3){
    	if (heapNode1==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode2.minNode<=heapNode3.minNode)
    			return heapNode2;
    		else
    			return heapNode3;
    	}
    	else if (heapNode2==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode1.minNode<=heapNode3.minNode)
    			return heapNode1;
    		else
    			return heapNode3;
    	}
    	else if (heapNode3==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode1.minNode<=heapNode2.minNode)
    			return heapNode1;
    		else
    			return heapNode2;
    	}
    	return heapNode1; //dummy return, program should never get here!
    }
    public  HeapNode getMaxRoot(HeapNode heapNode1,HeapNode heapNode2,HeapNode heapNode3){
    	if (heapNode1==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode2==getMiddleRoot(heapNode1,heapNode2,heapNode3))
    			return heapNode3;
    		else
    			return heapNode2;
    	}
    	else if (heapNode2==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode1==getMiddleRoot(heapNode1,heapNode2,heapNode3))
    			return heapNode3;
    		else
    			return heapNode1;
    	}
    	else if (heapNode3==getMinOf3Roots(heapNode1,heapNode2,heapNode3)){
    		if (heapNode1==getMiddleRoot(heapNode1,heapNode2,heapNode3))
    			return heapNode2;
    		else
    			return heapNode1;
    	}
    	return heapNode1;//dummy return, program should never get here!
    }
    
    public HeapNode linking(HeapNode heapNode1,HeapNode heapNode2,HeapNode heapNode3){
    	HeapNode new_heapNode=getMinOf3Roots(heapNode1,heapNode2,heapNode3);
    	HeapNode midNode= getMiddleRoot(heapNode1, heapNode2, heapNode3);
    	HeapNode maxNode= getMaxRoot(heapNode1, heapNode2, heapNode3);
    	if (new_heapNode.rank==0){
    		new_heapNode.MostLeftChild=midNode;
    		new_heapNode.RightestChild=maxNode;
    		new_heapNode.MostLeftChild.RightSibling=new_heapNode.RightestChild;
    		new_heapNode.RightestChild.RightSibling=null;
    		new_heapNode.MostLeftChild.parent=new_heapNode;
    		new_heapNode.RightestChild.parent=new_heapNode;
    	}
    	else{
    		new_heapNode.RightestChild.RightSibling=midNode;//oldest rightest
    		new_heapNode.RightestChild.RightSibling.parent=new_heapNode;//oldest rightest
    		new_heapNode.RightestChild.RightSibling.RightSibling=maxNode;//oldest rightest
    		new_heapNode.RightestChild=new_heapNode.RightestChild.RightSibling.RightSibling;//new rightest
    		new_heapNode.RightestChild.parent=new_heapNode;
    		new_heapNode.RightestChild.RightSibling=null;
    	}
    	new_heapNode.rank++;
    	if (new_heapNode.getKey()<this.min)
    		this.min=new_heapNode.getKey();
    	return new_heapNode;
    }
    
    //below is a utility function for meld
    private HeapNode arrangeTreesByRank(TrinaryHeap HeapNode1, TrinaryHeap HeapNode2) { ///NOTICE THAT IT RETURNS
    	
    	HeapNode lastNode; 
    	HeapNode root;
        HeapNode HeapNode1Next = HeapNode1.getRoot();
        HeapNode HeapNode2Next = HeapNode2.getRoot(); 
        
        if (HeapNode1.getRoot().rank <= HeapNode2.getRoot().rank) {
          root = HeapNode1.getRoot();
          HeapNode1Next = HeapNode1Next.RightSibling;
        }
        else {
          root = HeapNode2.getRoot();
          HeapNode2Next = HeapNode2Next.RightSibling;
        }
        
        lastNode = root;
        
        while (HeapNode1Next != null && HeapNode2Next != null) {
          if (HeapNode1Next.rank <= HeapNode2Next.rank) {
            lastNode.RightSibling = HeapNode1Next;
            HeapNode1Next = HeapNode1Next.RightSibling;
          } 
          else {
            lastNode.RightSibling = HeapNode2Next;
            HeapNode2Next = HeapNode2Next.RightSibling;
          }
          
          lastNode = lastNode.RightSibling;
        }
        
        if (HeapNode1Next != null) 
          lastNode.RightSibling = HeapNode1Next;
        else  // the case when HeapNode2Next != null && HeapNode1Next == null
          lastNode.RightSibling = HeapNode2Next; 
        return root; 
      
    }
    
    private HeapNode arrangeEqualTreesByRank(TrinaryHeap heap){
    	HeapNode Node=heap.getRoot();
    	HeapNode Next_Node=Node.RightSibling;
    	HeapNode new_root=Node;
    	HeapNode nextToRoot=CreateSimilarNode(new_root);
    	new_root.RightSibling=nextToRoot;
    	HeapNode temp=nextToRoot;
    	Node=Next_Node;
    	if (Next_Node!=null)
    		Next_Node=Next_Node.RightSibling;
    	while (Node!=null){
    		temp.RightSibling=Node;
    		HeapNode Next=CreateSimilarNode(Node);
    		temp.RightSibling.RightSibling=Next;
    		temp=temp.RightSibling.RightSibling;
    		Node=Next_Node;
        	if (Next_Node!=null)
        		Next_Node=Next_Node.RightSibling;
    	}
    	return new_root;
    }
    
    public HeapNode CreateSimilarNode(HeapNode Node){
    	HeapNode New_Node=new HeapNode(Node.getKey());
    	New_Node.RightSibling=null;
    	New_Node.MostLeftChild=Node.MostLeftChild;
    	New_Node.parent=null;
    	New_Node.RightestChild=Node.RightestChild;
    	New_Node.rank=Node.rank;
    	New_Node.minNode=Node.minNode;
    	return New_Node;
    }
    
    public boolean isThere_A_Smaller_KeyThanTheRoot(HeapNode Node){
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		if (Child.MostLeftChild!=null)
    			return isThere_A_Smaller_KeyThanTheRoot(Child);
    		if (Child.getKey()<Child.parent.getKey())
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public boolean IsThereAnOddNumOfChilds(HeapNode Node){
    	if (numOfChilds(Node)%2==1)
    		return true;
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		if (Child.MostLeftChild!=null)
    			return IsThereAnOddNumOfChilds(Child);
    		Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public int numOfChilds(HeapNode Node){
    	int sum=0;
    	if (Node.MostLeftChild==null)
    		return 0;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		sum++;
    		Child=Child.RightSibling;
    	}
    	return sum;
    }
    
    public int Rank(HeapNode Node){ //For IsHeap method!!!
    	return numOfChilds(Node)/2;
    }
    
    public boolean isThereABiggerChildRank(HeapNode Node){
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		if (Child.MostLeftChild!=null)
    			return isThereABiggerChildRank(Child);
    		if (Rank(Child)>=Rank(Node))
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public boolean IsRanksNotOrderedToLeft(HeapNode Node){
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child.RightSibling!=null){
    		if (Child.MostLeftChild!=null)
    			return IsRanksNotOrderedToLeft(Child);
    		if (Rank(Child)>Rank(Child.RightSibling))
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public boolean IsRanksNotOrderedToRight(HeapNode Node){
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child.RightSibling!=null){
    		if (Child.MostLeftChild!=null)
    			IsRanksNotOrderedToLeft(Child);
    		if (Rank(Child)<Rank(Child.RightSibling))
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public boolean AreThere3ChildsWithSameRank(HeapNode Node){
    	//Not a Recursive Method!!!!
    	if (Node.MostLeftChild==null)
    		return false;
    	int count=1;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child.RightSibling!=null){
    		if (Rank(Child)==Rank(Child.RightSibling)){
    			count++;
    			if (count==3)
    				return true;	
    		}
    		else
    			count=1;
    		Child=Child.RightSibling;	
    	}
    	return false;
    }
    
    public boolean AreTotalChildsRanksNumNotLegal(HeapNode Node){
    	if (Node.MostLeftChild==null)
    		return false;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		if (Child.MostLeftChild!=null)
    			return AreTotalChildsRanksNumNotLegal(Child);
    		if (AreThere3ChildsWithSameRank(Child)==true)
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
    
    public boolean AreThere3RootsWithSameRank(HeapNode Root){
    	if (Root==null)
    		return false;
    	//Not a recursive method!!!
    	int count=1;
    	HeapNode Node=Root;
    	while (Node.RightSibling!=null){
    		if (Rank(Node)==Rank(Node.RightSibling)){
    			count++;
    			if (count==3)
    				return true;	
    		}
    		else
    			count=1;
    		Node=Node.RightSibling;	
    	}
    	return false;
    }
    
    public int NodeHeight(HeapNode Node){ //For IsHeap Method!!!
    	int leftheight=0;
    	if (Node.MostLeftChild==null)
    		leftheight=0;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		leftheight++;
    		Child=Child.MostLeftChild;
    	}
    	int rightheight=0;
    	if (Node.RightestChild==null)
    		rightheight=0;
    	Child=Node.RightestChild;
    	while (Child!=null){
    		rightheight++;
    		Child=Child.RightestChild;
    	}
    	if (leftheight>=rightheight)
    		return leftheight;
    	return rightheight;
    }
    
    public boolean isHeightDiffThanRank(HeapNode Node){
    	if (NodeHeight(Node)!=Rank(Node))
    			return true;
    	HeapNode Child=Node.MostLeftChild;
    	while (Child!=null){
    		if (Child.MostLeftChild!=null)
    			return isHeightDiffThanRank(Child);
    		if (NodeHeight(Child)!=Rank(Child))
    			return true;
    		else
    			Child=Child.RightSibling;
    	}
    	return false;
    }
   
    public int findMinOfNewRoots(HeapNode Node){ //used in DeleteMin
    	int min=Integer.MAX_VALUE;
    	HeapNode first=Node;
    	while (first!=null){
    		first.parent=null; //update parent of each new root
    		if (first.getKey()<min)
    			min=first.getKey();
    		first=first.RightSibling;
    	}
    	return min;
    }
    
    public HeapNode findMinRoot(HeapNode Node,int min){
    	HeapNode first=Node;
    	while (first!=null){
    		if (first.getKey()==min)
    			return first;
    		else
    			first=first.RightSibling;
    	}
    	return null; //dummy value, program should never(!!) reach 
    	             //here
    }
    
    public HeapNode findNodeBeforeMinRoot(HeapNode ROOT,HeapNode MinimalNode){
    	if (ROOT==MinimalNode)
    		return null;
    	HeapNode first=ROOT;
    	while (first!=null){
    		if (first.RightSibling==MinimalNode)
    			return first;
    		else
    			first=first.RightSibling;
    	}
    	return MinimalNode;//dummy value, program should never(!!) reach 
                           //here
    }
        
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than TrinaryHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{
    	public int key;
    	public int rank; 
    	public HeapNode parent;
    	public HeapNode MostLeftChild;    
    	public HeapNode RightSibling;
    	public HeapNode RightestChild;
    	public int minNode=Integer.MAX_VALUE;
    	
    	public HeapNode(int value) {
            this.key= value;
            rank = 0;
            if (value<minNode)
            	minNode=value;
            if (value<min)           	
            	min=value;
            parent = null;
            RightSibling = null;
            MostLeftChild = null;
            RightestChild=null;      
    	}
    	
    	public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}
    }
    

}



package cn.edu.bistu.cs;

import java.io.Serializable;

public class HTNode implements Comparable<HTNode>,Serializable{
	
	private static final long serialVersionUID = -4126546718799065948L;


	public enum Code{//枚举类型，表示Code变量有且仅有两个取值，0和1
		ZERO('0'), ONE('1');
		private char code;
		private Code(char c){
			this.code = c;
		}
		public char getCode(){
			return code;
		}
	}
	/**
	 *  哈夫曼树的叶子结点数据
	 */
	private char data;
	/**
	 * 结点的编码，只有0和1两种可能
	 */
	private Code code;
	
	public static final char zero = '0';
	
	public static final char one = '1';
	
	private HTNode root;
	private HTNode parent;
	private HTNode lchild;
	private HTNode rchild;
	private double weight;
	//获取根节点
	public HTNode getRoot(){
		return root;
	}
	
	//判断是否是根节点
	public boolean isRoot() {  
        return this.getParent() == null;  
    }
	
  	//设置当前节点为根节点
	public void setRoot(HTNode root){
		this.root=root;
	}
	
	//获取双亲节点
	public HTNode getParent() {  
            return parent; 
    } 
	
	//设置双亲节点
    public void setParent(HTNode parent) {  
        this.parent = parent;  
    }  
	
	//判断是否是左孩子
	public boolean isLeftChild() {  
            return this.getParent()!=null && this == this.getParent().getLchild();  
	} 
	
	//判断是否是右孩子
  	public boolean isRightChild() {  
           return this.getParent()!=null && this == this.getParent().getRchild();  
    } 
	
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public HTNode getLchild() {
		return lchild;
	}
	public void setLchild(HTNode lchild) {
		this.lchild = lchild;
	}
	public HTNode getRchild() {
		return rchild;
	}
	public void setRchild(HTNode rchild) {
		this.rchild = rchild;
	}

	public boolean isLeaf() {
	 	if(this.getLchild() == null && this.getRchild() == null)  
            return true;
        else
        	return false;
	}
	
	public void setLeaf(boolean isLeaf) {
	}
	
	public Code getCode() {
		return code;
	}
	
	public void setCode(Code code) {
		this.code = code;
	}
	
	public int compareTo(HTNode o) {
		if(this.weight<o.weight){
			return -1;
		}
		else{
			return 1;
		}
	}

	public void setDepth(int depth) {
	}
	
}
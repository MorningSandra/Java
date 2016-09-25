package cn.edu.bistu.cs;
import java.io.Serializable;
import java.util.*;

public class HuffmanTree implements Serializable {
	
	private static final long serialVersionUID = -443619826762610831L;

	private Map<Character, String> code = new HashMap<Character,String>();
	
	private HTNode tree = null;
		
	/**
	 * 根据初始的结点列表，建立哈夫曼树，
	 * 并生成哈夫曼编码，保存在当前类的code对象中，
	 * 生成的树根结点，被保存在当前类的tree对象中。
	 * 可以反复生成哈夫曼树，每次重新构建树，将更新编码
	 */
	public HTNode buildTree(List<HTNode> nodes){
		
		PriorityQueue<HTNode> que = new PriorityQueue<HTNode>();//在优先队列中，元素被赋予优先级。当访问元素时，具有最高优先级的元素最先删除。
																//最小的元素在队列的头部因而最先被取出
		if(nodes.size()>0){
			for(int i = 0; i < nodes.size(); i++)
				que.add(nodes.get(i));
			
			//依次查看队列中最小的两个元素，同时依次将两个元素从容器中删除掉，并将两个最小元素的权值相加作为新的结点入队列
			while(que.size()> 1){
				HTNode lowest1 = que.poll();//查看最小的第一个元素，并从容器中删除
				HTNode lowest2 = que.poll();//查看最小的第二个元素，并从容器中删除
				
				HTNode totalnode = new HTNode();//将两个最小元素权值相加
				totalnode.setData((char)(lowest1.getData()+lowest2.getData()));
				totalnode.setWeight(lowest1.getWeight()+lowest2.getWeight());
				totalnode.setLchild(lowest1);
				totalnode.setRchild(lowest2);
				lowest1.setParent(totalnode);
				lowest2.setParent(totalnode);
				que.add(totalnode);
			}

			tree = que.poll();//查看队列中最后一个元素并作为树的根节点
			tree.setRoot(tree);
			HTNode p = tree.getRoot();
			
			//为哈夫曼树编码用中序非递归遍历实现，并将结果保存在code对象中
			Stack<HTNode> stack = new Stack<HTNode>();
			while( !stack.empty() || p != null ){
				while(p != null){//p非空或栈非空时
					if(p.isRoot())//若是根节点，不对其编码
						code.put(p.getData(),"");
					else if(p.isLeftChild())//若是左孩子，父节点编码+"0"
						code.put(p.getData(), code.get(p.getParent().getData())+"0");
					else if(p.isRightChild())//若是右孩子，父节点编码+"1"
						code.put(p.getData(), code.get(p.getParent().getData())+"1");
				
					stack.push(p);//p结点入栈
					p = p.getLchild();//进入左子树
				}
				if(!stack.empty()){//p为空且栈非空时
					p = stack.pop();//p指向出栈节点
					p = p.getRchild();//进入右子树
				}
			}
			return tree;
		}
		
		else {
			System.out.println("输入有误，请重新输入字符串：");
			System.out.println();
			return null;
		}
	}
	
	/**
	 * 根据已建立的哈夫曼树根结点，生成对应的字符编码，
	 * 字符编码应为0，1字符串
	 */
	public static Map<Character, String> getCode(HTNode hftree){
		
		Map<Character, String> characterencoding = new HashMap<Character,String>();
		Stack<HTNode> stack = new Stack<HTNode>();
		
		HTNode p = hftree.getRoot();
		
		//为哈夫曼树编码用中序非递归遍历实现，并将结果保存在characterencoding对象中
			while( p != null || !stack.empty()){ //p非空或栈非空时
				while(p != null){
					if(p.isRoot())//若是根节点，不对其进行编码
						characterencoding.put(p.getData(),"");
					else if(p.isLeftChild())//若是左孩子，获取父节点编码并+"0"
						characterencoding.put(p.getData(),characterencoding.get(p.getParent().getData())+"0");
					else if(p.isRightChild())//若是右孩子，获取父节点编码并+"1"
						characterencoding.put(p.getData(),characterencoding.get(p.getParent().getData())+"1");

					stack.push(p);//p节点入栈
					p = p.getLchild();//进入左子树
				}
				if(!stack.empty()){//p为空且栈非空时
					p = stack.pop();//p指向出栈节点
					p = p.getRchild();//进入右子树
				}
			}
			return characterencoding;
		}
	
	/**
	 * 获取已建立的哈夫曼树生成的字符编码，
	 * 字符编码应为0，1字符串
	 */
	public Map<Character, String> getCode(){
		return this.code;
	}
	
	/**
	*统计字符串中字符出现的频率，建立字符和权值（字符出现次数）的映射函数 
	*/
	public static Map<Character,Integer> computeCharCount(String data){
		char[] dataArray = data.toCharArray();//将字符串转换为字符数组
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		for(char c : dataArray){
			Character character = new Character(c);
			if(map.containsKey(character))
				map.put(character,map.get(character)+1);//将该character所映射的键值+1
			else map.put(character,1);
		}
		return map;
	}
	
	/**
	 * 使用当前类训练好的huffman编码来对文本进行编码
	 */
	public String encode(String text){
		char[] textArray = text.toCharArray();
		String str = "";
		for(int i=0;i<text.length();i++){//使用当前类训练好的huffman编码来对文本进行编码
			str = str + code.get(textArray[i]);
		}
		return str;
	}
	
	/**
	 * 使用指定的huffman编码来对文本进行编码
	 */
	public static String encode(String textstr, Map<Character, String> code){
		char[] textArray = textstr.toCharArray();
		String str ="";
		for(int i=0;i<textArray.length;i++){//使用指定的huffman编码来对文本进行编码
			str = str + code.get(textArray[i]);
		}
		return str;
	}

	/**
	 * 使用当前类中训练好的huffman编码，
	 * 对编码后的文本进行解码
	 */
	public String decode(String text){
		char[] textArray = text.toCharArray();
		HTNode p = tree.getRoot();
		String decodeTexts = "";
		int i = 0;
		int temp = 0;
		
		while(i < textArray.length){
			for(i = temp; i < textArray.length; i++){
				if(p.isLeaf() == false){//非叶子结点
					if(textArray[i] == '0'){//若取值为0，进入左孩子
						p = p.getLchild();
					}
					else if(textArray[i] == '1'){//若取值为1，进入右孩子
						p = p.getRchild();
					}
				}
				else{//叶子节点，保存此时i的值到temp，下次从i开始继续解码
					decodeTexts = decodeTexts + p.getData();
					p = tree;
					temp = i;
					break;
				}
			}
		}
		decodeTexts = decodeTexts + p.getData();
		return decodeTexts;
	}
	
	public HTNode getTree() {
		return tree;
	}

	/**
	 * 使用预先建立好的huffman树，
	 * 对编码后的文本进行解码
	 */
	public String decode(String text, HTNode p){
		char[] textArray = text.toCharArray();
		String decodeTexts = "";
		
		int i = 0;
		int temp = 0;
		while(i <= textArray.length){
			for(i = temp; i<textArray.length; i++){
				if(textArray[i] == '0')//若取值为0，进入左孩子
					p = p.getLchild();
				if(textArray[i] == '1')//若取值为1，进入右孩子
					p = p.getRchild();
				if(p.isLeaf()){//叶子节点，保存此时i的值到temp，下次从i开始继续解码
					decodeTexts = decodeTexts + p.getData();
					temp = i;
					break;
				}
			}
		}
		return decodeTexts;
	}
}

import java.util.*;

public class LinearHash {
  LinkedList<String>[] table;
  int splitIndex;
  int HTSize;
  int numWord;
  int empty;
  int hashing;
  public LinearHash(int HTinitSize)	{
    table=new LinkedList[HTinitSize];
    HTSize=HTinitSize;
    hashing=HTinitSize;
    numWord=0;
    empty=HTinitSize;
    for(int i=0;i<table.length;i++){
      table[i]=new LinkedList<>();
    }
  }// constructor for the Hash table

  public int insertUnique(String word) {
    int target=(int)MyUtil.ELFhash(word,hashing);
    int result;
    int target_extend=(int)MyUtil.ELFhash(word,hashing*2);
    if(target<splitIndex){
      target=target_extend;
    }
    for(String a:table[target]){
      if(a.equals(word)){return -1;}
    }
    numWord++;
    if(table[target].isEmpty()){
        table[target].add(word);
        //System.out.printf("%s %d\n",word,target);
        return target;
      }
      else{//collision occurs
      table[target].add(word);
      result=target;
      //System.out.printf("%d st collision : %s %d\n",splitIndex,word,target);
          HTSize+=1;
          table=Arrays.copyOf(table, HTSize);
          table[HTSize-1]=new LinkedList<>();
          Iterator it=table[splitIndex].iterator();

          while(it.hasNext()){
            String s=(String)it.next();
            int a=(int)MyUtil.ELFhash(s,hashing*2);
            if(a>splitIndex && a<HTSize){
              it.remove();
              //System.out.printf("[%s] has been moved from %d->%d\n",s,splitIndex,a);
              table[a].add(s);
            }
          }
          splitIndex++;
          if(splitIndex==hashing){
            hashing*=2;
            splitIndex=0;
          }

      //System.out.printf("%s %d\n",word,result);
          return result;
        }
  }
  public int lookup(String word) {
    int target=(int)MyUtil.ELFhash(word,hashing);
    if(target<splitIndex){
      target=(int)MyUtil.ELFhash(word,hashing*2);
    }
    int size=table[target].size();
    if(size==0){return 0;}
    for(int i=0;i<size;i++){
      if(table[target].get(i).equals(word)){
        return size;
      }
    }
    return -size;
  }

  public int wordCount() {return numWord;}
  public int emptyCount() {
    int cnt=0;
    for(int i=0;i<table.length;i++){
      if(table[i].isEmpty()){
        cnt++;
      }
    }
    return cnt;
  }
  public int size() {return HTSize;}			// 2^k + collisions in the current round
  public void print(){
    StringBuilder str=new StringBuilder();
    for(int i=0;i<HTSize;i++){
      Collections.sort(table[i]);
        String s="["+i+": ";
        for(String b:table[i]){
          s+=b+" ";
        }
        s=s.trim();
        str.append(s+"]\n");
      }


    System.out.println(str.toString().trim());
  }   		// Print keys in the hash table

}

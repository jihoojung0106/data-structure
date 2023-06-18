// LongInt ADT for unbounded integers

import java.util.ArrayList;

public class LongInt {
  boolean positive;
  ArrayList<Byte> data;
  boolean isZero;

  // constructor
  public LongInt(String s) {
    s=s.trim();
    this.positive=true;
    if(s.charAt(0)=='-'){this.positive=false;s=s.substring(1,s.length());}
    this.isZero=true;
    data=new ArrayList<>(s.length());
    for(int i=s.length()-1;i>=0;i--){
      char a=s.charAt(i);
      if(a!='0'){isZero=false;}
      data.add((byte)Character.getNumericValue(a));
    }
  }
  public LongInt(ArrayList<Byte> data,boolean positive){
    this.data=data;
    this.positive=positive;
    this.isZero=true;
    for(int i=0;i<data.size();i++){
      if(data.get(i)!=0){isZero=false;}
    }
  }
  public LongInt(int a){
    isZero=true;
  }
  public LongInt[] bigger(LongInt a, LongInt b){
    if(a.data.size()>b.data.size()){
      return new LongInt[]{a,b};
    }
    else if(a.data.size()<b.data.size()){
      return new LongInt[]{b,a};
    }
    for(int i=a.data.size()-1;i>=0;i--){
      if(a.data.get(i) >b.data.get(i)){
        return new LongInt[]{a,b};
      }
      if(a.data.get(i) < b.data.get(i)){
        return new LongInt[]{b,a};
      }
    }
    return new LongInt[]{a,b};
  }

  public void setPositive(boolean positive) {
    this.positive = positive;
  }

  public void setData(ArrayList<Byte> data) {
    this.data = data;
  }

  // returns 'this' + 'opnd'; Both inputs remain intact.
  public LongInt add(LongInt opnd) {

    if(this.positive==opnd.positive){
      ArrayList<Byte> result=new ArrayList<>();
      LongInt big=bigger(this,opnd)[0];
      LongInt small=bigger(this,opnd)[1];
      for(int i=0;i<big.data.size()+1;i++){
        result.add((byte)0);
      }
      for(int i=0;i<big.data.size();i++){
        int sum;
        if(i<small.data.size()){
          sum=big.data.get(i)+small.data.get(i);
        }
        else{
          sum=big.data.get(i);
        }
        sum+=result.get(i);

        if(sum>=10){
          sum=sum % 10;
          result.set(i+1,(byte)(result.get(i+1)+1));
        }
        result.set(i,(byte)(sum));
      }
      if(this.positive){return new LongInt(result,true);}
      return new LongInt(result,false);
    }

    //in case where this.postive != opnd.positive
    ArrayList<Byte> result=new ArrayList<>();

    LongInt big=bigger(this,opnd)[0];
    LongInt small=bigger(this,opnd)[1];
    for(int i=0;i<big.data.size();i++){
      result.add((byte)0);
    }
    for(int i=0;i<big.data.size();i++){
      int sub;
      if(i<small.data.size()){
        sub=big.data.get(i)-small.data.get(i);
      }
      else{
        sub=big.data.get(i);
      }
      sub+=result.get(i);
      if(sub<0){
        sub+=10;
        result.set(i+1,(byte)(result.get(i+1)-1));
      }
      result.set(i,(byte)(sub));
    }
    if((this.positive && big.equals(this)) || (!this.positive && big.equals(opnd))){
      return new LongInt(result,true);
    }
    return new LongInt(result,false);

  }

  // returns 'this' - 'opnd'; Both inputs remain intact.
  public LongInt subtract(LongInt opnd) {

    if(this.positive==opnd.positive){
      ArrayList<Byte> result=new ArrayList<>();

      LongInt big=bigger(this,opnd)[0];
      LongInt small=bigger(this,opnd)[1];
      for(int i=0;i<big.data.size();i++){
        result.add((byte)0);
      }
      for(int i=0;i<big.data.size();i++){
        int sub;
        if(i<small.data.size()){
          sub=big.data.get(i)-small.data.get(i);
        }
        else{
          sub=big.data.get(i);
        }
        sub+=result.get(i);
        if(sub<0){
          sub+=10;
          result.set(i+1,(byte)(result.get(i+1)-1));
        }
        result.set(i,(byte)(sub));
      }
      if(this.positive && big.equals(this)){return new LongInt(result,true);}
      if(this.positive && big.equals(opnd)){return new LongInt(result,false);}
      if(!this.positive && big.equals(opnd)){return new LongInt(result,true);}
      if(!this.positive && big.equals(this)){return new LongInt(result,false);}
    }
    ArrayList<Byte> result=new ArrayList<>();
    LongInt big=bigger(this,opnd)[0];
    LongInt small=bigger(this,opnd)[1];
    for(int i=0;i<big.data.size()+1;i++){
      result.add((byte)0);
    }
    for(int i=0;i<big.data.size();i++){
      int sum;
      if(i<small.data.size()){
        sum=big.data.get(i)+small.data.get(i);
      }
      else{
        sum=big.data.get(i);
      }
      sum+=result.get(i);

      if(sum>=10){
        sum=sum % 10;
        result.set(i+1,(byte)(result.get(i+1)+1));
      }
      result.set(i,(byte)(sum));
    }
    return new LongInt(result,this.positive);
  }

  // returns 'this' * 'opnd'; Both inputs remain intact.
  public LongInt multiply(LongInt opnd) {

    if(this.isZero || opnd.isZero){return new LongInt(0);}
    ArrayList<Byte> result=new ArrayList<>();
    int this_size=this.data.size();
    int opnd_size=opnd.data.size();
    LongInt big=new LongInt(bigger(this,opnd)[0].data,bigger(this,opnd)[0].positive);
    LongInt small=new LongInt(bigger(this,opnd)[1].data,bigger(this,opnd)[1].positive);
    int max_len=big.data.size()+small.data.size();
    int big_diff=max_len-big.data.size();
    int small_diff=max_len-small.data.size();
    for(int i=0;i<big_diff;i++){
      big.data.add((byte)0);
    }
    for(int i=0;i<small_diff;i++){
      small.data.add((byte)0);
    }
    for(int i=0;i<max_len;i++){
      result.add((byte)0);
    }
    int this_new_size=this.data.size();
    int opnd_new_size=opnd.data.size();

    int carry = 0;
    for (int idx = 0; idx <max_len; ++idx) {
      int sum = carry;
      for (int i = 0; i <= idx; ++i) {
        sum += small.data.get(i)*big.data.get(idx - i);
      }
      carry = sum / 10;
      result.set(idx,(byte)(sum % 10));
    }

    for(int i=0;i<this_new_size-this_size;i++){
      int len=this.data.size();
      this.data.remove(len-1);
    }
    for(int i=0;i<opnd_new_size-opnd_size;i++){
      int len=opnd.data.size();
      opnd.data.remove(len-1);
    }
    return new LongInt(result,this.positive==opnd.positive);
  }


  // print the value of 'this' element to the standard output.
  public void print() {
    if(this.isZero){System.out.print("0");}
    int len=this.data.size();
    StringBuilder sb=new StringBuilder();
    if(!this.positive){
      sb.append('-');
    }
    boolean check=false;
    for(int i=len-1;i>=0;i--){
      if(!check && this.data.get(i)!=0){
        sb.append(this.data.get(i));
        check=true;
        continue;
      }
      if(check){
        sb.append(this.data.get(i));
      }
    }
    System.out.print(sb.toString().trim());
  }
//  public static void main(String[] args) {
//    LongInt a=new LongInt("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
//    LongInt b=new LongInt("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
//
//    a.multiply(b).print();
//  }
}




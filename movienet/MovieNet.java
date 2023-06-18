/*
 * Six Degrees of Kevin Bacon
*/

import java.lang.*;
import java.util.*;

public class MovieNet {

  static final String KevinBacon = "Bacon, Kevin";
  static HashMap<String, HashSet> movie_actor;
  static HashMap<String, HashSet> actor_movie;
  static HashMap<String, HashMap> actor_actor;
  static HashSet<String> allActors;
  // Each instance of movielines is String[] such that
  //	String[0] = title of movie
  //	String[1..n-1] = list of actors
  public MovieNet(LinkedList<String[]> movielines) {
    movie_actor=new HashMap<>();
    actor_movie=new HashMap<>();
    actor_actor=new HashMap<>();
    allActors=new HashSet<>();
    HashMap<String,Integer> one;
      for(String[] a:movielines){
      LinkedHashSet<String> actors=new LinkedHashSet<>();
      for(int i=1;i<a.length;i++){
        allActors.add(a[i]);
        actors.add(a[i]);
        if(!actor_movie.containsKey(a[i])){
          HashSet<String> movies=new HashSet<>();
          movies.add(a[0]);
          actor_movie.put(a[i],movies);
          one=new HashMap();
          for(int j=1;j<a.length;j++) {
            if (i != j) {
              one.put(a[j],  1);
            }
            actor_actor.put(a[i],one);
          }
        }else{
          actor_movie.get(a[i]).add(a[0]);
          for(int j=1;j<a.length;j++){
            if(i!=j){
              one=actor_actor.get(a[i]);
              if(one.containsKey(a[j])){
                one.put(a[j],one.get(a[j])+1);
              }
              else{
                one.put(a[j],1);
              }
            }
          }
        }
      }
      movie_actor.put(a[0],actors);
    }
  }	// Constructor

/*============================================================================*/

  // [Q1]
  public String[] moviesby(String[] actors) {
    HashSet<String> set;
    HashSet<String> result;
    if(actors.length==0){return null;}
    if(actors.length==1){
      if(!actor_movie.containsKey(actors[0])){return null;}
      else{
        set=actor_movie.get(actors[0]);
        result=set;
        String[] results=new String[result.size()];
        //System.out.println(result.size());
        int i=0;
        for(String ele:result){
          //System.out.println("i "+i);
          results[i++] = ele;
        }
        if(result.size()==0){return null;}
        return results;
      }

    }
    //System.out.println("length : "+ actors.length);
    if(!actor_movie.containsKey(actors[0])){return null;}
    if(!actor_movie.containsKey(actors[1])){return null;}
    set=actor_movie.get(actors[0]);
    HashSet<String> set2=actor_movie.get(actors[1]);
    HashSet copy=new HashSet<>();
    if(set.size()>set2.size()){
      for(String one:set2){
        copy.add(one);
      }
    }
    else {
      for(String one:set2){
        copy.add(one);
      }
    }
    copy.retainAll(set);
    for(int i=2;i<actors.length;i++){
      if(!actor_movie.containsKey(actors[i])){return null;}
      copy.retainAll(actor_movie.get(actors[i]));
      if(copy.size()==0){return null;}
    }
    result=copy;
    if(result.size()==0){return null;}
    String[] string_result=new String[result.size()];
    //System.out.println(result.size());
    int i=0;
    for(String ele:result){
      string_result[i++] = ele;
    }
    return string_result;
  }

  // [Q2]
  public String[] castin(String[] titles) {
    HashSet<String> set;
    HashSet<String> result;
    if(titles.length==0){return null;}
    if(titles.length==1){
      if(!movie_actor.containsKey(titles[0])){return null;}
      else{
        set=movie_actor.get(titles[0]);
        result=set;
        String[] results=new String[result.size()];
        //System.out.println(result.size());
        int i=0;
        for(String ele:result){
          //System.out.println("i "+i);
          results[i++] = ele;
        }
        if(result.size()==0){return null;}
        return results;
      }

    }
    //System.out.println("length : "+ actors.length);
    if(!movie_actor.containsKey(titles[0])){return null;}
    if(!movie_actor.containsKey(titles[1])){return null;}
    set=movie_actor.get(titles[0]);
    HashSet<String> set2=movie_actor.get(titles[1]);
    HashSet copy=new HashSet<>();
    if(set.size()>set2.size()){
      for(String one:set2){
        copy.add(one);
      }
    }
    else {
      for(String one:set2){
        copy.add(one);
      }
    }
    copy.retainAll(set);
    for(int i=2;i<titles.length;i++){
      if(!movie_actor.containsKey(titles[i])){return null;}
      copy.retainAll(movie_actor.get(titles[i]));
      if(copy.size()==0){return null;}
    }
    result=copy;
    if(result.size()==0){return null;}
    String[] string_result=new String[result.size()];
    //System.out.println(result.size());
    int i=0;
    for(String ele:result){
      string_result[i++] = ele;
    }
    return string_result;
  }

  // [Q3]
  public String[] pairmost(String[] actors) {
    if(actors.length==0){return null;}
    if(actors.length==1||actors.length==0){return null;}
    if(actors.length==2){return actors;}
    String[] pair=new String[2];
    int max=0;
    int a;
    HashMap<String,Integer> one;
    for(int i=0;i<actors.length-1;i++){
      one=actor_actor.get(actors[i]);
      for(int j=i+1;j<actors.length;j++){
        a=one.get(actors[j]);
        if(one.get(actors[j])>max){
          pair[0]=actors[i];
          pair[1]=actors[j];
          max=a;
        }
      }
    }
    return pair;
  }

  // [Q4]
  public int Bacon(String actor) {
    if(!allActors.contains(KevinBacon)){return -1;}
    if(!allActors.contains(actor)){return -1;}
    if(actor.equals(KevinBacon)){return 0;}
    //src
    HashSet<String> s=new HashSet<>();
    s.add(KevinBacon);
    int level=0;
    HashMap<String,Integer> map;
    String v;
    Queue<String> q=new LinkedList<>();
    q.offer(KevinBacon);

    while (!q.isEmpty()){
      int size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();

        map=actor_actor.get(v);
        for(String u:map.keySet()){
          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
            if(u.equals(actor)){
              return level+1;
            }
          }
        }
      }
      level++;
    }
    return -1;
  }

  // [Q5]
  public int distance(String src, String dst) {
    if(!allActors.contains(src)){return -1;}
    if(!allActors.contains(dst)){return -1;}
    if(dst.equals(src)){return 0;}
    //src
    HashSet<String> s=new HashSet<>();
    s.add(src);
    int level=0;
    HashMap<String,Integer> map;
    String v;
    Queue<String> q=new LinkedList<>();
    q.offer(src);

    while (!q.isEmpty()){
      int size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();
//        if(v.equals(dst)){
//          return level;
//        }
        map=actor_actor.get(v);
        for(String u:map.keySet()){
          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
            if(u.equals(dst)){
              return level+1;
            }
          }
        }

      }
      level++;
    }
    return -1;
  }

  // [Q6]
  public int npath(String src, String dst) {
    if(!allActors.contains(src)){return 0;}
    if(!allActors.contains(dst)){return 0;}
    if(dst.equals(src)){return 1;}
    HashMap<String,Integer> map;
    String v;

    //src
    HashSet<String> s=new HashSet<>();
    s.add(src);

    Queue<String> q=new LinkedList<>();
    q.offer(src);

    HashMap<String,Integer> paths=new HashMap<>();
    HashMap<String,Integer> distance=new HashMap<>();


    int base=Integer.MAX_VALUE;
    int level=0;
    paths.put(src,1);
    distance.put(src,0);
    int size;
    while (!q.isEmpty()){
      size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();
        map=actor_actor.get(v);
       // int c=0;
        for(String u:map.keySet()){

          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
            if(u.equals(dst)){
              base=level;
            // System.out.println(base+"base");
            }
          }
         // System.out.println("level "+level+"base "+base);
          if(!distance.containsKey(u)){
            distance.put(u,distance.get(v)+1);
            paths.put(u,paths.get(v));
          }
          else if((distance.get(u)>distance.get(v)+1)){
            distance.put(u,distance.get(v)+1);
            paths.put(u,paths.get(v));
//            if(u.equals(dst)){
//              System.out.println(">");
//            }
          }
          else if(distance.get(u)==distance.get(v)+1){
            paths.put(u,paths.get(v)+paths.get(u));
//            if(u.equals(dst)){
//              System.out.println("=");
//            }
          }
//          if(base<=level && c==map.size()-1){
//            System.out.println("stop : "+paths.get(dst));
//          }
//          if(u.equals(dst) && base==level){
//            System.out.println("answer "+ paths.get(dst)+" "+i+" "+size+ " "+c+" "+ map.size());
//          }
//          if(base==level){
//          System.out.println(paths.get(dst)+" "+"v: "+v+"u: "+i+" "+size+ " "+c+" "+ map.size());}
//          c++;
          // System.out.println("level "+level+"base "+base);
        }

      }
      if(base<=level){
//        System.out.printf("=======================================================================\n");
        return  paths.get(dst);
      }
      level++;
    }
    return 0;
  }

  // [Q7]
  public String[] apath(String src, String dst) {
    if(!allActors.contains(src)){return null;}
    if(!allActors.contains(dst)){return null;}
    if(dst.equals(src)){return new String[]{src};}
    HashMap<String,String> pre=new HashMap<>();

    HashSet<String> s=new HashSet<>();
    s.add(src);
    HashMap<String,Integer> map;
    String v;
    Queue<String> q=new LinkedList<>();
    q.offer(src);

    while (!q.isEmpty()){
      int size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();
        map=actor_actor.get(v);
        for(String u:map.keySet()){
          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
            pre.put(u,v);
            if(u.equals(dst)){
              LinkedList<String> path = new LinkedList<>();
              String crawl = dst;
              String c;
              path.add(crawl);
              while (!crawl.equals(src)) {
                c=pre.get(crawl);
                path.add(0,c);
                crawl = c;
              }
              String[] result=new String[path.size()];
              int j=0;
              for (String a:path) {
                result[j++]=a;
              }
              return result;
            }
          }
        }
      }
    }
    return null;

  }


  // [Q8]
  public int eccentricity(String actor) {
    if(!allActors.contains(actor)){return 0;}

    //src
    HashSet<String> s=new HashSet<>();
    s.add(actor);
    int level=0;
    HashMap<String,Integer> map;
    String v;
    Queue<String> q=new LinkedList<>();
    q.offer(actor);

    while (!q.isEmpty()){
      int size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();
        map=actor_actor.get(v);
        for(String u:map.keySet()){
          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
          }
        }
      }
      level++;
    }
    if(level==0){return 0;}
    return level-1;
  }

  // [Q9]
  public float closeness(String actor) {
    if(!allActors.contains(actor)){return 0;}
    HashSet<String> s=new HashSet<>();
    s.add(actor);
    int level=0;
    HashMap<String,Integer> map;
    String v;
    Queue<String> q=new LinkedList<>();
    q.offer(actor);
    float result=0;
    while (!q.isEmpty()){
      int size=q.size();
      for(int i=1;i<=size;i++){
        v=q.poll();
        map=actor_actor.get(v);
        for(String u:map.keySet()){
          if(!s.contains(u)){
            q.offer(u);
            s.add(u);
            result+=1/Math.pow(2.0, level+1);
          }
        }
      }
      level++;
    }
    return result;
  }
/*============================================================================*/
}


package package com.github.brandtabbott;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class BlockingPoolTest {
  BlockingPool<String> pool;
  
  @Before
  public void setUp() throws Exception {
    Collection<String> elementsToPool = new ArrayList<String>();
    elementsToPool.add("element1");
    
    pool = new BlockingPool<String>(elementsToPool);    
  }  
  
  @Test(expected=BlockingPoolException.class)
  public void testEmptyCollectionConstruction() {
    Collection<String> elementsToPool = new ArrayList<String>();    
    pool = new BlockingPool<String>(elementsToPool);
  }
  
  @Test
  public void testValidConstuction() {    
    assert(pool.size()==1);
  }
  
  @Test
  public void testBorrowAndRelease() {
    assert(pool.size()==1);
    
    String element1 = pool.borrow();
    assert(pool.size()==0);
    pool.release(element1);
    assert(pool.size()==1);
  }
  
  @Test
  public void testBlockingCondition() throws InterruptedException {    
    Thread thread1 = new Thread(){
      public void run(){
        String element1 = pool.borrow();
        
        try {
          sleep(5000);
          assert(pool.size() == 0);
          assert("element1"==element1);
          pool.release(element1);          
        } catch (InterruptedException e) {             
          e.printStackTrace();
        }        
      }
    };
    
    Thread thread2 = new Thread(){
      public void run(){        
        try {
          sleep(1000);
          String element1 = pool.borrow();
          assert(pool.size() == 0);
          assert("element1"==element1);
          pool.release(element1);                  
        } catch (InterruptedException e) {            
          e.printStackTrace();
        }        
      }
    };    
    
    thread1.start();       
    thread2.start();
    thread1.join();
    thread2.join();
  }
}

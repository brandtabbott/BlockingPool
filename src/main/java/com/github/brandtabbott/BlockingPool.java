package com.github.brandtabbott;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockingPool<E> {
  private final Logger logger = LoggerFactory.getLogger(BlockingPool.class);
  
  private final ConcurrentLinkedQueue<E> queue;
  
   BlockingPool(Collection<E> poolContent) throws BlockingPoolException{
    if(poolContent == null || poolContent.size() < 1)
      throw new BlockingPoolException("Cannot initialize a BlockingPool without pool content");
    
    queue = new ConcurrentLinkedQueue<E>(poolContent);    
  }
   
  public E borrow(){
    logger.debug("Attempting to borrow from BlockingPool with size: "+queue.size());
    if(queue.peek() == null){
      try{
        synchronized(this){
          logger.info("BlockingPool contains "+queue.size()+" elements, queue.peek is null, waiting for notification...");
          wait();
        }                  
      }
      catch(InterruptedException e){
        logger.debug("BlockingPool thread interrupted!!");
        e.printStackTrace();
      }
    }
    
    logger.debug("Borrowed one item from the BlockingPool with size "+queue.size());
    return queue.poll();
  }
  
  public void release(E borrowed){
    logger.debug("Releasing one item back into the BlockingPool with size "+queue.size());
    queue.offer(borrowed);
    synchronized(this){
      notify();
    }    
  }
  
  public int size(){
    return queue.size();
  }
}

package latiOS.util;

import java.util.EmptyStackException;
import java.util.Stack;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.utils.SimpleLog;

public class SizedMessageStack<E> extends Stack<Message> {
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	private static final long serialVersionUID = 1L;
	
	private int maxSize;

	public SizedMessageStack() {
		super();
		this.maxSize=30;
	}
	
	public SizedMessageStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public Message push(Message msg) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(msg);
    }
    
    @Override
    public synchronized Message peek() {
    	if (this.size() == 0) {
            throw new EmptyStackException();
    	}
    	return elementAt(this.size() - 1);
    }
    
    public synchronized Message peekAtUser(String id) {
    	if (this.size() == 0) {
    		throw new EmptyStackException();
    	}
    	for (int i=this.size();i>0;i--) {
    		if (elementAt(i).getAuthor().getId().equals(id)) {
    			return elementAt(i);
    		}
    	}
    	throw new NullPointerException("Message not in recent message stack");
    }
    
    @Override
    public synchronized Message pop() {
    	Message obj = peek();
        removeElementAt(this.size() - 1);
        return obj;
    }
    
    public synchronized Message popAtUser(String id) {
    	if (this.size() == 0) {
    		log.fatal(new EmptyStackException());
    	}
    	for (int i=this.size();i>0;i--) {
    		if (elementAt(i).getAuthor().getId().equals(id)) {
    			Message obj = elementAt(i);
    			removeElementAt(i);
    			return obj;
    		}
    	}
    	throw new NullPointerException("Message not in recent message stack");
    }
}

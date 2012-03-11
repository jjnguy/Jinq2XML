package xmlcomponents;

/**
 * Runtime exception wrapper for all exceptions that may be thrown from XML parsing code. The idea is to let users
 * forget about exceptions that they don't wanna worry about.
 * 
 * @author Justin Nelson
 * 
 */
public class JinqException extends RuntimeException {
   
   private static final long serialVersionUID = 1L;
   
   public JinqException(String message) {
      super(message);
   }
   
   public JinqException(Exception e) {
      super(e);
   }
}

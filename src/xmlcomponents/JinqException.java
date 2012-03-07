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
   
   public JinqException() {
      super();
   }
   
   public JinqException(String message) {
      super(message);
   }
   
   public JinqException(String message, Jode cause) {
      super(message + ": caused By - " + cause.toString());
   }
   
   public JinqException(String message, JodeList cause) {
      super(message + ": caused By - " + cause.toString());
   }
   
   public JinqException(String message, Jocument cause) {
      super(message + ": caused By - " + cause.toString());
   }
   
   public JinqException(String message, Jattr cause) {
      super(message + ": caused By - " + cause.toString());
   }
   
   public JinqException(Exception e) {
      super(e);
   }
}

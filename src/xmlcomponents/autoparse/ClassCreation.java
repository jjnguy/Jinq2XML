package xmlcomponents.autoparse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import xmlcomponents.Jattr;
import xmlcomponents.Jode;

public class ClassCreation {

   public static void createClasses(Jode j, String destinationFolder) throws FileNotFoundException {
      new File(destinationFolder).mkdirs();
      String classShell = "public class <className> {\r\n<properties>}\r\n";
      String className = j.name();
      List<ClassProperties> properties = new ArrayList<ClassProperties>();
      for (Jattr jattr : j.attributes()) {
         String propName = jattr.name();
         String typeDescriptor = "String";
         properties.add(new ClassProperties(propName, typeDescriptor));
      }
      for (Jode child : j.children().distinct()) {
         String propName = child.name();
         String typeDescriptor = null;
         if (j.hasMultipleChildren(child.name())) {
            typeDescriptor = "List<" + child.name() + ">";
         } else {
            typeDescriptor = child.name();
         }
         properties.add(new ClassProperties(propName, typeDescriptor));
         createClasses(child, destinationFolder);
      }
      String propertiesString = "";
      for (ClassProperties prop : properties) {
         propertiesString += "\tprivate " + prop.typeDescriptor + " " + prop.name + ";\r\n";
      }
      String finalString = classShell.replaceAll("<className>", className).replaceAll("<properties>", propertiesString);
      PrintStream out = new PrintStream(new File(destinationFolder + "/" + className + ".java"));
      out.print(finalString);
      out.close();
   }

   private static class ClassProperties {
      final String name;
      final String typeDescriptor;

      ClassProperties(String name, String typeDescriptor) {
         this.name = name;
         this.typeDescriptor = typeDescriptor;
      }
   }
}

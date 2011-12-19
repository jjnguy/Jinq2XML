package xmlcomponents.autoparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import xmlcomponents.Jattr;
import xmlcomponents.Jode;

public class ClassCreation {

   public static void createClasses(Jode j, String destinationFolder) throws FileNotFoundException {
      new File(destinationFolder).mkdirs();
      String imports = "";
      String classShell = "public class <className> {\r\n<properties>}\r\n";
      String className = j.name();
      String annotation = "";
      List<ClassProperties> properties = new ArrayList<ClassProperties>();
      for (Jattr jattr : j.attributes()) {
         String propName = jattr.name();
         String typeDescriptor = determineTypeDescriptor(jattr);
         properties.add(new ClassProperties(propName, typeDescriptor));
      }
      for (Jode child : j.children().distinct()) {
         String propName = child.name();
         String typeDescriptor = null;
         if (j.hasMultipleChildren(child.name())) {
            if (!imports.contains("List")) {
               imports += "import java.util.List;\r\n";
            }
            typeDescriptor = "List<" + child.name() + ">";
         } else {
            if (child.name().equals("#text")) {
               if (!imports.contains("xml")) {
                  imports += "import xmlcomponents.autoparse.annotation.XmlProperty;\r\n";
                  imports += "import xmlcomponents.autoparse.annotation.XmlProperty.XmlPropertyType;\r\n";
               }
               annotation = "\t@XmlProperty(type=XmlPropertyType.TEXT_NODE)";
               typeDescriptor = "String";
               propName = "text";
            } else {
               typeDescriptor = child.name();
            }
         }
         if (!child.name().equals("#text")) {
            createClasses(child, destinationFolder);
         }
         properties.add(new ClassProperties(propName, typeDescriptor));
      }
      String propertiesString = "";
      for (ClassProperties prop : properties) {
         propertiesString += annotation + "\r\n\tprivate " + prop.typeDescriptor + " " + prop.name + ";\r\n";
      }
      String finalString = imports
            + classShell.replaceAll("<className>", className).replaceAll("<properties>", propertiesString);
      PrintStream out = new PrintStream(new File(destinationFolder + "/" + className + ".java"));
      out.print(finalString);
      out.close();
   }

   public static String determineTypeDescriptor(Jattr attr) {
      if (attr.v.equals("true") || attr.v.equals("false"))
         return "boolean";
      try {
         Integer.parseInt(attr.v);
         return "int";
      } catch (Exception e) {
      }
      try {
         Double.parseDouble(attr.v);
         return "double";
      } catch (Exception e) {
      }
      return "String";
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

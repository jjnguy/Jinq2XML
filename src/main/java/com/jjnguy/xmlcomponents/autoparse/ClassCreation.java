package com.jjnguy.xmlcomponents.autoparse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.jjnguy.xmlcomponents.Jocument;
import com.jjnguy.xmlcomponents.Jattr;
import com.jjnguy.xmlcomponents.Jode;

/**
 * Class used for generating Java classes out of XML files. The generated class files are designed to be used in
 * conjunction with the {@link AutoParser}.
 * 
 * This is not quite ready for prime time...
 * 
 * @author jjnguy
 */
public class ClassCreation {
   
   /**
    * Given a {@link Jode}, usually a {@link Jocument}, will generate class files used for eventually consuming the XML
    * into objects.
    * 
    * @param j
    *           the Jode to parse
    * @param destinationFolder
    *           the folder to save the generated class files
    * @throws FileNotFoundException
    */
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
                  imports += "import XmlProperty;\r\n";
                  imports += "import XmlProperty.XmlPropertyType;\r\n";
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

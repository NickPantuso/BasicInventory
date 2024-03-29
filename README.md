# BasicInventory
The program starts out with a completely empty inventory that is not connected to a database. Once you close the program, the inventory is gone. On the main form, you can decide whether to add, modify, or delete parts and products. There's a search bar above each table that will allow you to search for parts and products you've added. There are various input validation methods within the program to prevent you from entering things that don't make sense, such as "dog" for the price of a part. When adding or modifying a product, you can add parts that would be associated with the said product (wheels and handlebars for a bike) via the tables in the product form. Deleting associated parts from a product will not delete them from the parts inventory, just the product itself. You can create a product with no associated parts.

NOTE: Executable requires at least JRE version 1.6. Windows may create a pop-up that tells you to not run the program, but the program is safe.

UPDATE: The products table in the main form now has an extra column that displays true/false depending on if the product has any associated parts. This way, you don't have to try deleting or modifying a product to see if there are associated parts.

# What was learned?

* How to build a basic JavaFX GUI with Scene Builder
* Introduction to FXML
* More familiarity with OOP
* How to create an executable from a jar file

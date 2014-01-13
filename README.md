database_lib
============

## Initialiser une base de données

```java
context = (Application / Activity)
DatabaseHelper dbh = new DatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);
``` 

## Ajouter une table à la base de données
```java
dbh.addTable( TableThatExtendsModel.getClass());
```
## Créer la base de données
```java
dbh.createTables();
```
## Récupérer tous les éléments d'une table
```java
dbh.getAll(TableThatExtendsModel.getClass());
```

## Récupérer le premier élément d'une table
```java
dbh.findFirst(TableThatExtendsModel.getClass());
```

## Insérer un élément dans une table
```java
TableThatExtendsModel obj = new TableThatExtendsModel();
dbh.insertItem(obj);
```

## Insérer plusieurs éléments dans une table
```java
TableThatExtendsModel obj = new TableThatExtendsModel();
TableThatExtendsModel obj2 = new TableThatExtendsModel();
TableThatExtendsModel obj3 = new TableThatExtendsModel();

ArrayList<TableThatExtendsModel> objs = new ArrayList<TableThatExtendsModel>();
objs.add(obj);
objs.add(obj2);
objs.add(obj3);

dbh.insertCollection(objs);
dbh.insertItem(obj);
```

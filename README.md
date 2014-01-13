database_lib
============

## Initialiser une base de données

```java
DatabaseHelper dbh = new DatabaseHelper(this, DATABASE_NAME, DATABASE_VERSION);
``` 

## Ajouter une table à la base de données
```java
dbh.addTable( TableThatExtendsModel.getClass());
```
## créer la base de données
```java
dbh.createTables();
```
## Récupérer tous les éléments d'une table
```java
dbh.getAll(TableThatExtendsModel.getClass());
```

## Récupérer le premier élément
```java
dbh.findFirst(TableThatExtendsModel.getClass());
```

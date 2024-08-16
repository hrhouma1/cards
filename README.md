---
# Partie 1 : Lancement de l'Application et Configuration de la Base de Données PostgreSQL sur Windows
---

- Ce guide vous expliquera comment cloner le projet, l'ouvrir, configurer la base de données PostgreSQL sur Windows, et lancer l'application en utilisant Maven et Spring Boot.

# Étapes à suivre :

1. **Cloner le projet** :
    ```bash
    git clone https://github.com/hrhouma1/cards.git
    ```

2. **Accéder au répertoire du projet** :
    ```bash
    cd cards
    ```

3. **Ouvrir le projet dans votre éditeur de code** (par exemple, Visual Studio Code) :
    ```bash
    code .
    ```

# Configuration de la Base de Données PostgreSQL sur Windows

Avant de lancer l'application, il est nécessaire de configurer la base de données PostgreSQL sur votre système Windows.

# 1. Créer l'utilisateur PostgreSQL `hrgres` et la base de données `microDemo`

1. **Ouvrir pgAdmin ou l'invite de commande PostgreSQL** :

   - **Via pgAdmin** : Vous pouvez utiliser l'interface graphique pour créer un nouvel utilisateur et une base de données.
   - **Via l'invite de commande** : Ouvrez l'invite de commande PostgreSQL.

2. **Créer l'utilisateur `hrgres` avec un mot de passe** :

   Si vous utilisez l'invite de commande PostgreSQL :
   ```sql
   CREATE USER hrgres WITH PASSWORD 'hrgres';
   ```

   Dans pgAdmin :
   - Allez dans la section "Login/Group Roles".
   - Faites un clic droit et sélectionnez "Create" > "Login/Group Role".
   - Entrez `hrgres` comme nom d'utilisateur et définissez le mot de passe sur `hrgres`.

3. **Créer la base de données `microDemo`** :

   Si vous utilisez l'invite de commande PostgreSQL :
   ```sql
   CREATE DATABASE microDemo;
   ```

   Dans pgAdmin :
   - Allez dans la section "Databases".
   - Faites un clic droit et sélectionnez "Create" > "Database".
   - Entrez `microDemo` comme nom de la base de données.

4. **Attribuer tous les privilèges à l'utilisateur `hrgres` sur la base de données `microDemo`** :

   Si vous utilisez l'invite de commande PostgreSQL :
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE microDemo TO hrgres;
   ```

   Dans pgAdmin :
   - Allez dans la section "Object", sélectionnez "Database", puis choisissez `microDemo`.
   - Allez dans "Properties" et sous "Privileges", attribuez tous les privilèges à l'utilisateur `hrgres`.

# 2. Configurer les propriétés de connexion dans l'application

1. **Ouvrez le fichier `application.properties` (ou `application.yml`)** dans votre éditeur de code et ajoutez/modifiez les configurations suivantes :

    ```properties
    spring.datasource.driverClassName=org.postgresql.Driver
    spring.datasource.url=jdbc:postgresql://localhost:5432/microDemo
    spring.datasource.username=hrgres
    spring.datasource.password=hrgres
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

    server.port=8080
    ```

   > **Remarque :** Assurez-vous que les informations de connexion (`url`, `username`, `password`) correspondent à votre configuration locale.

# Lancer l'Application

1. **Nettoyer le projet avec Maven** :
    ```bash
    mvn clean
    ```

2. **Compiler et installer les dépendances sans exécuter les tests** :
    ```bash
    mvn install -DskipTests
    ```

3. **Lancer l'application avec Spring Boot** :
    ```bash
    mvn spring-boot:run
    ```

# Résolution des Problèmes : Port 8080 Occupé

Si vous rencontrez une erreur indiquant que le port 8080 est déjà utilisé, suivez ces étapes :

1. **Vérifier les processus utilisant le port 8080** :

   Ouvrez l'invite de commande en tant qu'administrateur et exécutez :
   ```bash
   netstat -ano | findstr :8080
   ```

2. **Terminer le processus occupant le port 8080** :

   Exécutez la commande suivante en remplaçant `numero_pid` par l'ID du processus récupéré :
   ```bash
   taskkill /F /PID numero_pid
   ```

3. **Relancer l'application** :
    ```bash
    mvn spring-boot:run
    ```





---
# Partie 2 : Gestion des Comptes sans Auto-Incrément et Ajout de Clients
---

- Dans cette partie, nous allons gérer les comptes bancaires associés aux clients, en respectant certaines contraintes, notamment l'absence d'auto-incrémentation pour les comptes. 
- Assurez-vous de bien comprendre les relations entre les clients et les comptes avant de commencer.

# Remarque Importante :
Avant de commencer cette partie, il est essentiel de comprendre la relation entre les `accounts` et les `customers`. **Nous ne pouvons pas créer un compte sans avoir de clients.** C'est pourquoi il est impératif de commencer par la Partie 4 (ajout de 20 clients) avant de revenir ici pour tester les comptes. Vous devez ajouter les 20 clients avant de passer à la gestion des comptes.

# Objectif :
Créer 12 comptes via des requêtes POST selon la répartition suivante :
- **Client 1** : comptes 1, 11, 21
- **Client 2** : comptes 2, 12, 22
- **Client 3** : comptes 3, 13, 23
- **Client 4** : comptes 4, 14, 24

# Étapes:

1. **Insertion des 20 Clients via SQL (requêtes dans la Partie 4)**

   Commencez par insérer les clients dans la base de données en suivant les étapes ci-dessous :

   1. **Accéder à PostgreSQL** :
      - Allez dans pgAdmin ou ouvrez l'invite de commande PostgreSQL.

   2. **Insérer les 20 clients dans la table `customer`** :
      ```sql
      INSERT INTO public.customer (customer_id, create_dt, email, mobile_number, name)
      VALUES
      (1, '2023-01-01', 'email1@example.com', '1000000001', 'Name One'),
      (2, '2023-01-02', 'email2@example.com', '1000000002', 'Name Two'),
      (3, '2023-01-03', 'email3@example.com', '1000000003', 'Name Three'),
      (4, '2023-01-04', 'email4@example.com', '1000000004', 'Name Four'),
      (5, '2023-01-05', 'email5@example.com', '1000000005', 'Name Five'),
      (6, '2023-01-06', 'email6@example.com', '1000000006', 'Name Six'),
      (7, '2023-01-07', 'email7@example.com', '1000000007', 'Name Seven'),
      (8, '2023-01-08', 'email8@example.com', '1000000008', 'Name Eight'),
      (9, '2023-01-09', 'email9@example.com', '1000000009', 'Name Nine'),
      (10, '2023-01-10', 'email10@example.com', '1000000010', 'Name Ten'),
      (11, '2023-01-11', 'email11@example.com', '1000000011', 'Name Eleven'),
      (12, '2023-01-12', 'email12@example.com', '1000000012', 'Name Twelve'),
      (13, '2023-01-13', 'email13@example.com', '1000000013', 'Name Thirteen'),
      (14, '2023-01-14', 'email14@example.com', '1000000014', 'Name Fourteen'),
      (15, '2023-01-15', 'email15@example.com', '1000000015', 'Name Fifteen'),
      (16, '2023-01-16', 'email16@example.com', '1000000016', 'Name Sixteen'),
      (17, '2023-01-17', 'email17@example.com', '1000000017', 'Name Seventeen'),
      (18, '2023-01-18', 'email18@example.com', '1000000018', 'Name Eighteen'),
      (19, '2023-01-19', 'email19@example.com', '1000000019', 'Name Nineteen'),
      (20, '2023-01-20', 'email20@example.com', '1000000020', 'Name Twenty');
      ```

   3. **Vérifier l'insertion des clients** :
      ```sql
      SELECT * FROM public.customer;
      ```

2. **Insertion des Comptes via HTTP (POST)**

   Une fois les 20 clients ajoutés, vous pouvez commencer à créer les comptes associés via des requêtes POST.

   1. **Créer un compte pour un client via Postman** :
   
      Exemple pour créer un compte pour le Client 1 :
      - Ouvrez Postman.
      - Choisissez `POST`.
      - URL : `http://localhost:8080/newAccount`.
      - Body -> raw -> JSON :
        ```json
        {
          "accountNumber": 1,
          "customerId": 1,
          "accountType": "Checking",
          "branchAddress": "123 Main St",
          "createDt": "2023-01-02"
        }
        ```

   2. **Vérifier l'insertion du compte** :
      - Ouvrir le navigateur et tester cette URL : `http://localhost:8080/accounts`.
      - Ou utilisez Postman pour effectuer un GET sur `http://localhost:8080/accounts`.

   3. **Répéter l'opération pour les autres comptes** :
   
      Exemple pour créer un compte pour le Client 2 :
      ```json
      {
        "accountNumber": 2,
        "customerId": 2,
        "accountType": "Savings",
        "branchAddress": "456 Main St",
        "createDt": "2023-01-03"
      }
      ```

- Testez : `http://localhost:8080/accounts
- Ouvrir le navigateur et tester cette URL : `http://localhost:8080/accounts`.
- Ou utilisez Postman pour effectuer un GET sur `http://localhost:8080/accounts`.
- Continuez ainsi pour les comptes restants.



### Comptes pour le **Client 1** :
- **Compte 1** :
  ```json
  {
    "accountNumber": 1,
    "customerId": 1,
    "accountType": "Checking",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  }
  ```
- **Compte 11** :
  ```json
  {
    "accountNumber": 11,
    "customerId": 1,
    "accountType": "Savings",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  }
  ```
- **Compte 21** :
  ```json
  {
    "accountNumber": 21,
    "customerId": 1,
    "accountType": "Checking",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  }
  ```

### Comptes pour le **Client 2** :
- **Compte 2** :
  ```json
  {
    "accountNumber": 2,
    "customerId": 2,
    "accountType": "Savings",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  }
  ```
- **Compte 12** :
  ```json
  {
    "accountNumber": 12,
    "customerId": 2,
    "accountType": "Checking",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  }
  ```
- **Compte 22** :
  ```json
  {
    "accountNumber": 22,
    "customerId": 2,
    "accountType": "Savings",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  }
  ```

### Comptes pour le **Client 3** :
- **Compte 3** :
  ```json
  {
    "accountNumber": 3,
    "customerId": 3,
    "accountType": "Checking",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  }
  ```
- **Compte 13** :
  ```json
  {
    "accountNumber": 13,
    "customerId": 3,
    "accountType": "Savings",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  }
  ```
- **Compte 23** :
  ```json
  {
    "accountNumber": 23,
    "customerId": 3,
    "accountType": "Checking",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  }
  ```

### Comptes pour le **Client 4** :
- **Compte 4** :
  ```json
  {
    "accountNumber": 4,
    "customerId": 4,
    "accountType": "Savings",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  }
  ```
- **Compte 14** :
  ```json
  {
    "accountNumber": 14,
    "customerId": 4,
    "accountType": "Checking",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  }
  ```
- **Compte 24** :
  ```json
  {
    "accountNumber": 24,
    "customerId": 4,
    "accountType": "Savings",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  }
  ```

# Récapitulatif
- **Client 1** : Comptes 1, 11, 21
- **Client 2** : Comptes 2, 12, 22
- **Client 3** : Comptes 3, 13, 23
- **Client 4** : Comptes 4, 14, 24


- **Test des Endpoints via Navigateur et Postman** : Ouvrez le navigateur et testez les endpoints avec l'URL `http://localhost:8080/accounts`. Si tout est configuré correctement, cette URL devrait être vide si aucun compte n'a été créé.
  
---
# Résumé :
---

### Insertions et Tests Supplémentaires :

1. **Insertion de Clients (Clé Secondaire dans la BD)**

   - **Allez à Postgres -> Schéma -> Tables -> Customers -> Clique Droit -> Scripts -> Insert Scripts**
   - **Insérez 20 clients comme spécifié précédemment.**

2. **Insertion d'un Compte (Account) via POST** :

   - **Allez à POSTMAN**
   - **Choisir POST -> URL : `http://localhost:8080/newAccount` -> Body -> raw -> JSON**

3. **Vérification via GET** :

   - **Vérifiez l'insertion via l'URL `http://localhost:8080/accounts` ou via POSTMAN GET `http://localhost:8080/accounts`**.

---

















### Mise à Jour d'un Compte (PUT)

1. **Vérifiez le Controller** :
   - **Vérifiez votre controller `AccountsController` pour le mapping PUT** (`@PutMapping("/update/{id}")`).

2. **Vérification et Mise à Jour** :

   - **Utilisez GET pour vérifier les données existantes** (`http://localhost:8080/accounts`).
   - **Choisissez de mettre à jour le premier compte via PUT** : 
   ```json
   {
     "accountNumber": 1,
     "customerId": 3,
     "accountType": "Savings",
     "branchAddress": "123 Main St",
     "createDt": "2024-01-02"
   }
   ```
   - **Vérifiez la mise à jour via l'URL `http://localhost:8080/accounts` ou POSTMAN GET `http://localhost:8080/accounts`**.

---

### Suppression d'un Compte Existante (DELETE)

1. **Suppression d'un Compte** :

   - **Choisissez DELETE -> URL : `http://localhost:8080/deleteAccount/1` (Pas Besoin de Body)**.
   - **Vérifiez la suppression via l'URL `http://localhost:8080/accounts` ou POSTMAN GET `http://localhost:8080/accounts`**.

2. **Suppression du Deuxième Compte** :

   - **Répétez l'opération pour le deuxième compte**.

---

### Conclusion

1. **Vérification Continue des Endpoints** : Testez régulièrement les endpoints dans vos controllers.
2. **Importance du GET pour Vérification** : Utilisez toujours les méthodes GET pour vérifier l'état des données après chaque opération.
3. **Consultation via PostgreSQL** : Vérifiez également les données directement au niveau de la base de données PostgreSQL.
4. **Gestion des Corps de Requête** : N'oubliez pas que pour les méthodes GET et DELETE, il n'y a pas besoin de spécifier un body, contrairement aux méthodes POST et PUT.

---

### Prochains Défis :

1. **Documentation via Swagger** : Implémentez et testez la documentation de votre API avec Swagger.
2. **Compréhension de la Structure MVC** : Approfondissez la compréhension de la structure `controller -> services -> repository -> BD`.
3. **Gestion de l'Auto-Incrémentation de AccountID** : Explorez l'impl

émentation de l'auto-incrémentation pour les `accountId`.
4. **Insertion en Lots (Batch Processing)** : Implémentez l'insertion de comptes en masse via des opérations batch.

---

### Évaluation Formative

1. **Insertion Multiple en Lots** :
   - Objectif : Créer 12 comptes via POST.
   - **URL** : `http://localhost:8080/newAccounts`
   - **Body** : Passez une liste d'objets JSON représentant les comptes à créer.

2. **Suppression en Masse** :
   - Objectif : Implémenter la suppression de tous les comptes d'un seul coup.
   - **URL** : `http://localhost:8080/deleteAllAccounts`
   - **Méthode** : DELETE (pas de body requis).

3. **Mise à Jour Multiple** :
   - Objectif : Mettre à jour plusieurs comptes en une seule opération.
   - **URL** : `http://localhost:8080/updateAccounts`
   - **Body** : Liste d'objets JSON représentant les comptes à mettre à jour.

4. **Ajout de Validation Personnalisée** :
   - Objectif : Ajouter une validation pour s'assurer que tous les comptes créés en lot appartiennent au même client.

5. **Recherche en Masse** :
   - Objectif : Ajouter la fonctionnalité de recherche de comptes en passant une liste d'IDs.

---

Ces exercices sont conçus pour renforcer votre compréhension de la gestion des données en lot, des opérations CRUD, de la validation, et de la gestion des transactions et des exceptions dans une application Spring Boot.

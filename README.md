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

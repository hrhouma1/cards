# 🏁 Partie 1 : Création en Lot des Comptes Bancaires

Dans cette partie, nous allons aborder l'insertion multiple de comptes pour différents clients via des requêtes POST en une seule opération. Cette approche vous permet de gérer efficacement les données tout en minimisant les risques d'erreurs.

## 🚀 Étapes à suivre :

### 1️⃣ Objectif : Insertion Multiple en Lots

Nous allons créer 12 comptes via une requête POST pour les clients existants, selon la répartition suivante :
- **Client 1** : comptes 1, 11, 21
- **Client 2** : comptes 2, 12, 22
- **Client 3** : comptes 3, 13, 23
- **Client 4** : comptes 4, 14, 24

### 2️⃣ Préparation de la Requête POST

#### 2.1 **URL de l'API**
```plaintext
POST http://localhost:8080/newAccounts
```

#### 2.2 **Corps de la Requête (Body)**
Préparez un corps JSON pour inclure les informations sur chaque compte à créer :

```json
[
  {
    "accountNumber": 1,
    "customerId": 1,
    "accountType": "Checking",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  },
  {
    "accountNumber": 11,
    "customerId": 1,
    "accountType": "Savings",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  },
  {
    "accountNumber": 21,
    "customerId": 1,
    "accountType": "Checking",
    "branchAddress": "123 Main St",
    "createDt": "2023-01-02"
  },
  {
    "accountNumber": 2,
    "customerId": 2,
    "accountType": "Savings",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  },
  {
    "accountNumber": 12,
    "customerId": 2,
    "accountType": "Checking",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  },
  {
    "accountNumber": 22,
    "customerId": 2,
    "accountType": "Savings",
    "branchAddress": "456 Main St",
    "createDt": "2023-01-03"
  },
  {
    "accountNumber": 3,
    "customerId": 3,
    "accountType": "Checking",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  },
  {
    "accountNumber": 13,
    "customerId": 3,
    "accountType": "Savings",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  },
  {
    "accountNumber": 23,
    "customerId": 3,
    "accountType": "Checking",
    "branchAddress": "789 Elm St",
    "createDt": "2023-01-04"
  },
  {
    "accountNumber": 4,
    "customerId": 4,
    "accountType": "Savings",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  },
  {
    "accountNumber": 14,
    "customerId": 4,
    "accountType": "Checking",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  },
  {
    "accountNumber": 24,
    "customerId": 4,
    "accountType": "Savings",
    "branchAddress": "101 Pine St",
    "createDt": "2023-01-05"
  }
]
```

---

## 🛠️ Implémentation de l'Insertion Multiple

### 1️⃣ Modification du `AccountsController` pour gérer l'insertion en lot

Ajoutez une méthode dans `AccountsController` pour accepter une liste d'objets `Accounts` et les enregistrer via le service approprié.

```java
@PostMapping("/newAccounts")
public List<String> newAccounts(@RequestBody List<Accounts> accountsList) {
    return accountsService.saveAll(accountsList);
}
```

### 2️⃣ Implémentation du service dans `AccountsService`

Dans `AccountsService`, ajoutez une méthode pour enregistrer tous les comptes fournis. Cette méthode assure l'intégrité des données en utilisant une transaction.

```java
@Transactional
public List<String> saveAll(List<Accounts> accountsList) {
    List<String> responseList = new ArrayList<>();
    for (Accounts account : accountsList) {
        try {
            accountsRepository.save(account);
            responseList.add("Compte enregistré avec succès : " + account.getAccountNumber());
        } catch (Exception e) {
            responseList.add("Échec d'enregistrement pour le compte : " + account.getAccountNumber() + " - Erreur : " + e.getMessage());
        }
    }
    return responseList;
}
```

> **💡 Remarque :** L'utilisation de l'annotation `@Transactional` permet de garantir que toutes les opérations sont atomiques. En cas d'erreur, toutes les insertions sont annulées.

---

## 🏃‍♂️ Test et Vérification

### 1️⃣ Utiliser Postman pour tester l'insertion en lot

- **URL** : `http://localhost:8080/newAccounts`
- **Méthode** : POST
- **Body** : Copiez le JSON précédemment préparé.

### 2️⃣ Vérification dans la base de données

Après l'insertion, vérifiez que les comptes ont bien été créés en exécutant la commande suivante dans PostgreSQL :

```sql
SELECT * FROM public.accounts WHERE customer_id IN (1, 2, 3, 4);
```

---

# 🏁 Partie 2 : Suppression en Masse des Comptes

Dans cette partie, nous allons implémenter une fonctionnalité permettant de supprimer tous les comptes bancaires en une seule opération.

## 🚀 Étapes à suivre :

### 1️⃣ Objectif : Suppression en Masse

Ajouter une méthode dans `AccountsController` pour supprimer tous les comptes enregistrés.

### 2️⃣ Implémentation

#### 2.1 **Modification du `AccountsController`**

Ajoutez une méthode `DELETE` pour supprimer tous les comptes via le service approprié.

```java
@DeleteMapping("/deleteAllAccounts")
public String deleteAllAccounts() {
    return accountsService.deleteAllAccounts();
}
```

#### 2.2 **Modification du `AccountsService`**

Dans `AccountsService`, ajoutez une méthode pour supprimer tous les comptes et gérer les erreurs éventuelles.

```java
public String deleteAllAccounts() {
    try {
        accountsRepository.deleteAll();
        return "Tous les comptes ont été supprimés avec succès";
    } catch (Exception e) {
        return "Erreur lors de la suppression des comptes : " + e.getMessage();
    }
}
```

> **💡 Remarque :** Cette méthode supprime tous les comptes de la base de données. Assurez-vous d'avoir une copie de sauvegarde avant d'exécuter cette opération en environnement de production.

---

## 🏃‍♂️ Test et Vérification

### 1️⃣ Utiliser Postman pour tester la suppression en masse

- **URL** : `http://localhost:8080/deleteAllAccounts`
- **Méthode** : DELETE

### 2️⃣ Vérification dans la base de données

Après la suppression, vérifiez que les comptes ont bien été supprimés en exécutant la commande suivante dans PostgreSQL :

```sql
SELECT * FROM public.accounts;
```

---

# 🏁 Partie 3 : Exercice de Validation Avancée

## 🚀 Exercices à Compléter

### 1️⃣ Suppression en Lot des Comptes
- **Objectif** : Implémentez la suppression de plusieurs comptes à la fois en fournissant une liste d'identifiants.
- **URL** : `DELETE http://localhost:8080/deleteAccounts`
- **Body** : `[1, 2, 3, 4]` // Liste des identifiants de comptes à supprimer.

### 2️⃣ Mise à Jour Multiple
- **Objectif** : Mettre à jour plusieurs comptes en une seule opération.
- **URL** : `PUT http://localhost:8080/updateAccounts`
- **Body** : Liste d'objets JSON représentant les comptes à mettre à jour.

### 3️⃣ Ajout de Validation Personnalisée
- **Objectif** : Ajouter une validation pour s'assurer que tous les comptes créés en lot appartiennent au même client.

### 4️⃣ Recherche en Masse
- **Objectif** : Ajouter la fonctionnalité de recherche de comptes en passant une liste d'IDs.
- **URL** : `GET http://localhost:8080/findAccounts`
- **Body** : `[1, 2, 3, 4]` // Liste des identifiants de comptes à rechercher.

---

Ces exercices sont conçus pour renforcer votre compréhension de la gestion des données en lot, des opérations CRUD, de la validation, et de la gestion des transactions et des exceptions dans une application Spring Boot.


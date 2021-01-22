# **Gamificator**

![Gamificator](images/welcome.jpg)

## Implémentation

## Signature

Pour authentifier et authorisé un _client_ à faire une requête sur notre api, il dois construire une signature et l'envoyer avec sa requête.

### Calcule

Le calcule est inspiré de ce que fait [AWS](https://docs.aws.amazon.com/AmazonS3/latest/dev/RESTAuthentication.html) pour authentifer ces clients.

Pour le calcule de la signature, nous avons besoin de la _key_ et du _secret_ forunis quand on créer une appliaction.

Exemple JS: 

```javascript
 const shaObj = new jsSHA("SHA-1", "TEXT", {
            hmacKey: { value: secret, format: "TEXT" },
 });
 const url = req.url.split('?')[0]; // On ignore la querystring
 const data = `${key}${url}`;
 shaObj.update(data);
 const hmacHEX = shaObj.getHash("HEX");
 const hmac64 = window.btoa(hmacHEX);
```

## Transfer

Pour envoyer la _key_ et la _signature_ , il faut le faire dans les headers, Dans respectivement le header `x-api-key` pour la key et `signature` pour la signature.

## Swagger

Pour implémenter la signature dans _swagger_ , il a fallu customizer le _swagger-ui_ pour y inclure un `requestInterceptor`.

```javascript
requestInterceptor: req => {
          const apiKeyheader = req.headers['x-api-key'];
          if(!apiKeyheader){
            return;
          }

          const key = apiKeyheader.split(":")[0];
          const secret = apiKeyheader.split(":")[1];

          const shaObj = new jsSHA("SHA-1", "TEXT", {
            hmacKey: { value: secret, format: "TEXT" },
          });
          const url = req.url.split('?')[0];
          const data = `${key}${url}`;
          shaObj.update(data);
          const hmacHEX = shaObj.getHash("HEX");
          const hmac64 = window.btoa(hmacHEX);

          req.headers['x-api-key']= key;
          req.headers.signature = hmac64;
          return req;
        },
```



Pour ce faire, on a pris la version buildé de swagger-ui et placé son dossier dans le dossier `ressources` pour qu'il soit servit statiquement par notre serveur spring. 

## Comment ...

### ... lancer l'application

1. Lancer le docker-compose qui se trouve dans `docker/environment/dev` avec `docker-compose up`

2. Dans le dossier `impl`:

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

### ... lancer l'application avec docker

1. S'assurer d'avoir la dernière version des image avec `docker-compose pull`
2. Dans le dossier `docker/environment/prod`, lancer le docker-compose avec `docker-compose up`

### ... lancer les tests Cucumber

1. S'assurer d'avoir une version du serveur qui tourne (voir au dessus)

2. Dans le dossier `specs`:

   ```bash
   mvn clean test
   ```

### ... lancer les tests de charges

1. S'assurer d'avoir un serveur qui tourne
2. Ouvir le ficher `gameificatorTest.jmx` du dossier `jmeter` avec _Apache Jmeter_ et lancer les tests (Avec le petit bouton play)

## Problèmes connus

### la pagination

Le leaderboard pour un pointScale donné est paginé pour ne pas récupérer tout les utilisateurs d'un seul coup. Pour ce faire nous utilisons un `PagingAndSortingRepository` sur l'entité `User`. Le soucis est que nous calculons les scores des utilisateurs seulement après avoir récupéré les `User`. Nous nous trouvons donc dans une situation ou chaque _page_ est triée par le score décroissant des users. Mais les pages ne sont pas triès entres elles. Donc la page 3 pourrait avoir des scores de `User` supérieurs à ceux de la page 1.

La correction de ce problème passerait par l'écriture d'une requête en `jpql` pour calculer directement le score des users en fonction d'un pointScale donnée et sur ce résultat on applique la pagination avec le ``PagingAndSortingRepository`.

## Contributeurs

- Simon Walther - simon.walther@heig-vd.ch
- Didier Page - didier.page@heig-vd.ch
- Eric Noel - eric.noel@heig-vd.ch
- Guillaume Laubscher -  guillaume.laubscher@heig-vd.ch
- Bruno Legrand - bruno.legrand@heig-vd.ch
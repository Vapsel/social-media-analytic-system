# Configuration
### How to start application server
```
cd manual-config-spring-mvc
gradle bootRun
```

### How to set up database

1. Install latest version of PostgreSQL (tested with server 9.6.1).
1. Create database (preferred name is *smas*).
1. Chose username and password to access to database (*postgres* username is default).
1. Set *SMAS_DATABASE_USERNAME* system variable with your username.
1. Set *SMAS_DATABASE_PASSWORD* system variable with your password.
1. You can set username and password directly in the /smas-core/src/main/resources/database_application.properties
1. Database schema will be created automatically during application server start up

# Examples
#### Test report of usage:
Twitter account: [@vapsel21](https://twitter.com/vapsel21?lang=en)  
Dictionary: rower, Kraków, pasja, sport, komputer, Java, kawa, podróż, AGH, samochód, auto, samolot, pizza, burger,
 herbata, cafe, restauracja, jedzenie, hackathon, soft, narty, góry, piłka, football, basketball, UEK, student, zima, 
 lato, wiosna, jesień, morze, plaż, wspinaczka.  
Found preferences: AGH (12), UEK (2), Kraków (1).

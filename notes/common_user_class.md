Support for tourist offers in Poland only, therefore location are checking in Poland and Polish only.

###### Problem: how to detect unlike facilities?
###### How to detect dependencies between preferences?

Preferences will be found by parsing users's posts, liked posts or user's group names, if another isn't specified.

Common UserPreferencesData class may content:

| Preference name | Description | Search way | Data type |
| :-------------: | :---------- | :--------- | :-------- |
| **Locations** | User's home town and all locations that will be fined in liked posts or user's group name. | Predefined dictionary of ~10 000 biggest cities. | HashSet<String> as *contains* operation will be called frequently. String will represent city name e.g. "Kraków", "Warszawa". |
| **Active sports** | Count of each predefined sport occurrences. Most popular sports will be used in further calculation. Also may be used to recommend sport events as football matches or racing. | Predefined dictionary of sports. Polish names will be defined. Few keywords may be defined for one sport e.g.: swimming ("pływanie", "basen"), racing ("wyścigi"), football ("piłka", "futbol"). | HashSet<SportIdInteger, Integer> Additional enum (of integers) may be created to store sport id. |
| **Cuisine** | User's preferred food may be implemented in similar way as active sports i.e. count of occurrences. | Predefined set of dishes for each cuisine. |
   

     
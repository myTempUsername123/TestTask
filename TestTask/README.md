Aplikacja do działania wymaga:
1. bazy danych postgresql
2. ustwienia wartości jta-data-source,
                      javax.persistence.jdbc.url,
                      javax.persistence.jdbc.user,
                      javax.persistence.password  
    w pliku:  
    /src/main/resources/META-INF/persistence.xml
    dla posiadanej bazy danych 
3. serwera (zalecam WildFly'a)
4. opcjonalnie po pierwszym uruchomieniu mozna zmienic wartosc hibernate.hbm2ddl.auto na update lub validate

Działanie:
    sciezka dostepu: {host}{port}/StoringDataRestApplication-1.0-SNAPSHOT/webresources/url

    rzadania:
        GET: zwraca wszystkie adresy url
        GET pod adresem "/id/{id}": zwraca plik o id podanym w url
        POST: przyjmuje obiekt typu Json z Stringiem ktory zawiera url pliku do pobrania
        POST pod adresem "/list": przyjmuje obiekt Json z lista Stringow jako adresy plikow do pobrania
    

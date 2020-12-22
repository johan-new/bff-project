### Struktur på JSON
Strukturen på JSON-objektet är detsamma oavsett om du ska uppdatera användarens information eller hämta den.
```
{
  "username": "erik@a.a",
  "presentation": "hej världen här är jag",
  "gender": "FEMALE",
  "city": "Lerum",
  "age": 100
}
```

* ```"games"``` kan bara hämtas via GET, inte ändras via PUT.
* lösenord kan inte hämtas, endast ändras genom ```"newPassword"```/```"oldPassword"```
* I dagsläget görs ingen kontroll om gamla lösenordet stämmer innan det ändras till det nya.
* användarnamn, alltså `username` kan inte ändras



`GET /loggedinuser`
Hämtar information om inloggad användare. Ex

``` 
{
  "games": {
    "1": {
      "venue": "Göteborg",
      "players": "[Johan@mail.com, Erik@mail.com, Simon@mail.com]",
      "id": "1",
      "when": "2020-12-10 09:18:41.368"
    }
  },
  "username": "Johan@mail.com"
}
  ```

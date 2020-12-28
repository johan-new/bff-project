### Struktur på JSON
Strukturen på JSON-objektet är detsamma oavsett om du ska uppdatera användarens information eller hämta den.

Godkända parametrar
```
oldPassword
newPassword
presentation
city
age
gender
```


* ```"games"``` kan bara hämtas via GET, inte ändras via PUT.
* lösenord kan inte hämtas, endast ändras genom ```"newPassword"```/```"oldPassword"```
* användarnamn, alltså `username` kan inte ändras

`PUT /user` Ändrar info om den inloggade användaren på den parametrar man fyller i
```
{
	"presentation": "hej världen här är jag",
	"gender": "FEMALE",
	"city": "Lerum",
	"age": 100
}
```
```
{
	"oldPassword": "gammalt",
	"newPassword": "nytt"
}
```


`GET /loggedinuser`
Hämtar information om inloggad användare. Ex

``` 
{
  "username": "johan@mail.com",
  "gender": "MALE",
  "city": "Lerum",
    "games": {
    "1": {
      "venue": "Göteborg",
      "players": "[Johan@mail.com, Erik@mail.com, Simon@mail.com]",
      "id": "1",
      "when": "2020-12-10 09:18:41.368"
    }
  }
}
  ```

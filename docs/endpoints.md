
### End-points

`GET /notifications`
Hämtar notiser för inloggad användare. Ex

``` 
{
    "-169041602": 
    {
      "type": "MATCH_SUCCESS",
      "content": "[Simon, Johan, Greven, Erik]",
      "timestamp": "Thu Dec 10 08:27:54 CET 2020"
    },
    "-1179904300": {
      "type": "GENERAL",
      "content": "Detta är en notis!",
      "timestamp": "Thu Dec 10 08:27:54 CET 2020"
    }
  } 
  ```
  
  
`GET /loggedinuser`
Hämtar notiser för inloggad användare. Ex

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

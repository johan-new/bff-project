`GET /notifications`
Hämtar notiser för inloggad användare. Ex

``` 
{
    "-169041602": 
    {
      "type": "MATCH_SUCCESS",
      "content": "[Simon, Johan, Greven, Erik]",
      "time": "17:00:00.0",
      "date": "2021-01-01"
    },
    "-1179904300": {
      "type": "GENERAL",
      "content": "Detta är en notis!",
      "time": "17:00:00.0",
      "date": "2021-01-01"
    }
  } 
  ```
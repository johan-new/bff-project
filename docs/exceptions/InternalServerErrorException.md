### Struktur för Internal Server Error Exceptions 
Ett annat exempel på ett exception vi har implementerat är när man ska läsa in en inloggad användare och det
inte går.

Då kastar vi detta ``exception``:
```
{
        "statusCode": 500,
        "timestamp": "2021-01-18T12:37:30.588+00:00",
        "message": "Cannot read logged in user!",
        "description": "uri=/user/"
}
```

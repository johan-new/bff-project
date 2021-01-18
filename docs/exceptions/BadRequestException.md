### Struktur för Bad Request Exceptions 

Ett exempel där vi har implementerat den här så kan man inte i vårt program skriva in ett 
användarnamn utan att ha en giltig e-post adress som användarnamn. ``Skriver man följande:``
```
}
        "username": "carola",
        "password": "ola"
}

```

Då kastar vi detta ``exception``:
```
{
        "statusCode": 400,
        "timestamp": "2021-01-18T12:37:30.588+00:00",
        "message": "Invalid email address!",
        "description": "uri=/user/"
}
```



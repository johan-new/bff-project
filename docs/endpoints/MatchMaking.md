`POST /game` 
Lägger till en matchrequest i kön
Notera att antal deltagare som anges är 1-3 stycken. Organisatören läggs til lautomatiskt, och antas alltså att delta i spelet.

``` 
{
	"Göteborg": [
		{ 
			"username": "e@e.e",
			"date": "2020-12-24",
			"time": "18:30",
			"reservation": false,
			"venue": "GLTK",
            "location": "Göteborg",
			"participants": 3
		},
		{ 
			"username": "s@s.s",
			"date": "2020-12-24",
			"time": "16:30",
			"reservation": false,
			"venue": "MBB",
            "location": Göteborg,
			"participants": 2
		}
	]
}

``` 

`POST match/request`

Organisatören för matchningsförfrågan 904 vill acceptera förfrågan att gå med från en annan
spelare. Denna förfrågan har idnummer 1. Endast organisatören kan acceptera("accept") eller avvisa ("reject").

```
{
	"matchingRequestId": 904,
	"joinRequestId": "1",
	"action": "accept"
}
```


`POST match/join`
Den inloggade användaren ber om att få delta i en MatchRequest med id 900.

```
{
	"requestId": 904
}
```

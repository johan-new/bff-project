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
			"participants": 3
		},
		{ 
			"username": "s@s.s",
			"date": "2020-12-24",
			"time": "16:30",
			"reservation": false,
			"venue": "MBB",
			"participants": 2
		},
		{ 
			"username": "b@b.b",
			"date": "2020-12-31",
			"time": "23:30",
			"reservation": true,
			"venue": "Hallen",
			"participants": 1
		},
	]
}

``` 

`POST /match/900`

Organisatören för matchningsförfrågan 900 vill acceptera förfrågan att gå med från en annan
spelare. Denna förfrågan har idnummer 3. Endast organisatören kan acceptera("accept") eller avvisa ("reject").

```
{
  "joinRequestId": 3
  "action": "accept"
}
```


`POST /match/asktojoin/900`
Den inloggade användaren ber om att få delta i en MatchRequest med id 900.

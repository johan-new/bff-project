`POST /game` 
Lägger till en matchrequest i kön

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

# Booking API

## Getting start

First thing first, you need to grant executable permission for setup.sh script

```shell
chmod +x setup.sh
```

Then we can run the script. This will automatic build postgresql database and application and start automatically.
```shell
./setup.sh
```

## API

OpenAPI UI: ```text http://localhost:8080/openapi/ui/```

For CREATE/UPDATE/DELETE booking, we need authentication, so go to Login API in OpenAPI UI and send this to get JWT:

![Alt text](/images/login.png "a title")
```json
{
  "username": "booking_user",
  "password": "booking_pwd"
}
```

Then just get the jwt:

![Alt text](/images/jwt.png "a title")

And login to OpenAPI

![Alt text](/images/login_to_openapi.png "a title")

1. Example data for create booking: 

```json
{
  "hotel": {
    "id": 54
  },
  "user": {
    "id": 120
  },
  "rooms": [
    {
      "id": 52
    },
    {
      "id": 53
    }
  ]
}
```

2. For update we can only update the rooms so example here:

```json
{
  "hotel": {
    "id": 54
  },
  "user": {
    "id": 120
  },
  "rooms": [
    {
      "id": 50
    },
    {
      "id": 51
    }
  ]
}
```
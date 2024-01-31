# Ktor, Postgresql, JWT, Exposed, Rest API

Build Restful CRUD API for a blog using Ktor, Postgresql, JWT, Exposed, Koin

## Steps to Set up

**1. Clone the application**

```bash
git clone https://github.com/vlad-bstrv/HairBookBackend.git
```

**2. Change postgres url, username and password as per your installation**

+ open `src/main/kotlin/com/vladbstrv/plugins/Databases.kt`
+ change `bdUrl`, `bdUser` and `dbPassword`

**4. Run the app**

The app will start running at <http://0.0.0.0:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url            | Decription | Sample Valid Request Body | 
|--------|----------------|------------| --------------------------- |
| POST   | /api/v1/signup | Sign up    | [JSON](#signup) |
| POST   | /api/v1/signin | Log in     | [JSON](#signin) |
| GET    | /api/v1/user   | User info  |  |

### Service

| Method | Url                             | Description       | Sample Valid Request Body |
| ------ |---------------------------------|-------------------|---------------------------|
| POST    | /api/v1/service                 |                   | [JSON](#createservice)    |
| GET    | /api/v1/service                 | Get all services  |                           |
| GET   | /api/v1/service/{id}            | Get service by id |    |
| PUT    | /api/v1/service                 | Update service    | [JSON](#serviceupdate)       |
| DELETE | /api/v1/service/{id}            | Delete service    |                           |

### Client

| Method | Url                            | Description                | Sample Valid Request Body |
| ------ |--------------------------------|----------------------------|---------------------------|
| POST    | /api/v1/client                |                            | [JSON](#createclient)     |
| GET    | /api/v1/client                | Get all clients            |                           |
| GET   | /api/v1/client/{phoneNumber}   | Get service by phoneNumber |                           |
| PUT    | /api/v1/client                | Update clients             | [JSON](#updateclient)     |
| DELETE | /api/v1/client/{id}           | Delete clients             |                           |

### WorkingDay

| Method | Url                             | Description       | Sample Valid Request Body |
| ------ |---------------------------------|-------------------|---------------------------|
| POST    | /api/v1/working-day                 |                   | [JSON](#working-daycreate)    |
| GET    | /api/v1/working-day                 | Get all working-day  |                           |
| GET   | /api/v1/working-day/{id}            | Get working-day by id |    |
| PUT    | /api/v1/working-day                 | Update working-day    | [JSON](#working-dayeupdate)       |
| DELETE | /api/v1/working-day/{id}            | Delete working-day    |                           |

### Appointment

| Method | Url                             | Description       | Sample Valid Request Body |
| ------ |---------------------------------|-------------------|---------------------------|
| POST    | /api/v1/appointment                 |                   | [JSON](#servicecreate)    |
| GET    | /api/v1/appointment                 | Get all appointment  |                           |
| GET   | /api/v1/appointment/{id}            | Get appointment by id |    |
| PUT    | /api/v1/appointment                 | Update appointment    | [JSON](#serviceupdate)       |
| DELETE | /api/v1/appointment/{id}            | Delete appointment    |                           |



## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /api/auth/signup</a>
```json
{
  "email": "mail@gmail.com",
  "login": "IvanIvanov",
  "password": "123",
  "firstName": "Ivan",
  "lastName": "Ivanov",
  "role": "admin"
}
```

##### <a id="signin">Log In -> /api/auth/signin</a>
```json
{
  "email": "mail@gmail.com",
  "password": "123"
}
```

##### <a id="servicecreate">Create Service -> /api/v1/service</a>
```json
{
  "name": "Классическая модельная стрижка",
  "price": 2000.0,
  "time": "01:00:00"
}
```

##### <a id="serviceupdate">Update Service -> /api/v1/service</a>
```json
{
  "id": 1,
  "name": "Классическая модельная стрижка",
  "price": 2000.0,
  "time": "01:00:00"
}
```

##### <a id="createclient">Create Client -> /api/v1/client</a>
```json
{
  "firstName": "Petr",
  "lastName": "Petrov",
  "phoneNumber": "89113334455",
  "comment": ""
}
```

##### <a id="client-daycreate">Update Client -> /api/v1/client</a>
```json
{
  "firstName": "Petr",
  "lastName": "Petrov",
  "phoneNumber": "89113334455",
  "comment": ""
}
```

##### <a id="working-daycreate">Create working-day -> /api/v1/working-day</a>
```json
{
  "date": "2024-01-07",
  "startTime": "12:00:00",
  "endTime": "19:00:00"
}
```

##### <a id="working-dayeupdate">Update working-day -> /api/v1/working-day</a>
```json
{
  "id": 1,
  "date": "2024-01-07",
  "startTime": "12:00:00",
  "endTime": "19:00:00"
}
```

##### <a id="appointmentcreate">Create appointment -> /api/v1/appointment</a>
```json
{
  "workingDayId": 1,
  "servicesId": [
    1,
    2
  ],
  "startTime": "13:00:00"
}
```

##### <a id="appointmentupdate">Update appointment -> /api/v1/appointment</a>
```json
{
  "id": 13,
  "workingDayId": 1,
  "servicesId": [
    1,
    2
  ],
  "startTime": "13:00:00"
}
```
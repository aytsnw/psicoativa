# Psicoativa
#### A backend API that allows clients to schedule appointments with psychologists.

## Disclaimer
This software was conceived as a didactic approach I've idealized as an aspiring software engineer.
Its main goal was to learn more about servlet containers (i.e. Tomcat), ORMs (Hibernate and more on
JPA annotations) and RDBMSs (PostgreSQL as per this project), some Design Patterns like *singletons*
as well as Layered Architecture, thus `I've chosen not to delegate any part of the code to AI`.

## Tech Stack
- Language: `Java 17`.
- ORM: `Hibernate` connected to `PostgreSQL` via xml configuration file, but compatible with most RDBMSs.
- Standalone `Tomcat 11.0.7` server. 
- `Jakarta Servlet API 6` based.
- `jBCrypt` for password encryption.
- `Jackson` for JSON serialization.

## Endpoints
- `GET /api/v1/client` - Allows *id* or *name* query as parameter and returns client information in JSON format.
#### 
- `GET /api/v1/psychologist` - Allows *id* or *name* query as parameter and returns psychologist information in JSON format.
####
- `GET /api/v1/appointment` - Accepts *appointemnt id* and returns appointment information in JSON format.
####
- `POST /api/v1/register` - User type specification (client | psychologist) necessary in order to forward request to proper servlet.
####
- `POST /api/v1/client` - Accepts client user information and stores it in database (*accessed via internal forwarding*).
####
- `POST /api/v1/psychologist` - Accepts psychologist user information and stores it in database *accessed via internal forwarding*.
####
- `POST /api/v1/appointment` - Accepts appointment information and stores it in database.
####
- `POST /api/v1/login`- User login via email and password.
####
- `PUT /api/v1/appointment` - Allows appointment canceling via *appointment id*.

## Key Features
- Appointments are granted to be 29 up to 59 minutes long.
- Collisions between appointments at the same time slot prevented by business logic.

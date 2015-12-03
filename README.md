This project contains the source code for the CineFiles web site.

# Building and Deploying

Maven is required to build. A local installation of Tomcat is required for running the application.

The following environment variables must be set:

- `CATALINA_BASE`

	The full path to the base directory of a local Tomcat instance, into which the web application will be deployed.

- `DB_HOST`

	The hostname of the database to use.

- `DB_PORT`

	The port number of the database to use.

- `DB_CINEFILES_SITE_USER`

	The name of the database user.

- `DB_CINEFILES_SITE_PASSWORD`

	The password of the database user.


To build and deploy the web application to a local Tomcat installation:

```
mvn clean install
```
### Cinefiles Web Site

This project contains the source code for the CineFiles web site.

Currently running at: https://cinefiles.bampfa.berkeley.edu/cinefiles/

### Building and Deploying

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


To build and deploy the web application to a local Tomcat installation, it _seems_ you'll need to clone the needed repos into `/src`, set some variables, and invoke `mvn`:

```
# .cinefiles_site contains the exports for the above environment variables
vi ~/.cinefiles_site
source ~/.cinefiles_site

# now build and deploy
cd src/cinefiles_jsp/
mvn clean install
```

To restart Tomcat after the build:

```
[app_cinefiles_site@cspace-xxx-nn ~]$ exit
logout

[xxx@cspace-xxx-nn ~]$ sudo service tomcat6-cinefiles-site restart
```

The crontab for running the backend refresh nightly and `cscheckmem`:

```
[app_cinefiles_site@cspace-prod-01 ~]$ crontab -l
1 1 * * 0 ~/src/cinefiles_jsp/src/main/scripts/update_featured.sh > /dev/null 2>&1
2 1 * * 1-5 ~/src/cinefiles_jsp/src/main/scripts/update_images.sh > /dev/null 2>&1
11 1 * * 1-5 ~/src/cinefiles_jsp/src/main/scripts/cinefiles_denorm_nightly.sh > /dev/null 2>&1
11 3 * * 1-5 ~/src/cinefiles_jsp/src/main/scripts/cinefiles_denorm_nightly.sh > /dev/null 2>&1
*/10 * * * * bash -l -c '~/bin/cscheckmem -dn >/dev/null 2>&1'

```

### Other fun facts

* There is no documentation (that we know of) that describes how to set up and
maintain this component.
* At least one of the nightly scripts seem to avail themselves of `curl` to fetch image files, and
uses the `-n` option, which says to look for username in passwrd in the `.netrc` file. That is,
`$HOME/.netrc`, it seems. 
* The layout of the home directory needed to deploy and run the system is not
described anywhere.
* Nor is the database schema that is required...

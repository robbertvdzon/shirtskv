Om te testen: start lokaal met:
docker run -p 80:80 -v c:\git\shirtskv\db\html:/var/www/html php:7.0-apache

Voor productie draait shirtskv gewoon op one.com (dus niet in docker).
Na het genereren van de html pagina's moeten deze gewoon de op oude manier geftp'ed worden naar ftp.shirtskv.nl.
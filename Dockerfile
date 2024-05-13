# Verwende das offizielle Apache-Image als Basis
FROM httpd:2.4

COPY ./html/ /usr/local/apache2/htdocs/

# Exponiere den Port 80
EXPOSE 80

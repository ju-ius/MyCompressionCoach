1. 
- docker build -t apache_image:1.0 .

2.
- docker run --name apacheserver -d -p 80:80 apache_image:1.0
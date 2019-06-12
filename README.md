# multi-tenant-rest
Sample Project Of SpringBoot , Spring JDBC Template, Custom ORM, Multitenant, Runtime Database change, Swagger, oAuth2


http://localhost:8080/multi-tenant/oauth/token?username=admin__mt-tenant&password=admin&grant_type=password
Headears:
X-TENANT-ID: mt-tenant
Accept-Language: en


Basic_Auth
username: MTClient
password: MTSecret


http://localhost:8080/multi-tenant/user/all
Headears:
X-TENANT-ID: mt-tenant
Accept-Language: en
Authorization: Bearer <paste access token here got from previous request>




## Set up sharepoint on windows 10

Instalar nodejs via nvm con la version 16.6

node --version
npm install gulp-cli yo @microsoft/generator-sharepoint --location=global

//gulp trust-dev-cert 

crear una carpeta code
md code
entrar a code
cd code

ejecutar lo siguiente:
yo @microsoft/sharepoint

md webpart
cd webpart

yo @microsoft/sharepoint

creamos el nombre del proyecto:
MyFirstWebPart

seleccionamos WEBPART y dejamos el nombre por DEFAULT.

seleccionamos NO FRAMEWORK.

gulp trust-dev-cert

aceptar el mensaje del certificado que se muestra.

abrir el proyecto en VSCode
code .

gulp serve --nobrowser
(en caso de que de error realizar el siguiente comando)
set-executionpolicy bypass -scope process

gulp serve --nobrowser
dar permisos de red de windows firewall

En la terminal:
Control + shift + B
Elegir la opcion npm build.
Ir al navegador y copiar el siguiente link en el cual sustituir {tenantDomain} por nuesto usuario admin. 
"https://{tenantDomain}/_layouts/workbench.aspx"

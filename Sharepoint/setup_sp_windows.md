## Set up sharepoint on windows 10

Instalar nodejs via nvm con la version 16.18.1

nvm -l
nvm install 16.18.1
nvm use 16.18.1

node -v

npm install gulp-cli yo @microsoft/generator-sharepoint --location=global


Abrimos una cmd y ejecutamos lo siguiente:

npm install gulp-cli --global
npm install yo --global
npm install  @microsoft/generator-sharepoint --global
npm install gulp --global

En la siguiente ruta se encuentra las versiones de nodejs que tenemos instalado:
C:\Users\[Vuestro Usuario]\AppData\Roaming\nvm

Ahora ejecutar lo suguiente en cmd(este comando debe dar error)
gulp trust-dev-cert


Crearemos un proyecto de prueba:

md nombre_proyecto

acceder a la carpeta del nuevo proyecto:
cd nombre_proyecto

Dentro del projecto ejecutar el siguiente comando:

yo @microsoft/sharepoint

Respondemos a las perguntas para crear el entorno.

La primera pregunta:
la dejamos por default(presionamos enter).

La segunda pregunta seleccionamos:
WEBPART 

La tercera pregunta seleccionamos:
REACT

Una vez termine de instalarse todo ejecutamos lo siguiente:
gulp trust-dev-cert

(en caso de error realizar el siguiente comando)
set-executionpolicy bypass -scope process

Aceptar el mensaje del certificado que se muestra.

abrir el proyecto en VSCode
code .

Una vez dentro del proyecto buscar el siguiente fichero:
./config/server.json

Buscamos la propiedad initialPage: modificar de tal manera que quede asi:

modificar el dominio por el nuestro.

"https://{tenantDomain}.sharepoint.com/sites/dev/_layouts/workbench.aspx"

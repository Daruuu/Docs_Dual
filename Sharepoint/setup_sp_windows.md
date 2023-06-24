## Set up sharepoint on Windows 10

To install SharePoint on Windows 10, follow these steps:

1. Install Node.js via NVM with version 16.18.1:

```bash
nvm -l
nvm install 16.18.1
nvm use 16.18.1
```

2. Verify the Node.js version:

```bash
node -v
```

3. Install the required packages globally:

```bash
npm install gulp-cli yo @microsoft/generator-sharepoint --location=global
```

4. Open a command prompt and RUN AS ADMINISTRATOR the following commands:

```bash
npm install gulp-cli --global
npm install yo --global
npm install  @microsoft/generator-sharepoint --global
npm install gulp --global
```

5. Locate the installed versions do Node.js at the following path:

```bash
C:\Users\[Your Username]\AppData\Roaming\nvm
```

6. Now, run the following command in the command prompt (this command may give an error):
```bash
gulp trust-dev-cert
```

7. Create a test project:
```bash
md [project_name]
```
8. Access the newly created project folder:

```bash
cd project_name
```

9. Inside the project folder, execute the following command:

```bash
yo @microsoft/sharepoint
```

10. Answer the questions to create the environment:

    - Leave the first question with the default name (press Enter).
    
    - Select "WEBPART" for the second question.
    
    - Select "No framework.

12. Once everything is installed, inside the project folder, run the following command:

```bash
gulp trust-dev-cert
```
(If there's an error, run the following command:)
```bash
set-executionpolicy bypass -scope process
```
12. Accept the certificate message that appears to complete the process.

13. Run the following command:
```bash
gulp serve
```

14. Open the project in Visual Studio Code.
```bash
code .
```

15. Once inside the project, locate the following file:
```bash
./config/server.json
```

16. Find the initialPage property and modify it as follows:
Replace the {tenantDomain} with the SharePoint's enterprise space.
Example: 

```bash
"https://{tenantDomain}.sharepoint.com/sites/dev/_layouts/workbench.aspx"
```

Sustituir el dominio que es {}, por el espacio empresarial de SharePoint.

"https://{tenantDomain}.sharepoint.com/sites/dev/_layouts/workbench.aspx"

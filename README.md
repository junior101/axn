# Personal Website

Source code of personal website.

* Website: <a href="https://axnikitenko.com">https://axnikitenko.com</a>
* Administration Panel: <a href="https://axnikitenko.com/axnadmpnl">https://axnikitenko.com/axnadmpnl</a>
* RESTful API Server: <a href="https://axnikitenko.com/axnrstapi">https://axnikitenko.com/axnrstapi</a>

## Table of Contents

* [Download](#download)
* [Project structure](#project-structure)
* [Back-end](#back-end)
	* [Technology stack](#be-technology-stack)
	* [Setup](#be-setup)
	* [Building and running](#be-building-running)
* [Front-end](#front-end)
	* [Technology stack](#fe-technology-stack)
	* [Packages](#fe-packages)
	* [Installing](#fe-installing)
	* [Administration panel](#fe-admin) 
		* [Commands](#fe-admin-commands) 
		* [Running](#fe-admin-run) 
	* [Website](#fe-platform) 
		* [Commands](#fe-platform-commands) 
		* [Running](#fe-platform-run) 

<div id="download"/>
### Download

Clone project from git: 

```sh
git clone https://github.com/axnikitenko/axn.git
```

<div id="project-structure"/>
### Project structure

Project contains two sub-projects for back-end and front-end sources. Front-end sub-project contains two modules for website and administration panel sources.

Detailed structure:

```sh
axn/									* root
├──axn-client/							* front-end sub-project
|	├──axn-cln-admin/					* module for administration panel
|		├──config/						* configuration
|			├──webpack.common.js		* common configuration
|			├──webpack.development.js	* developmnet configuration
|			├──webpack.production.js	* production configuration
|		├──plugin/						* custom plugins
|			├──helper-conf-plugin/		* additional function for configuration
|		├──src/							* sources
|			├──app/						* angular components
|			├──assets/					* icons, images, font and etc
|			├──entry/					* vendors, polyfills and entry main file
|			├──index.html				* generate our index page
|		├──typings/						* generated typings from typings.json
|		├──version/						* sources
|			├──result.txt				* needed for webpack-version plugin
|			├──template.ejs				* template for generate version.txt file
|		├──package.json					* what npm uses to manage it's dependencies
|		├──tsconfig.json				* config for typescript
|		├──typings.json					* config for typings
|	├──axn-cln-platform/				* module for website
|		...								* same as the structure of axn-cln-admin		
├──axn-server/							* back-end sub-project
|	├──axn-srv-database/				* module for core classes
|		├──data/						* config for different modes and SQL scripts
|		├──src/							* sources and tests
|		├──build.gradle					* module build configuration	
|	├──axn-srv-rest-api/				* module for RESTful API
|		├──data/						* log4j config for different modes
|		├──src/							* sources and tests
|		├──build.gradle					* module build configuration
|	├──build.gradle						* main build configuration
|	├──settings.gradle					* included module 
├──.gitignore							* files which needed to ignore on push
├──LICENSE								* license file
├──README.md							* small documentation
```

<div id="back-end"/>
## Back-end

Back-end needed for provide RESTful API to client.

<div id="be-technology-stack"/>
### (Back-end) Technology stack

| Category  | Technology                           |
| :---      | :---                                 |
| Language  | Java 8, Groovy 2               	   |
| Framework | Spring 4                             |
| Libraries |                                	   |
| Testing   | jUnit4                               |
| Building  | Gradle 3                             |
| Database  | PostgreSQL 9.5                       |
| Server    | Tomcat 8.5      					   |

<div id="be-setup"/>
### (Back-end) Setup

For server you need install:

* JDK 1.8
* Gradle 3
* PostgreSQL 9.5
* Tomcat 8.5

After installing you need change config of PostgreSQL 9.5:

```sh
sudo gedit /etc/postgresql/9.5/main/postgresql.conf
```

Set max connection to 300:

```sh
max_connections = 300
```

Set to ¼ of your RAM to parameter shared_buffers:

```sh
shared_buffers = 2000MB
```

Restart:

```sh
sudo service postgresql restart
```

Connect to your postgresql as root user and execute scripts:

```sh
${path_to_root_project_dir}/axn-server/axn-srv-database/data/sql/pg_create_database.sql
${path_to_root_project_dir}/axn-server/axn-srv-database/data/sql/pg_drop_create_tables.sql
```

Set your local path for log, go to:

```sh
${path_to_root_project_dir}/axn-server/axn-srv-rest-api/data/log4j.debug.properties
```

Set your local path:

```sh
log4j.appender.file_rest_api.File=${your_local_path}
```

After you can add file to .gitignore, if you want.

<div id="be-building-running"/>
### (Back-end) Building and running

Commands need execute in root sub-project directory:

```sh
cd ${path_to_root_project_dir}/axn-server
```

After that you can use commands:

| Command   				   							 | Description   |
| :---                                                   | :---          |
| gradle clean && gradle build 							 | Build project for debug mode |
| gradle clean && gradle -PbuildProfile=production build | Build project for production |

You can use --info --debug --stacktrace for detail information.

Build file being in (*.war):

```sh
${path_to_root_project_dir}/axn-server/axn-srv-rest-api/build/libs
```


<div id="front-end"/>
## Front-end

Front-end render data for website and administration panel.

<div id="fe-technology-stack"/>
### (Front-end) Technology stack

| Category  | Technology                           |
| :---      | :---                                 |
| Language  | JavaScript, TypeScript               |
| Framework | AngularJS 2                          |
| Libraries |                                	   |
| Testing   |                                      |
| Building  | Webpack 2                            |
| Server    | Webpack-dev-server, http-server      |

<div id="fe-packages"/>
### (Front-end) Packages

Global packages (npm install --global):

* webpack-dev-server - simple server for test development version of project
* http-server - simple zero-configuration command-line http server
* karma-cli - cli for test framework
* typescript - TypeScript adds optional types, classes, and modules to JavaScript
* typings - the TypeScript Definition Manager

Main dependencies (dependencies in package.json):

* @angular/… - angular2 needed dependencies
* assets-webpack-plugin - create webpack-assets.json in assets
* core-js - includes polyfills for es5, es6, es7
* ie-shim - polyfills for IE9
* rxjs - reactive extensions for js, needed for angular2
* zone.js - implements Zones for JavaScript, needed for angular2

Development dependencies (devDependencies in package.json):

* angular2-template-loader - angular2 webpack loader that inlines your angular2 templates and stylesheets into angular components
* awesome-typescript-loader - TypeScript loader for Webpack
* css-loader - css loader for webpack
* json-loader - json loader for webpack
* to-string-loader - to-string loader for webpack
* raw-loader - raw loader for webpack
* file-loader - file loader for webpack
* rimraf - for delete files in directories
* typescript - support *.ts files
* webpack - system for building projects
* webpack-dev-server - simple server for test development version of project
* webpack-dev-middleware - webpack hot reloading
* webpack-version-file-plugin - generates a file with your package name, version and build date.
* webpack-md5-hash - replace a standard webpack chunkhash with md5
* webpack-merge - for merge configuration files
* copy-webpack-plugin - copy files and directories in webpack
* html-webpack-plugin - simplifies creation of HTML files to serve your webpack bundles

<div id="fe-installing"/>
### (Front-end) Installing

Install node.js and npm:

```sh
# example for ubuntu 14.04 LTS
curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo apt-get install -y nodejs
```

Install global packages: 

```sh
npm install --global webpack
npm install --global webpack-dev-server
npm install --global karma-cli
npm install --global typescript
npm install --global typings
npm install --global http-server
```

Next you need to install local packages for two modules. 

Go to root administration panel module directory and install:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-admin
npm install
```

Go to root website module directory and install:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-platform
npm install
```

<div id="fe-admin"/>
## (Front-end) Administration panel

<div id="fe-admin-commands"/>
### (Front-end, Administration panel) Commands

Commands need execute in root module directory:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-admin
```

After that you can use commands:

| Command   				| Description                           |
| :---                      | :---                                  |
| npm run clean  			| Clean build directory				    |
| npm run build:development | Build for development mode   		    |
| npm run build:production  | Build for production mode   		    |
| npm run start:development | Start application in development mode | 
| npm run start:production  | Start application in production mode  | 

Build files being in:

```sh
${path_to_root_project_dir}/axn-client/axn-cln-admin/build
```

<div id="fe-admin-run"/>
### (Front-end, Administration panel) Running

Commands need execute in root module directory:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-admin
```

For running application in different mode you can use commands:

| Command   				| Description                           |
| :---                      | :---                                  |
| npm run start:development | Start application in development mode | 
| npm run start:production  | Start application in production mode  | 

Before you start this module in production mode you need comment line in file:

```sh
config/webpack.production.js
```

Line with publicPath:

```sh
...
output: {
    publicPath: 'axnadmpnl/', // need comment when start client in production mode on local machine
    path: helpers.root('build'),
    filename: '[name].[chunkhash].bundle.js',
...
```

In development and production mode servers use this url: 

<a href="http://localhost:3001">localhost:3001</a>

<div id="fe-platform"/>
## (Front-end) Website

<div id="fe-platform-commands"/>
### (Front-end, Website) Commands

Commands need execute in root module directory:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-platform
```

After that you can use commands:

| Command   				| Description                           |
| :---                      | :---                                  |
| npm run clean  			| Clean build directory				    |
| npm run build:development | Build for development mode   		    |
| npm run build:production  | Build for production mode   		    |
| npm run start:development | Start application in development mode | 
| npm run start:production  | Start application in production mode  | 

Build files being in:

```sh
${path_to_root_project_dir}/axn-client/axn-cln-platform/build
```

<div id="fe-platform-run"/>
### (Front-end, Website) Running

Commands need execute in root module directory:

```sh
cd ${path_to_root_project_dir}/axn-client/axn-cln-platform
```

For running application in different mode you can use commands:

| Command   				| Description                           |
| :---                      | :---                                  |
| npm run start:development | Start application in development mode | 
| npm run start:production  | Start application in production mode  | 

In development and production mode servers use this url: 

<a href="http://localhost:3000">localhost:3000</a>
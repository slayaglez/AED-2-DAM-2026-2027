# Práctica 01
## 03 - Maven y construcción de proyectos en Java

### Introducción
Debemos crear un proyecto en Java usando Maven manejando archivos como el pom.xml

### Primeros pasos
En primer lugar configuramos nuestro entorno, repositorio en github, configuración del IDE, máquina virtual... etc.

1. Crear clave ssh con `ssh-keygen` y conectarla a github copiando la clave del archivo idXXXX.pub.
2. Configurar tu identidad en git con `git config --global name:"slayaglez"` y opcionalmente `git config --global email:"slayaglez@gmail.com"`
3. Tras esto podemos crear nuestro repositorio en github y traerlo a nuestra máquina virtual con `git clone <dirección ssh>`
4. Con nuestro repo y control de versiones ya configurado podemos empezar con la práctica

### Creación del proyecto
Mediante terminal o manualmente creamos el siguiente árbol de carpetas:
```bash
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │     └── com
    │   │         └── codelearn
    │   │               └── Main.java
    │   └── resources
    └── test
        └── java
```

Tras ello creamos el `Main.java` con un SystemPrintOut sencillo y editamos el `pom.xml` para que quede como se nos pide.

Para comprobar que el `pom.xml` ha quedado bien escribimos el comando `mvn validate` en la terminal, ubicado en el directorio raíz del proyecto. El resultado deberia ser como el que se muestra en la siguiente imagen:

![img](img/1.png)
<hr>


## 04 - Compilar y entender archivos
Empecemos a compilar el proyecto con `mvn compile`


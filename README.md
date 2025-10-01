# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**


## 🚀 Tecnologías Principales

- **Java 21** con Spring Boot 3.5.4
- **Spring WebFlux** para programación reactiva
- **Spring Security** con autenticación JWT y OAuth2 Resource Server
- **DynamoDB Enhanced** para almacenamiento NoSQL
- **Project Reactor** para streams reactivos
- **MapStruct** para mapeo de objetos
- **Lombok** para reducción de código boilerplate

## 📊 Base de Datos y Persistencia

- **DynamoDB** como base de datos NoSQL
- **DynamoDB Enhanced Client** para acceso reactivo
- **AWS SDK Enhanced** para abstracción de datos

## 🔐 Seguridad y Autenticación

- **JWT (JSON Web Tokens)** con JJWT 0.12.3
- **Spring Security** para autorización y autenticación
- **OAuth2 Resource Server** para validación de tokens
- **AWS SDK 2.33.1** para integración con servicios AWS

## 📚 Documentación y APIs

- **OpenAPI 3.0** con SpringDoc para documentación automática
- **Swagger UI** integrado para testing de APIs
- **Endpoints REST reactivos** con validaciones

## 🧪 Testing y Calidad

- **JUnit 5** para pruebas unitarias
- **Reactor Test** para testing de streams reactivos
- **Mockito** para mocking en pruebas
- **JaCoCo** para cobertura de código
- **PIT (Pitest)** para mutation testing
- **SonarQube** para análisis estático de código

## 🐳 Containerización y Despliegue

- **Docker** con imagen base Eclipse Temurin 21 JDK Alpine
- **Gradle 8.14.3** como sistema de build
- **Spring Boot Actuator** para monitoreo y métricas
- **Micrometer** con Prometheus para métricas

## ☁️ Integración AWS

- **AWS DynamoDB** para almacenamiento de reportes (implementado)
- **AWS SQS** para procesamiento de mensajes (implementado)
- **AWS SDK 2.33.1** para integración con servicios AWS

## 🔧 Herramientas de Desarrollo

- **Gradle Wrapper** para builds reproducibles
- **Spring Boot DevTools** para desarrollo ágil
- **Validation API** para validaciones de entrada
- **Object Mapper** para serialización/deserialización

## ⏰ Procesamiento y Schedulers

- **Spring Scheduler** para tareas programadas
- **Cron Jobs** para generación automática de reportes
- **SQS Listener** para procesamiento de mensajes asíncronos
- **SQS Sender** para envío de mensajes

## 🌐 Comunicación y Resilencia

- **REST Consumer** para comunicación con microservicios externos
- **Resilience4j** para circuit breakers y tolerancia a fallos
- **WebClient** para llamadas HTTP reactivas

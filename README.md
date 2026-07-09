Sistema de Tramitación de Ayudas y Subvenciones Públicas - API REST

Este proyecto es un **Backend robusto y escalable** desarrollado con **Java 21** y **Spring Boot 3.4.1**, diseñado bajo una arquitectura limpia en capas. El sistema gestiona el ciclo de vida completo de solicitudes de ayuda financiera, controlando de forma estricta las reglas de negocio y manteniendo un registro automatizado de auditoría.

---

Características Principales y Lógica de Negocio

* **Arquitectura Limpia en 4 Capas:** Separación total de responsabilidades (`Model` -> `Repository` -> `Service` -> `Controller`).
* **Control del Ciclo de Vida de Solicitudes:** Validación estricta de transiciones de estados mediante lógica booleana avanzada (`RECIBIDA` ➡️ `EN REVISIÓN` ➡️ `APROBADA`/`DENEGADA`). El sistema impide estados corruptos.
* **Procesamiento Funcional (Java Streams):** Filtrado y explotación de datos en memoria optimizados utilizando la API de Streams y Expresiones Lambda de Java.
* **Bonus - Sistema de Auditoría Automatizado:** Cada cambio de estado genera un registro histórico inmutable con marca de tiempo precisa (`LocalDateTime`), guardando el estado anterior, el nuevo y el autor del cambio.
* **Base de Datos en Memoria:** Persistencia relacional eficiente configurada con **H2 Database** y gestionada mediante **Spring Data JPA / Hibernate**.

---

Tecnologías Utilizadas

* **Lenguaje:** Java 21 (JDK 21)
* **Framework Principal:** Spring Boot 3.4.1
* **Persistencia:** Spring Data JPA & Hibernate
* **Base de Datos:** H2 (En memoria, con consola web activa)
* **Herramientas de Servidor:** Apache Tomcat (Embebido, puerto `8080`)
* **Pruebas de API:** Postman & SQL Console

---

Estructura del Proyecto 


com.consultoria.ayudas_api
├── model        # Entidades de la Base de Datos (JPA) e Historial de Auditoría
├── repository   # Interfaces de persistencia (Spring Data JPA)
├── service      # El "cerebro" (Lógica de negocio, validaciones y Streams)
└── controller   # Puntos de entrada HTTP (Endpoints API REST)
Endpoints de la API REST (Probadolos en Postman)
Solicitudes
POST /api/solicitudes -> Crea una nueva solicitud (Estado inicial por defecto: RECIBIDA).

Parámetros: descripcion (String), importe (Double), usuarioId (Long).

PUT /api/solicitudes/{id}/estado -> Modifica el estado de una solicitud aplicando las reglas de negocio.

Parámetros: nuevoEstado (String), usuarioId (Long).

GET /api/solicitudes/aprobadas/usuario/{usuarioId} -> Devuelve la lista de solicitudes aprobadas de un usuario específico usando Java Streams.

Cómo Ejecutar el Proyecto Localmente
Clona este repositorio:

Bash
git clone [https://github.com/jesusnzr/sistema-tramitacion-ayudas.git](https://github.com/jesusnzr/sistema-tramitacion-ayudas.git)
Abre el proyecto en tu IDE favorito (IntelliJ IDEA recomendado).

Asegúrate de tener instalado el JDK 21.

Ejecuta la clase principal AyudasApiApplication.

Accede a la consola de la base de datos en: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:ayudasdb

User: SA | Password: (Vacío)

# Shastho Record

Medical history tracker built with Spring Boot, Thymeleaf and MySQL.

Each doctor visit stores the date, doctor, symptoms, diagnosis and a list of medicines (name, dose, frequency, duration). The server marks a medicine as an antibiotic when its lowercase name equals or contains a name from a seeded reference list of generic and Bangladeshi brand names.

## Features

- Add, view, edit and delete visits
- Dynamic medicine rows on the form
- Antibiotic badge on medicines, antibiotic count per visit, "antibiotics only" filter
- Server-side validation with inline errors, 404 page for unknown ids

## Stack

Java 17, Maven, Spring Web, Spring Data JPA, Validation, Thymeleaf, MySQL

## Setup

MySQL (run in Workbench or the mysql client):

```sql
CREATE DATABASE IF NOT EXISTS medtracker;
CREATE USER IF NOT EXISTS 'medtracker'@'localhost' IDENTIFIED BY 'medtracker_pass';
GRANT ALL PRIVILEGES ON medtracker.* TO 'medtracker'@'localhost';
FLUSH PRIVILEGES;
```

Run:

```
mvn spring-boot:run
```

Open http://localhost:8080

Credentials can be overridden with the `DB_USER` and `DB_PASS` environment variables.

## Structure

```
controller  VisitController
service     VisitService, VisitNotFoundException
repository  VisitRepository, AntibioticRefRepository
model       Visit, Medicine, AntibioticRef
config      DataSeeder
```

Project report: `docs/ShasthoRecord_Report.pdf` (LaTeX source: `docs/ShasthoRecord_Report.tex`)

## Future scope

OCR of prescriptions, AI medicine recognition, interaction alerts, login and encryption, audit log

## Submitted by 
Ishtiak Ahmed Ayon \
Id: it24039 \
Instructor: Prof. Dr. Ziaur Rahman


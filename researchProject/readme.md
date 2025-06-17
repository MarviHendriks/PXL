# PXL-Digital Research Project

# Tinkerstal 'T Boesenhof - Paardenfokkerij Applicatie

Welkom bij Tinkerstal 'T Boesenhof, een applicatie voor het beheren van een paardenfokkerij. Dit project maakt gebruik van Java Spring Boot voor de backend en Vue.js voor de frontend.

In deze applicatie kan je makkelijk merries, hengsten en veulen beheren. Je kan een merrie of hengst creëren, lezen, updaten en verwijderen. Dit kan ook voor een veulen. Een veulen kan toegevoegd worden aan een merrie. Er is ook plaats voor klanten voor verkochte veulens.
Enkele functionaliteiten opgesomd:

- Homepage

  - Lijst van alle merries en hengsten
  - Optie op te filteren op eigenschappen van merries en hengsten
  - Mogelijkheid voor het creëren van een nieuw paard

- Merrie

  - Details van merrie kunnen bekijken, aanpassen, verwijderen
  - Een dekdatum toevoegen aan een merrie
  - Dagboek voor de merrie
  - Merrie drachtig maken
  - Een veulen aanmaken bij de merrie

- Hengst

  - Details van hengst kunnen bekijken, aanpassen, verwijderen

- Veulen

  - Details van een veulen kunnen bekijken, aanpassen, verwijderen
  - Bijhouden van ontwormingen
  - Creëren van een klant als de veulen verkocht is

- Klant
  - Details van een klant kunnen bekijken, aanpassen, verwijderen

## Vereisten

Om dit project lokaal te draaien, moet je de volgende tools geïnstalleerd hebben:

- Docker
- npm (Node.js Package Manager)

## backend

Ga naar de `backend/` directory en voer het volgende commando uit om de backend te starten:
`docker-compose up`
Als de docker container opgestart is run je de backend.

## frontend

Ga naar de frontend/ directory en installeer de benodigde npm-packages met volgend commando:
`npm install`

Vervolgens start je de frontend op door het commando:
`npm run dev`

## De applicatie

Na het opstarten van de backend en de frontend kan je surfen naar `http://localhost:5173/`
Hier kom je terecht op de inlogpagina van de applicatie.

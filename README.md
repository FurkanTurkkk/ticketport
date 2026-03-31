# TicketPort

Back-end for listing events and selling sessions (tickets per show time). The codebase is a modular monolith: **User** and **Event** bounded contexts are wired end-to-end; **Booking** exists at domain level only; **Payment** is not implemented. The HTTP surface is oriented toward catalog browsing plus **admin-style** operations (create/publish events, manage sessions). There is no customer-facing storefront UI in this repository.

---

## English

### What is implemented

- **User:** registration, login, JWT issuance. Flyway seeds a development **admin** account for protected routes.
- **Event:** draft events, slug assignment, publish workflow, public list. Categories are loaded via migration (no public “list categories” API); creating an event references a `categoryId` from seed data.
- **Event sessions:** per-event sessions with capacity, price snapshot (`amount` / `currency`, or zero for free), time window. Listing is public; creation requires admin.

### Architecture (short)

Gradle multi-module layout: `*-domain` (no framework), `*-application` (use cases, ports), `*-infra` (JPA adapters), `*-api` (HTTP), plus `bootstrap` as the Spring Boot composition root. Inbound adapters depend on application ports; infrastructure implements outbound ports. Domain stays inward of all of that.

### Prerequisites

- Java **21**
- **PostgreSQL** (local or Docker)
- Optional: **Docker** and **Docker Compose** for the bundled stack

### Configuration

Default port: **9090**. Data source and JWT settings live in `bootstrap/src/main/resources/application.yml`. For Docker Compose, the app container overrides the JDBC URL to reach the `postgres` service.

**Security note:** The default JWT secret in `application.yml` is for local use only. Replace it (or inject via environment) before any real deployment.

### Run locally (Gradle + local Postgres)

Create database and user matching `application.yml` (`ticketport` / `ticketport`), then:

```bash
./gradlew :bootstrap:bootRun
```

Flyway runs migrations on startup (`ddl-auto: validate`).

### Run with Docker Compose

From the repository root:

```bash
docker compose up --build
```

Application: `http://localhost:9090` · Postgres: `localhost:5432` (credentials `ticketport` / `ticketport`).

### Seeded data (Flyway)

- **Categories** (use `categoryId` when creating events):
  - `b0000001-0000-4000-8000-000000000001` — MOVIE  
  - `b0000001-0000-4000-8000-000000000002` — TRIP  
  - `b0000001-0000-4000-8000-000000000003` — THEATRE  

- **Admin user** (JWT for `ROLE_ADMIN` routes):  
  - Email: `admin@ticketport.local`  
  - Password: `Admin1!Pass`  

Change or remove the seed in production.

### HTTP API (current)

Base URL: `http://localhost:9090` · JSON bodies · `Content-Type: application/json`

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/v1/auth/register` | none | Register; returns `userId`. |
| POST | `/api/v1/auth/login` | none | Returns JWT `token`. Body: `email`, `rawPassword`. |
| POST | `/api/v1/events` | Bearer **ADMIN** | Create draft event. Body: `categoryId`, `title`, `description`, `coverImageRef`. |
| PUT | `/api/v1/events/{eventId}/slug` | Bearer **ADMIN** | Assign slug. Body: `slug`. |
| POST | `/api/v1/events/{eventId}/publish` | Bearer **ADMIN** | Publish event (required before adding sessions). |
| GET | `/api/v1/events/list` | none | List events (public catalog). |
| GET | `/api/v1/sessions?eventId={uuid}` | none | List sessions for an event. |
| POST | `/api/v1/sessions` | Bearer **ADMIN** | Create session. Body: `eventId`, `capacity`, `amount`, `currency`, `startedAt`, `endsAt` (ISO-8601). `amount` null or zero → treated as free. |

Protected routes expect header: `Authorization: Bearer <token>`.

### Planned / outstanding (technical backlog)

These are intentional gaps for follow-up work, not hidden limitations:

1. **Booking (reservation):** domain model exists under `booking-domain`; application layer, HTTP API, persistence, and integration with session inventory are **not** implemented yet.
2. **Payment:** not started; will pair with booking lifecycle (e.g. confirm after successful payment).
3. **Customer UX:** no dedicated end-user app; admin-oriented API flow only.
4. **Hardening:** production secrets, rate limiting, audit logging, and payment-provider specifics belong in later iterations.

---

## Türkçe

### Ne hazır?

- **Kullanıcı:** kayıt, giriş, JWT. Geliştirme için Flyway ile **admin** kullanıcısı ekleniyor.
- **Etkinlik:** taslak oluşturma, slug atama, yayınlama, herkese açık liste. Kategoriler migrasyonla geliyor (ayrı bir “kategori listesi” endpoint’i yok); etkinlik oluştururken seed `categoryId` kullanılır.
- **Seans:** kapasite, fiyat (`amount` / `currency`, sıfır veya null → ücretsiz), zaman aralığı. Listeleme herkese açık; oluşturma admin.

### Mimari (özet)

Çok modüllü Gradle yapısı: `*-domain` (saf Java), `*-application` (use case ve portlar), `*-infra` (JPA adapter), `*-api` (HTTP), `bootstrap` Spring Boot giriş noktası. Bağımlılıklar dıştan içe doğru; domain framework içermez.

### Gereksinimler

- Java **21**
- **PostgreSQL**
- İsteğe bağlı: **Docker** / **Docker Compose**

### Çalıştırma

**Yerel:** `application.yml` ile uyumlu DB’yi hazırlayıp:

```bash
./gradlew :bootstrap:bootRun
```

**Docker Compose** (repo kökü):

```bash
docker compose up --build
```

Uygulama: `http://localhost:9090`

### Flyway seed özeti

- **Kategori id’leri:** MOVIE / TRIP / THEATRE — yukarıdaki İngilizce tabloda UUID’ler.
- **Admin:** `admin@ticketport.local` / `Admin1!Pass` — üretimde kaldırın veya değiştirin.

### HTTP API (mevcut)

Tablo ve akış İngilizce bölümdeki tablo ile aynıdır: korumalı çağrılar için `Authorization: Bearer <token>`.

### Yol haritası / teknik borç

Bunlar bilinçli olarak sonraya bırakılan işler:

1. **Rezervasyon (booking):** `booking-domain` mevcut; uygulama katmanı, REST, kalıcılık ve kontenjan entegrasyonu **yok**.
2. **Ödeme (payment):** henüz yok; booking yaşam döngüsüyle birlikte ele alınacak.
3. **Son kullanıcı arayüzü:** repo yalnızca admin ağırlıklı API akışı içerir.
4. **Üretim olgunluğu:** gizli anahtarlar, güvenlik sertleştirme, ödeme sağlayıcı entegrasyonu sonraki adımlarda.

# 🐾 Pet Management API - Spring Boot REST Service

A simple CRUD REST API for managing pets, built with Spring Boot 3
---

##  Prerequisites

You only need **Docker Desktop** installed on your machine

---

## Quick Start

### 1. Start the Application

Open a terminal in the project directory and run:

```bash
./start.sh
```
*(On Windows useGit Bash):*



### 2. Wait for Startup

The script will:
- ✅ Build your application (first time: ~2-3 minutes)
- ✅ Start PostgreSQL database
- ✅ Load sample data (10 pets)
- ✅ Start your API

**You'll see:**
```
✓ Application is ready!
```


### 3. Access the API

Once started, the API is available at:

**🌐 API Base URL:**
```
http://localhost:8080
```

---

## 🧪 Testing the API

### Option 1: Swagger UI (Visual Interface)

1. **Open your browser:**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

2. **You'll see all available endpoints**

3. **Try it out:**
    - Click on any endpoint (e.g., `GET /api/v1/pets`)
    - Click "Try it out"
    - Click "Execute"
    - See the response!

### you can also use curl/postman ###

## 🛑 Stopping the Services

### Stop All Services

```bash
./stop.sh
```
( run only : docker compose down)


### Stop and Remove Data (Reset)
```bash
docker compose down -v
```

This will:
- Stop all containers
- Remove all data
- Next start will recreate everything fresh

### Restart Services
```bash
docker compose restart
```

---

## 🔄 Development Workflow

### View Logs
```bash
# All services
docker compose logs -f

# Only application
docker compose logs -f app

# Last 50 lines
docker compose logs --tail=50 app
```

### Rebuild After Code Changes
```bash
docker compose up --build app
```

### Full Restart
```bash
docker compose down
docker compose up --build
```

---

### View Full Logs
```bash
docker compose logs app
```


### Reset Everything
```bash
docker compose down -v
./start.sh
```

---

## 🎯 Quick Reference

### URLs
- **API Base:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **PostgreSQL:** localhost:5432
- **pgAdmin:** http://localhost:5050

### Commands
```bash
# Start
./start.sh

# Stop
docker compose down

# Logs
docker compose logs -f app

# Restart
docker compose restart app

# Reset
docker compose down -v && ./start.sh
```

---

## 💡 Tips

1. **First startup is slow** (2-3 minutes) - Docker downloads images and builds
2. **Subsequent startups are fast** (30-60 seconds) - Everything is cached
3. **Use Swagger UI** - It's the easiest way to test the API
4. **Watch logs** if something doesn't work - `docker compose logs -f app`
5. **Reset if stuck** - `docker compose down -v && ./start.sh`

---

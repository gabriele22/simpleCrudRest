#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo "╔══════════════════════════════════════════════════════════╗"
echo "║     Pet Management API - Docker Complete Setup          ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

# Check Docker
echo -e "${YELLOW}Step 1: Checking Docker...${NC}"
if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗ Docker not found. Please install Docker first.${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Docker is installed${NC}"
echo ""

# Build and start all services
echo -e "${YELLOW}Step 2: Building and starting all services...${NC}"
echo -e "${BLUE}This will:${NC}"
echo "  • Build your Spring Boot application (using Maven in Docker)"
echo "  • Start PostgreSQL database"
echo "  • Run init.sql (create table + insert 10 sample pets)"
echo "  • Start your application"
echo "  • Start pgAdmin (optional web UI)"
echo ""
echo -e "${YELLOW}Building... (this may take a few minutes on first run)${NC}"
echo ""

docker compose up --build -d

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}✓ All services started successfully!${NC}"
else
    echo -e "${RED}✗ Failed to start services${NC}"
    exit 1
fi

echo ""
echo -e "${YELLOW}Step 3: Waiting for application to be ready...${NC}"
echo ""
echo -e "${BLUE}Showing application logs (press Ctrl+C when you see 'Started SimpleCrudRestApplication'):${NC}"
echo ""

# Show logs in background and wait for app to be ready
docker compose logs -f app &
LOGS_PID=$!

# Wait for app to be ready (max 120 seconds)
for i in {1..60}; do
    if curl -s http://localhost:8080/api/v1/pets > /dev/null 2>&1; then
        kill $LOGS_PID 2>/dev/null
        echo ""
        echo -e "${GREEN}✓ Application is ready!${NC}"
        break
    fi
    sleep 2

    # If we've waited 30 seconds, show a message
    if [ $i -eq 15 ]; then
        echo ""
        echo -e "${YELLOW}Still waiting... This is normal for first startup.${NC}"
        echo ""
    fi
done

# If we've exited the loop and logs are still running, kill them
kill $LOGS_PID 2>/dev/null || true

# Final check
if ! curl -s http://localhost:8080/api/v1/pets > /dev/null 2>&1; then
    echo ""
    echo -e "${YELLOW}⚠ Application seems to be taking longer than expected.${NC}"
    echo ""
    echo -e "${YELLOW}Check logs with:${NC} docker compose logs app"
    echo -e "${YELLOW}Check status with:${NC} docker compose ps"
    echo ""
    echo "The app might still be starting. Wait a minute and try:"
    echo "  curl http://localhost:8080/api/v1/pets"
    echo ""
fi

echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║                    🎉 ALL READY!                         ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""
echo -e "${GREEN}Your services are running:${NC}"
echo ""
echo "  📱 Spring Boot API:"
echo "     http://localhost:8080"
echo ""
echo "  📚 Swagger UI:"
echo "     http://localhost:8080/swagger-ui.html"
echo ""
echo "  🐘 PostgreSQL:"
echo "     localhost:5432 (petdb/petuser/petpass123)"
echo ""
echo "  🔧 pgAdmin (optional):"
echo "     http://localhost:5050 (admin@example.com/admin123)"
echo ""
echo -e "${YELLOW}Quick test:${NC}"
echo "  curl http://localhost:8080/api/v1/pets"
echo ""
echo -e "${YELLOW}View logs:${NC}"
echo "  docker compose logs -f app"
echo ""
echo -e "${YELLOW}Stop everything:${NC}"
echo "  docker compose down"
echo ""
echo -e "${YELLOW}Restart everything:${NC}"
echo "  docker compose restart"
echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║  Press Ctrl+C to stop, or run: docker compose down      ║"
echo "╚══════════════════════════════════════════════════════════╝"
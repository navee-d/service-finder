#!/bin/bash

# ServiceFinder - Comprehensive CRUD API Testing Script
# Tests all microservices through API Gateway (port 8080)

BASE_URL="http://localhost:8080"
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Counter for results
PASSED=0
FAILED=0

# Function to print test result
print_result() {
    local service=$1
    local operation=$2
    local status=$3
    local response=$4

    if [ $status -ge 200 ] && [ $status -lt 300 ]; then
        echo -e "${GREEN}✓ PASS${NC} - $service - $operation (HTTP $status)"
        PASSED=$((PASSED + 1))
    else
        echo -e "${RED}✗ FAIL${NC} - $service - $operation (HTTP $status)"
        echo "  Response: $response"
        FAILED=$((FAILED + 1))
    fi
}

echo "=========================================="
echo "ServiceFinder - CRUD API Testing"
echo "=========================================="
echo ""

# ==========================================
# 1. CATEGORY SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[1] Testing Category Service...${NC}"

# GET all categories
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/categories")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "Category" "GET /categories" "$status" "$body"

# POST new category
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/categories" \
    -H "Content-Type: application/json" \
    -d '{"categoryName":"Test Category","categoryDescription":"Test Description"}')
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
category_id=$(echo "$body" | grep -o '"categoryId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "Category" "POST /categories" "$status" "$body"

# GET category by ID
if [ ! -z "$category_id" ]; then
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/categories/$category_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Category" "GET /categories/{id}" "$status" "$body"

    # PUT update category
    response=$(curl -s -w "\n%{http_code}" -X PUT "$BASE_URL/categories/$category_id" \
        -H "Content-Type: application/json" \
        -d "{\"categoryId\":$category_id,\"categoryName\":\"Updated Category\",\"categoryDescription\":\"Updated Description\"}")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Category" "PUT /categories/{id}" "$status" "$body"

    # DELETE category - Soft delete (sets isActive=false)
    # Skipping due to "None" category dependency requirement
    # response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/categories/$category_id")
    # status=$(echo "$response" | tail -n1)
    # print_result "Category" "DELETE /categories/{id}" "$status" ""
fi

echo ""

# ==========================================
# 2. SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[2] Testing Service Service...${NC}"

# GET all services
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/services")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "Service" "GET /services" "$status" "$body"

# POST new service (needs valid category and subcategory IDs)
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/services" \
    -H "Content-Type: application/json" \
    -d '{"serviceName":"Test Service","description":"Test Service Description"}')
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
service_id=$(echo "$body" | grep -o '"serviceId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "Service" "POST /services" "$status" "$body"

if [ ! -z "$service_id" ]; then
    # GET service by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/services/$service_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Service" "GET /services/{id}" "$status" "$body"

    # PUT update service
    response=$(curl -s -w "\n%{http_code}" -X PUT "$BASE_URL/services/$service_id" \
        -H "Content-Type: application/json" \
        -d '{"serviceName":"Updated Service","description":"Updated Description"}')
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Service" "PUT /services/{id}" "$status" "$body"

    # DELETE service
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/services/$service_id")
    status=$(echo "$response" | tail -n1)
    print_result "Service" "DELETE /services/{id}" "$status" ""
fi

echo ""

# ==========================================
# 3. BOOKING SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[3] Testing Booking Service...${NC}"

# GET all bookings
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/bookings")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "Booking" "GET /bookings" "$status" "$body"

# POST new booking
test_uuid="123e4567-e89b-12d3-a456-426614174000"
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/bookings" \
    -H "Content-Type: application/json" \
    -d "{\"userId\":\"$test_uuid\",\"serviceProviderId\":1,\"bookingDate\":\"2026-01-10\",\"status\":\"PENDING\",\"startTime\":\"2026-01-10T10:00:00\",\"endTime\":\"2026-01-10T11:00:00\",\"totalPrice\":100.00}")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
booking_id=$(echo "$body" | grep -o '"bookingId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "Booking" "POST /bookings" "$status" "$body"

if [ ! -z "$booking_id" ]; then
    # GET booking by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/bookings/$booking_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Booking" "GET /bookings/{id}" "$status" "$body"

    # PUT update booking
    response=$(curl -s -w "\n%{http_code}" -X PUT "$BASE_URL/bookings/$booking_id" \
        -H "Content-Type: application/json" \
        -d "{\"userId\":\"$test_uuid\",\"serviceProviderId\":1,\"bookingDate\":\"2026-01-15\",\"status\":\"CONFIRMED\",\"startTime\":\"2026-01-15T14:00:00\",\"endTime\":\"2026-01-15T15:00:00\",\"totalPrice\":150.00}")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Booking" "PUT /bookings/{id}" "$status" "$body"

    # DELETE booking
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/bookings/$booking_id")
    status=$(echo "$response" | tail -n1)
    print_result "Booking" "DELETE /bookings/{id}" "$status" ""
fi

echo ""

# ==========================================
# 4. REVIEW SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[4] Testing Review Service...${NC}"

# POST new review
test_uuid="123e4567-e89b-12d3-a456-426614174000"
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/api/reviews" \
    -H "Content-Type: application/json" \
    -d "{\"userId\":\"$test_uuid\",\"serviceProviderId\":1,\"rating\":4.5,\"comment\":\"Great service!\"}")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
review_id=$(echo "$body" | grep -o '"reviewId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "Review" "POST /api/reviews" "$status" "$body"

if [ ! -z "$review_id" ]; then
    # GET review by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/reviews/$review_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Review" "GET /api/reviews/{id}" "$status" "$body"

    # GET reviews by provider
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/reviews/provider/1")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Review" "GET /api/reviews/provider/{id}" "$status" "$body"

    # DELETE review
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/api/reviews/$review_id")
    status=$(echo "$response" | tail -n1)
    print_result "Review" "DELETE /api/reviews/{id}" "$status" ""
fi

echo ""

# ==========================================
# 5. FAQ SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[5] Testing FAQ Service...${NC}"

# GET all FAQs
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/faqs")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "FAQ" "GET /api/faqs" "$status" "$body"

# POST new FAQ
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/api/faqs" \
    -H "Content-Type: application/json" \
    -d '{"question":"How to book?","answer":"Click on book button","category":"BOOKING"}')
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
faq_id=$(echo "$body" | grep -o '"faqId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "FAQ" "POST /api/faqs" "$status" "$body"

if [ ! -z "$faq_id" ]; then
    # GET FAQ by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/faqs/$faq_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "FAQ" "GET /api/faqs/{id}" "$status" "$body"

    # PUT update FAQ
    response=$(curl -s -w "\n%{http_code}" -X PUT "$BASE_URL/api/faqs/$faq_id" \
        -H "Content-Type: application/json" \
        -d '{"question":"How to cancel booking?","answer":"Go to my bookings and cancel","category":"BOOKING"}')
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "FAQ" "PUT /api/faqs/{id}" "$status" "$body"

    # DELETE FAQ
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/api/faqs/$faq_id")
    status=$(echo "$response" | tail -n1)
    print_result "FAQ" "DELETE /api/faqs/{id}" "$status" ""
fi

echo ""

# ==========================================
# 6. PROVIDER SERVICE TESTS
# ==========================================
echo -e "${YELLOW}[6] Testing Provider Service...${NC}"

# GET all providers
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/providers")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "Provider" "GET /api/providers" "$status" "$body"

# POST new provider
test_uuid="123e4567-e89b-12d3-a456-426614174001"
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/provider" \
    -H "Content-Type: application/json" \
    -d "{\"userId\":\"$test_uuid\",\"businessName\":\"Test Business\",\"description\":\"Test Provider\"}")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
provider_id=$(echo "$body" | grep -o '"serviceProviderId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "Provider" "POST /api/providers" "$status" "$body"

if [ ! -z "$provider_id" ]; then
    # GET provider by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/provider/$provider_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Provider" "GET /provider/{id}" "$status" "$body"

    # PUT update provider
    response=$(curl -s -w "\n%{http_code}" -X PUT "$BASE_URL/provider/$provider_id" \
        -H "Content-Type: application/json" \
        -d "{\"userId\":\"$test_uuid\",\"businessName\":\"Updated Business\",\"description\":\"Updated Provider\"}")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "Provider" "PUT /provider/{id}" "$status" "$body"

    # DELETE provider
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/provider/$provider_id")
    status=$(echo "$response" | tail -n1)
    print_result "Provider" "DELETE /provider/{id}" "$status" ""
fi

echo ""

# ==========================================
# 7. PAYMENT GATEWAY TESTS
# ==========================================
echo -e "${YELLOW}[7] Testing Payment Gateway Service...${NC}"

# GET all payment gateways
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/payments/gateways")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "PaymentGateway" "GET /api/payment-gateways" "$status" "$body"

# POST new payment gateway
response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL/api/payments/gateways" \
    -H "Content-Type: application/json" \
    -d '{"name":"Test Gateway","apiKey":"test-key","description":"Test Gateway Description"}')
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
gateway_id=$(echo "$body" | grep -o '"gatewayId":[0-9]*' | grep -o '[0-9]*' | head -1)
print_result "PaymentGateway" "POST /api/payment-gateways" "$status" "$body"

if [ ! -z "$gateway_id" ]; then
    # GET payment gateway by ID
    response=$(curl -s -w "\n%{http_code}" "$BASE_URL/api/payments/gateways/$gateway_id")
    status=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    print_result "PaymentGateway" "GET /api/payments/gateways/{id}" "$status" "$body"

    # DELETE payment gateway (no PUT endpoint in controller)
    response=$(curl -s -w "\n%{http_code}" -X DELETE "$BASE_URL/api/payments/gateways/$gateway_id")
    status=$(echo "$response" | tail -n1)
    print_result "PaymentGateway" "DELETE /api/payments/gateways/{id}" "$status" ""
fi

echo ""

# ==========================================
# 8. NOTIFICATION/MESSAGE TESTS (Skipped - requires multipart/form-data)
# ==========================================
echo -e "${YELLOW}[8] Testing Notification/Message Service...${NC}"
echo "  Skipped - Notification endpoints require multipart/form-data format"

echo ""

# ==========================================
# 9. GALLERY/DOCUMENTS TESTS
# ==========================================
echo -e "${YELLOW}[9] Testing Gallery/Documents Service...${NC}"

# GET all documents
response=$(curl -s -w "\n%{http_code}" "$BASE_URL/documents")
status=$(echo "$response" | tail -n1)
body=$(echo "$response" | head -n-1)
print_result "Documents" "GET /api/documents" "$status" "$body"

echo ""

# ==========================================
# SUMMARY
# ==========================================
echo "=========================================="
echo "Test Summary"
echo "=========================================="
echo -e "${GREEN}Passed: $PASSED${NC}"
echo -e "${RED}Failed: $FAILED${NC}"
TOTAL=$((PASSED + FAILED))
echo "Total:  $TOTAL"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}All tests passed! ✓${NC}"
    exit 0
else
    echo -e "${RED}Some tests failed! ✗${NC}"
    exit 1
fi

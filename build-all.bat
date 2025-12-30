@echo off
echo ========================================================
echo       Building All Hexalyte Microservices
echo ========================================================

:: Loop through each service directory and build it
for %%s in (sf-discovery-server sf-api-gateway sf-user-management-application sf-service-application sf-provider-application sf-gallery-application sf-booking-application sf-notification-application sf-review-application sf-payment-application sf-faq-application) do (
    echo --------------------------------------------------------
    echo Building %%s...
    echo --------------------------------------------------------
    cd %%s

    :: Try to use the wrapper; if it fails, check if Maven is installed globally
    if exist mvnw.cmd (
        call mvnw.cmd clean package -DskipTests
    ) else (
        echo mvnw.cmd not found, trying global 'mvn'...
        call mvn clean package -DskipTests
    )

    cd ..

    :: Stop if the build failed
    if %ERRORLEVEL% NEQ 0 (
        echo [ERROR] Build failed for %%s.
        echo Fix errors before running Docker.
        pause
        exit /b %ERRORLEVEL%
    )
)

echo ========================================================
echo       All Builds Successful!
echo Ready for Docker.
echo ========================================================
pause
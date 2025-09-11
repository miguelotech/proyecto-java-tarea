@echo off
echo ========================================
echo    INICIANDO TODOS LOS MICROSERVICIOS
echo ========================================
echo.

echo [1/3] Iniciando Pedido MS (Puerto 8082)...
start "Pedido MS" cmd /k "cd pedido-ms && mvn spring-boot:run"

timeout /t 5 /nobreak > nul

echo [2/3] Iniciando Producto MS (Puerto 8083)...
start "Producto MS" cmd /k "cd producto-ms && mvn spring-boot:run"

timeout /t 5 /nobreak > nul

echo [3/3] Iniciando Main App (Puerto 8081)...
start "Main App" cmd /k "cd main-app && mvn spring-boot:run -Dspring.cloud.compatibility-verifier.enabled=false"

echo.
echo ========================================
echo    TODOS LOS SERVICIOS INICIADOS
echo ========================================
echo.
echo URLs disponibles:
echo - Main App:     http://localhost:8081
echo - Pedido MS:    http://localhost:8082  
echo - Producto MS:  http://localhost:8083
echo.
echo Dashboard de integración:
echo http://localhost:8081/api/integration/dashboard
echo.
pause



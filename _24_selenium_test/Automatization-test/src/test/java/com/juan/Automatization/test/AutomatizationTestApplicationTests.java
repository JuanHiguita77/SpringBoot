package com.juan.Automatization.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
class AutomatizationTestApplicationTests {

	private WebDriver driver;

    @BeforeAll
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeEach
    void openPage() {
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    @DisplayName("Login exitoso")
    void testLoginExitoso() {
        login("standard_user", "secret_sauce");

        WebElement title = driver.findElement(By.className("title"));
        assertEquals("Products", title.getText(), "El usuario no pudo iniciar sesión correctamente");
    }

    @Test
    @DisplayName("Login con credenciales incorrectas")
    void testLoginFallido() {
        login("standard_user", "clave_incorrecta");

        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
        assertTrue(errorMessage.isDisplayed(), "El mensaje de error no se mostró");
        assertEquals("Epic sadface: Username and password do not match any user in this service", 
                     errorMessage.getText(), "El mensaje de error es incorrecto");
    }

    @Test
    @DisplayName("Añadir un producto al carrito")
    void testAgregarProductoAlCarrito() {
        login("standard_user", "secret_sauce");

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        assertEquals("1", cartBadge.getText(), "El producto no se agregó correctamente al carrito");
    }

    @Test
    @DisplayName("Cerrar sesión")
    void testCerrarSesion() {
        login("standard_user", "secret_sauce");

        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        // Espera breve para que se despliegue el menú
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
		
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();

        WebElement loginButton = driver.findElement(By.id("login-button"));
        assertTrue(loginButton.isDisplayed(), "No se redirigió correctamente a la página de login");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login(String username, String password) {
        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

}

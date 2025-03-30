package com.example.testing

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testing.ui.screens.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
//    val composeTestRule = createComposeRule()
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginSuccess_navigatesToHome() {
        // Creamos un NavHostController "de prueba"
        val navController = TestNavHostController(composeTestRule.activity)


        // Seteamos el contenido de la prueba a la composable LoginScreen
        composeTestRule.setContent {
            LoginScreen(navController)
        }

        // 1. Llenar el username (usado en el OutlinedTextField con label "Username")
        composeTestRule
            .onNodeWithText("Username")
            .performTextInput("user@example.com")

        // 2. Llenar el password (usado en el OutlinedTextField con label "Password")
        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("123456")

        // 3. Hacer clic en el botón "Login"
        composeTestRule
            .onNodeWithText("Login")
            .performClick()

        // 4. Verificar que se navega a la ruta "home" (dependiendo de tu lógica,
        // podrías observar algo distinto, p.ej. un texto "Welcome" o que el NavController
        // haya cambiado de destino)
        // Con un TestNavHostController real, podrías comprobar navController.currentBackStackEntry
        // para asegurar que la ruta sea "home"
        // Por ejemplo:
        // assertEquals("home", navController.currentBackStackEntry?.destination?.route)

        // Si tu loginScreen se basa en strings.xml para defaultUser & defaultPass,
        // asegúrate de que "user@example.com" y "123456" coincidan con esos valores.
        // Si no coinciden, forzará la alerta. Ajusta el texto según tus defaults reales.
    }

    @Test
    fun loginFailed_showsAlertDialog() {
        val navController = TestNavHostController(composeTestRule.activity)

        composeTestRule.setContent {
            LoginScreen(navController)
        }

        // 1. Llenamos un username que NO coincida con defaultUser
        composeTestRule
            .onNodeWithText("Username")
            .performTextInput("otro@example.com")

        // 2. Llenamos un password que NO coincida con defaultPass
        composeTestRule
            .onNodeWithText("Password")
            .performTextInput("badpassword")

        // 3. Clic en "Login"
        composeTestRule
            .onNodeWithText("Login")
            .performClick()

        // 4. Verificamos que aparezca la alerta de "Login Failed"
        composeTestRule
            .onNodeWithText("Login Failed")
            .assertIsDisplayed()

        // 5. Opcional: Cerrar la alerta
        composeTestRule
            .onNodeWithText("OK")
            .performClick()

        // Y verificar que ya no esté visible
        composeTestRule
            .onNodeWithText("Login Failed")
            .assertDoesNotExist()
    }
}

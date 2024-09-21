import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.Exceptions.UserException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTest {

    @Test
    fun `should create a user successfully when all fields are valid`() {
        val user = User(
            firstName = "Juan",
            lastName = "Perez",
            email = "juan.perez@example.com",
            password = "StrongPassword!1",
            address = "Avenida Siempre Viva 742",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "12345678"
        )

        assertEquals("Juan", user.firstName)
        assertEquals("Perez", user.lastName)
        assertEquals("juan.perez@example.com", user.email)
        assertEquals("Avenida Siempre Viva 742", user.address)
        assertEquals("1234567890123456789012", user.cvu)
        assertEquals("12345678", user.cryptoWalletAddress)
    }

    @Test
    fun `should throw exception when first name is too short`() {
        val exception = assertThrows<UserException.UserCannotBeRegisteredException> {
            User(
                firstName = "Jo",
                lastName = "Perez",
                email = "juan.perez@example.com",
                password = "StrongPassword!1",
                address = "Avenida Siempre Viva 742",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "12345678"
            )
        }
        assertEquals("The firstName is too short or too long", exception.message)
    }

    @Test
    fun `should throw exception when email is invalid`() {
        val exception = assertThrows<UserException.UserCannotBeRegisteredException> {
            User(
                firstName = "Juan",
                lastName = "Perez",
                email = "invalid-email",
                password = "StrongPassword!1",
                address = "Avenida Siempre Viva 742",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "12345678"
            )
        }
        assertEquals("The email does not have a valid format", exception.message)
    }

    @Test
    fun `should throw exception when password is too weak`() {
        val exception = assertThrows<UserException.UserCannotBeRegisteredException> {
            User(
                firstName = "Juan",
                lastName = "Perez",
                email = "juan.perez@example.com",
                password = "weakpass",
                address = "Avenida Siempre Viva 742",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "12345678"
            )
        }
        assertEquals("The password is too weak", exception.message)
    }

    @Test
    fun `should throw exception when CVU is invalid`() {
        val exception = assertThrows<UserException.UserCannotBeRegisteredException> {
            User(
                firstName = "Juan",
                lastName = "Perez",
                email = "juan.perez@example.com",
                password = "StrongPassword!1",
                address = "Avenida Siempre Viva 742",
                cvu = "12345678",
                cryptoWalletAddress = "12345678"
            )
        }
        assertEquals("The cvu must have 22 digits", exception.message)
    }

    @Test
    fun `should throw exception when crypto wallet address is invalid`() {
        val exception = assertThrows<UserException.UserCannotBeRegisteredException> {
            User(
                firstName = "Juan",
                lastName = "Perez",
                email = "juan.perez@example.com",
                password = "StrongPassword!1",
                address = "Avenida Siempre Viva 742",
                cvu = "1234567890123456789012",
                cryptoWalletAddress = "1234"
            )
        }
        assertEquals("The crypto wallet address must have 8 digits", exception.message)
    }
}

import java.math.BigInteger
import java.security.SecureRandom

fun main() {
    val random = SecureRandom()

    // 1) Zwei kleine zufällige Primzahlen
    val p = BigInteger.probablePrime(16, random) // 37 699
    val q = BigInteger.probablePrime(16, random) // 41 597

    // 2) Modulus n und Euler‑Phi berechnen
    val n   = p * q // 1 568 165 303
    val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE) // 1 568 086 008

    // 3) Öffentlichen Exponenten e wählen 65 537 - geht meistens
    val e = BigInteger.valueOf(65_537)
    check(phi.gcd(e) == BigInteger.ONE) { "e und φ(n) müssen teilerfremd sein" }

    // 4) Privaten Exponenten d berechnen (Inverse von e modulo φ(n))
    val d = e.modInverse(phi) // 1 107 998 945

    // 5) Finaler Schlüssel
    val publicKey = Pair(n, e) // (n = 1 568 165 303, e = 65 537)
    val privateKey = Pair(n, d) // (n = 1 568 165 303, d = 1 107 998 945)

    // 6) Kurze Test-Nachricht verschlüsseln
    val message = BigInteger.valueOf(42) // Beispiel: die Nachricht ist die Zahl 42
    val ciphertext = message.modPow(e, n) // 174 945 063

    // 7) Entschlüsselung
    val decrypted = ciphertext.modPow(d, n) // 42
    println("Entschlüsselt = $decrypted")
    println("Erfolg: ${decrypted == message}") // true
}
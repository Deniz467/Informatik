# Funktionsaufruf

## Schlagwörter

### Primitive Datentypen

- Haben keine Eigenschaften oder Methoden

| Typ     | Vorzeichen | Größe  | Wertebereich                                                               |
|---------|------------|--------|----------------------------------------------------------------------------|
| byte    | ja         | 8 bit  | -128...128                                                                 |
| short   | ja         | 16 bit | -32768...32767                                                             |
| int     | ja         | 32 bit | -2147483648...2147483647                                                   |
| long    | ja         | 64 bit | -9223372036854775808...9223372036854775807                                 |
| char    | nein       | 16 bit | 16-Bit Unicode Zeichen (0x0000...0xffff (6553510))                         |
| float   | ja         | 32 bit | $$-3.40282347 \times 1038 ... 3.40282347 \times 1038$$                     |
| double  | ja         | 64 bit | $$-1.79769313486231570 \times 10308 ... 1.79769313486231570 \times 10308$$ |
| boolean | -/-        | 8 bit  | true/false                                                                 |

### Objektreferenzen

- Beschreibt Ort im Speicher
- Benötigt, um das Objekt zu finden
- Wie eine Handynummer

> Jemand, der Eure Telefonnummer hat, kann Euch eine Mitteilung schicken und Euch auffordern etwas zu tun, unabhängig
> davon, wo Ihr gerade seid. Denkt an Euren Boss, der Euch anruft, und Euch eine Mitteilung schickt. "Fangen Sie an den
> Bericht zu schreiben!" Das ist wie das Verwenden einer Objektreferenz, um ein Objekt aufzufordern eine Methode
> auszuführen.
>
{style="tip" title="Beispiel"}

### Call by Value

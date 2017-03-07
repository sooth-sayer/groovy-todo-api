package tBackend.lib

import java.security.MessageDigest

class Encryptor {
  static encrypt(String message) {
    MessageDigest sha1 = MessageDigest.getInstance("SHA1")
    byte[] digest  = sha1.digest(message.getBytes())
    new  BigInteger(1, digest).toString(16)
  }
}

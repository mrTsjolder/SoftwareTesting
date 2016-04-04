package at.archkb.server.util;

import java.util.regex.Pattern;

public class PasswordUtils {

  private static final Pattern BCRYPT_PATTERN = Pattern
      .compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

  public static boolean isBcryptPassword(String password) {
    return BCRYPT_PATTERN.matcher(password).matches();
  }
}

import java.io.IOException;

public class Repro {

  /**
   * Compiles on JDK 8, 9 and 10 but fails to compile on JDK 11:
   *
   *   unreported exception java.lang.Exception; must be caught or declared to be thrown
   */
  void repro() throws IOException {
    when(f(any()));
  }

  interface G<T, E extends Exception> { }

  <T, E extends Exception> T f(G<T, E> g) throws IOException, E {
    return null;
  }

  static <T> T any() {
    return null;
  }

  static <T> void when(T methodCall) {
  }
}

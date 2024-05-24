/** The entry point of the application. Displays an AppWindow. */
// (You don't need to do anything in this file)
@main
def main(): Unit =
  val window = AppWindow()
  window.setVisible(true)
  window.start()
  
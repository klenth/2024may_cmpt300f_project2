import java.awt.*
import java.awt.event.{KeyAdapter, KeyEvent}
import javax.swing.*

/** The main window of the application. It holds a Field and not much else. */
// (You don't need to do anything in this file)
class AppWindow(width: Int = 500, height: Int = 500) extends JFrame("Stuff"):

  private val field: Field = Field(width, height)
  val start: () => Unit = field.start

  setLayout(new BorderLayout())
  add(field, BorderLayout.CENTER)
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  pack()
  setResizable(false)

  // Pause/unpause the animation on space bar
  private class SpaceKeyListener extends KeyAdapter:
    override def keyReleased(e: KeyEvent): Unit =
      if (e.getKeyCode == KeyEvent.VK_SPACE)
        if (field.isRunning)
          field.stop()
        else
          field.start()

  addKeyListener(SpaceKeyListener())
